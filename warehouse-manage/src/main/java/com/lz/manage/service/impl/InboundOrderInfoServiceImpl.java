package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.enums.*;
import com.lz.manage.mapper.InboundOrderInfoMapper;
import com.lz.manage.model.domain.*;
import com.lz.manage.model.dto.inboundOrderInfo.InboundOrderInfoQuery;
import com.lz.manage.model.dto.inventoryTransactionInfo.InventoryTransactionInfoDto;
import com.lz.manage.model.vo.inboundOrderInfo.InboundOrderInfoVo;
import com.lz.manage.service.*;
import com.lz.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 入库单Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class InboundOrderInfoServiceImpl extends ServiceImpl<InboundOrderInfoMapper, InboundOrderInfo> implements IInboundOrderInfoService {

    @Resource
    private InboundOrderInfoMapper inboundOrderInfoMapper;

    @Resource
    private ISupplierInfoService supplierInfoService;

    @Resource
    private IPurchaseOrderInfoService purchaseOrderInfoService;

    @Resource
    private IWarehouseInfoService warehouseInfoService;

    @Resource
    private ILocationInfoService locationInfoService;

    @Resource
    private ISysUserService userService;

    @Resource
    private IInventoryTransactionInfoService inventoryTransactionInfoService;

    //region mybatis代码

    /**
     * 查询入库单
     *
     * @param id 入库单主键
     * @return 入库单
     */
    @Override
    public InboundOrderInfo selectInboundOrderInfoById(Long id) {
        return inboundOrderInfoMapper.selectInboundOrderInfoById(id);
    }

    /**
     * 查询入库单列表
     *
     * @param inboundOrderInfo 入库单
     * @return 入库单
     */
    @Override
    public List<InboundOrderInfo> selectInboundOrderInfoList(InboundOrderInfo inboundOrderInfo) {
        List<InboundOrderInfo> inboundOrderInfoList = inboundOrderInfoMapper.selectInboundOrderInfoList(inboundOrderInfo);
        for (InboundOrderInfo info : inboundOrderInfoList) {
            // 获取仓库名称
            if (StringUtils.isNotNull(info.getWarehouseId())) {
                WarehouseInfo warehouseInfo = warehouseInfoService.selectWarehouseInfoById(info.getWarehouseId());
                if (StringUtils.isNotNull(warehouseInfo)) {
                    info.setWarehouseName(warehouseInfo.getWarehouseName());
                }
            }
            // 获取供应商名称
            if (StringUtils.isNotNull(info.getSupplierId())) {
                SupplierInfo supplierInfo = supplierInfoService.selectSupplierInfoById(info.getSupplierId());
                if (StringUtils.isNotNull(supplierInfo)) {
                    info.setSupplierName(supplierInfo.getSupplierName());
                }
            }
            // 获取经办人姓名
            if (StringUtils.isNotNull(info.getOperatorId())) {
                com.lz.common.core.domain.entity.SysUser sysUser = userService.selectUserById(info.getOperatorId());
                if (StringUtils.isNotNull(sysUser)) {
                    info.setOperatorName(sysUser.getNickName());
                }
            }
            // 获取审核人姓名
            if (StringUtils.isNotNull(info.getReviewerId())) {
                com.lz.common.core.domain.entity.SysUser sysUser = userService.selectUserById(info.getReviewerId());
                if (StringUtils.isNotNull(sysUser)) {
                    info.setReviewerName(sysUser.getNickName());
                }
            }
            //获取订单
            if (StringUtils.isNotNull(info.getOrderId())) {
                PurchaseOrderInfo purchaseOrderInfo = purchaseOrderInfoService.selectPurchaseOrderInfoByOrderId(info.getOrderId());
                if (StringUtils.isNotNull(purchaseOrderInfo)) {
                    info.setOrderNo(purchaseOrderInfo.getOrderNo());
                }
            }
        }
        return inboundOrderInfoList;
    }

    /**
     * 新增入库单
     *
     * @param inboundOrderInfo 入库单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertInboundOrderInfo(InboundOrderInfo inboundOrderInfo) {
        // 设置默认值
        inboundOrderInfo.setInboundStatus(WarehouseInboundStatusEnum.WAREHOUSE_INBOUND_STATUS_0.getValue());
        inboundOrderInfo.setReviewStatus(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_0.getValue());
        inboundOrderInfo.setCreateBy(SecurityUtils.getUsername());
        inboundOrderInfo.setCreateTime(DateUtils.getNowDate());

        // 校验入库单号是否存在
        InboundOrderInfo inboundOrderInfoByNo = inboundOrderInfoMapper.selectInboundOrderInfoByNo(inboundOrderInfo.getInboundNo());
        ThrowUtils.throwIf(StringUtils.isNotNull(inboundOrderInfoByNo), "入库单号已存在");

        // 判断仓库是否存在
        if (StringUtils.isNotNull(inboundOrderInfo.getWarehouseId())) {
            WarehouseInfo warehouseInfo = warehouseInfoService.selectWarehouseInfoById(inboundOrderInfo.getWarehouseId());
            ThrowUtils.throwIf(StringUtils.isNull(warehouseInfo), "仓库不存在");
        }

        // 判断供应商是否存在
        if (StringUtils.isNotNull(inboundOrderInfo.getSupplierId())) {
            SupplierInfo supplierInfo = supplierInfoService.selectSupplierInfoById(inboundOrderInfo.getSupplierId());
            ThrowUtils.throwIf(StringUtils.isNull(supplierInfo), "供应商不存在");
        }

        // 如果是采购入库，需要校验订单
        if (StringUtils.isNotEmpty(inboundOrderInfo.getInboundType())
            && inboundOrderInfo.getInboundType().equals(WarehouseInboundTypeEnum.WAREHOUSE_INBOUND_TYPE_0.getValue())) {
            ThrowUtils.throwIf(StringUtils.isNull(inboundOrderInfo.getOrderId()), "采购入库必须选择关联订单");
            PurchaseOrderInfo purchaseOrderInfo = purchaseOrderInfoService.selectPurchaseOrderInfoByOrderId(inboundOrderInfo.getOrderId());
            ThrowUtils.throwIf(StringUtils.isNull(purchaseOrderInfo), "关联的订单不存在");
            // 判断订单是否已入库
            ThrowUtils.throwIf(purchaseOrderInfo.getOrderStatus().equals(WarehouseOrderStatusEnum.WAREHOUSE_ORDER_STATUS_1.getValue()), "该订单已入库");
            // 判断订单是否为已下单状态
            ThrowUtils.throwIf(!purchaseOrderInfo.getOrderStatus().equals(WarehouseOrderStatusEnum.WAREHOUSE_ORDER_STATUS_0.getValue()), "只能为已下单状态的订单创建入库单");
            // 判断订单审核状态是否为已通过
            ThrowUtils.throwIf(!purchaseOrderInfo.getApplicantStatus().equals(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue()), "只能为审核通过的订单创建入库单");
        }

        // 校验明细
        validateInboundOrderDetailInfo(inboundOrderInfo);

        int rows = inboundOrderInfoMapper.insertInboundOrderInfo(inboundOrderInfo);
        insertInboundOrderDetailInfo(inboundOrderInfo);
        return rows;
    }

    /**
     * 校验入库明细
     */
    private void validateInboundOrderDetailInfo(InboundOrderInfo inboundOrderInfo) {
        List<InboundOrderDetailInfo> detailList = inboundOrderInfo.getInboundOrderDetailInfoList();
        ThrowUtils.throwIf(StringUtils.isNull(detailList) || detailList.isEmpty(), "入库明细不能为空");

        for (InboundOrderDetailInfo detail : detailList) {
            ThrowUtils.throwIf(StringUtils.isEmpty(detail.getPartsCode()), "备件编号不能为空");
            ThrowUtils.throwIf(StringUtils.isNull(detail.getInboundQuantity()), "入库数量不能为空");
            ThrowUtils.throwIf(StringUtils.isNull(detail.getInboundQuantity()) || detail.getInboundQuantity() <= 0, "入库数量必须大于0");
            ThrowUtils.throwIf(StringUtils.isEmpty(detail.getBatchNo()), "批次号不能为空");
            ThrowUtils.throwIf(StringUtils.isNull(detail.getLocationId()), "库位不能为空");
            ThrowUtils.throwIf(StringUtils.isEmpty(detail.getQualityStatus()), "质量状态不能为空");

            // 判断库位是否存在
            LocationInfo locationInfo = locationInfoService.selectLocationInfoById(detail.getLocationId());
            ThrowUtils.throwIf(StringUtils.isNull(locationInfo), "库位不存在");
        }
    }

    /**
     * 修改入库单
     *
     * @param inboundOrderInfo 入库单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateInboundOrderInfo(InboundOrderInfo inboundOrderInfo) {
        // 判断入库单是否存在
        InboundOrderInfo existingInboundOrder = inboundOrderInfoMapper.selectInboundOrderInfoById(inboundOrderInfo.getId());
        ThrowUtils.throwIf(StringUtils.isNull(existingInboundOrder), "入库单不存在");

        // 判断入库单是否已审核通过
        ThrowUtils.throwIf(existingInboundOrder.getReviewStatus().equals(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue()), "已审核通过的入库单不可修改");

        // 校验入库单号是否存在（排除自身）
        InboundOrderInfo inboundOrderInfoByNo = inboundOrderInfoMapper.selectInboundOrderInfoByNo(inboundOrderInfo.getInboundNo());
        ThrowUtils.throwIf(StringUtils.isNotNull(inboundOrderInfoByNo)
                           && !inboundOrderInfoByNo.getId().equals(inboundOrderInfo.getId()),
                "入库单号已存在");

        // 判断仓库是否存在
        if (StringUtils.isNotNull(inboundOrderInfo.getWarehouseId())) {
            WarehouseInfo warehouseInfo = warehouseInfoService.selectWarehouseInfoById(inboundOrderInfo.getWarehouseId());
            ThrowUtils.throwIf(StringUtils.isNull(warehouseInfo), "仓库不存在");
        }

        // 判断供应商是否存在
        if (StringUtils.isNotNull(inboundOrderInfo.getSupplierId())) {
            SupplierInfo supplierInfo = supplierInfoService.selectSupplierInfoById(inboundOrderInfo.getSupplierId());
            ThrowUtils.throwIf(StringUtils.isNull(supplierInfo), "供应商不存在");
        }
        inboundOrderInfo.setCreateBy(inboundOrderInfoByNo.getCreateBy());
        inboundOrderInfo.setCreateTime(inboundOrderInfoByNo.getCreateTime());
        inboundOrderInfo.setUpdateTime(DateUtils.getNowDate());
        inboundOrderInfoMapper.deleteInboundOrderDetailInfoByInboundId(inboundOrderInfo.getId());

        // 校验明细
        validateInboundOrderDetailInfo(inboundOrderInfo);

        insertInboundOrderDetailInfo(inboundOrderInfo);
        return inboundOrderInfoMapper.updateInboundOrderInfo(inboundOrderInfo);
    }

    /**
     * 审核入库单
     */
    @Override
    @Transactional
    public int auditInboundOrderInfo(InboundOrderInfo inboundOrderInfo) {
        // 判断入库单是否存在
        InboundOrderInfo existingInboundOrder = inboundOrderInfoMapper.selectInboundOrderInfoById(inboundOrderInfo.getId());
        ThrowUtils.throwIf(StringUtils.isNull(existingInboundOrder), "入库单不存在");

        // 判断入库单是否已审核
        ThrowUtils.throwIf(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue().equals(existingInboundOrder.getReviewStatus()), "入库单已审核通过，不可重复审核");
        // 校验明细
        validateInboundOrderDetailInfo(inboundOrderInfo);

        inboundOrderInfo.setCreateBy(existingInboundOrder.getCreateBy());
        inboundOrderInfo.setCreateTime(existingInboundOrder.getCreateTime());
        inboundOrderInfo.setInboundNo(existingInboundOrder.getInboundNo());
        Date nowDate = DateUtils.getNowDate();
        // 如果审核状态发生变化，设置审核人和审核时间
        if (!inboundOrderInfo.getReviewStatus().equals(existingInboundOrder.getReviewStatus())) {
            inboundOrderInfo.setReviewerId(SecurityUtils.getUserId());
            inboundOrderInfo.setReviewTime(nowDate);
        }

        // 如果审核通过，同时更新入库状态为已入库
        if (WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue().equals(inboundOrderInfo.getReviewStatus())) {
            inboundOrderInfo.setInboundStatus(WarehouseInboundStatusEnum.WAREHOUSE_INBOUND_STATUS_1.getValue());

            // 如果有关联订单，同步更新订单状态为已入库
            if (StringUtils.isNotNull(existingInboundOrder.getOrderId())) {
                PurchaseOrderInfo purchaseOrderInfo = new PurchaseOrderInfo();
                purchaseOrderInfo.setOrderId(existingInboundOrder.getOrderId());
                purchaseOrderInfo.setOrderStatus(WarehouseOrderStatusEnum.WAREHOUSE_ORDER_STATUS_1.getValue());
                purchaseOrderInfoService.updateById(purchaseOrderInfo);
            }

            //创建入库流水
            List<InventoryTransactionInfoDto> transactionInfoDtos = new ArrayList<>();
            //遍历明细
            for (InboundOrderDetailInfo detail : inboundOrderInfo.getInboundOrderDetailInfoList()) {
                InventoryTransactionInfoDto transactionInfoDto = getInventoryTransactionInfoDto(inboundOrderInfo, detail, nowDate);
                transactionInfoDtos.add(transactionInfoDto);
            }
            inventoryTransactionInfoService.insertInventoryTransactionInfos(transactionInfoDtos);
        }

        // 删除原有明细并插入新明细
        inboundOrderInfoMapper.deleteInboundOrderDetailInfoByInboundId(inboundOrderInfo.getId());
        insertInboundOrderDetailInfo(inboundOrderInfo);

        inboundOrderInfo.setUpdateTime(nowDate);
        return inboundOrderInfoMapper.updateInboundOrderInfo(inboundOrderInfo);
    }

    private static InventoryTransactionInfoDto getInventoryTransactionInfoDto(InboundOrderInfo inboundOrderInfo, InboundOrderDetailInfo detail, Date nowDate) {
        InventoryTransactionInfoDto transactionInfoDto = new InventoryTransactionInfoDto();
        transactionInfoDto.setPartsCode(detail.getPartsCode());
        transactionInfoDto.setWarehouseId(detail.getWarehouseId());
        transactionInfoDto.setLocationId(detail.getLocationId());
        transactionInfoDto.setBatchNo(detail.getBatchNo());
        transactionInfoDto.setTransactionType(WarehouseTransactionTypeEnum.WAREHOUSE_TRANSACTION_TYPE_0.getValue());
        transactionInfoDto.setRelatedOrderNo(inboundOrderInfo.getInboundNo());
        transactionInfoDto.setOperatorId(inboundOrderInfo.getOperatorId());
        transactionInfoDto.setTransactionTime(inboundOrderInfo.getInboundDate());
        transactionInfoDto.setCreateBy(inboundOrderInfo.getCreateBy());
        transactionInfoDto.setCreateTime(nowDate);
        transactionInfoDto.setChangeQuantity(detail.getInboundQuantity());
        transactionInfoDto.setExpiryDate(detail.getExpiryDate());
        return transactionInfoDto;
    }

    /**
     * 批量删除入库单
     *
     * @param ids 需要删除的入库单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteInboundOrderInfoByIds(Long[] ids) {
        for (Long id : ids) {
            InboundOrderInfo existingInboundOrder = inboundOrderInfoMapper.selectInboundOrderInfoById(id);
            ThrowUtils.throwIf(StringUtils.isNull(existingInboundOrder), "入库单不存在");
            // 判断入库单是否已审核通过
            ThrowUtils.throwIf(existingInboundOrder.getReviewStatus().equals(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue()), "已审核通过的入库单不可删除");
        }
        inboundOrderInfoMapper.deleteInboundOrderDetailInfoByInboundIds(ids);
        return inboundOrderInfoMapper.deleteInboundOrderInfoByIds(ids);
    }

    /**
     * 删除入库单信息
     *
     * @param id 入库单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteInboundOrderInfoById(Long id) {
        InboundOrderInfo existingInboundOrder = inboundOrderInfoMapper.selectInboundOrderInfoById(id);
        ThrowUtils.throwIf(StringUtils.isNull(existingInboundOrder), "入库单不存在");
        // 判断入库单是否已审核通过
        ThrowUtils.throwIf(existingInboundOrder.getReviewStatus().equals(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue()), "已审核通过的入库单不可删除");
        inboundOrderInfoMapper.deleteInboundOrderDetailInfoByInboundId(id);
        return inboundOrderInfoMapper.deleteInboundOrderInfoById(id);
    }

    /**
     * 新增入库明细信息
     *
     * @param inboundOrderInfo 入库单对象
     */
    public void insertInboundOrderDetailInfo(InboundOrderInfo inboundOrderInfo) {
        List<InboundOrderDetailInfo> inboundOrderDetailInfoList = inboundOrderInfo.getInboundOrderDetailInfoList();
        Long id = inboundOrderInfo.getId();
        if (StringUtils.isNotNull(inboundOrderDetailInfoList)) {
            List<InboundOrderDetailInfo> list = new ArrayList<InboundOrderDetailInfo>();
            for (InboundOrderDetailInfo inboundOrderDetailInfo : inboundOrderDetailInfoList) {
                inboundOrderDetailInfo.setInboundId(id);
                if (StringUtils.isNotEmpty(inboundOrderInfo.getCreateBy())) {
                    inboundOrderDetailInfo.setCreateBy(inboundOrderInfo.getCreateBy());
                }
                if (StringUtils.isNotEmpty(inboundOrderInfo.getUpdateBy())) {
                    inboundOrderDetailInfo.setUpdateBy(inboundOrderInfo.getUpdateBy());
                }
                if (StringUtils.isNotNull(inboundOrderInfo.getCreateTime())) {
                    inboundOrderDetailInfo.setCreateTime(inboundOrderInfo.getCreateTime());
                }
                if (StringUtils.isNotNull(inboundOrderInfo.getUpdateTime())) {
                    inboundOrderDetailInfo.setUpdateTime(inboundOrderInfo.getUpdateTime());
                }
                list.add(inboundOrderDetailInfo);
            }
            if (!list.isEmpty()) {
                inboundOrderInfoMapper.batchInboundOrderDetailInfo(list);
            }
        }
    }

    //endregion
    @Override
    public QueryWrapper<InboundOrderInfo> getQueryWrapper(InboundOrderInfoQuery inboundOrderInfoQuery) {
        QueryWrapper<InboundOrderInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = inboundOrderInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = inboundOrderInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        String inboundNo = inboundOrderInfoQuery.getInboundNo();
        queryWrapper.eq(StringUtils.isNotEmpty(inboundNo), "inbound_no", inboundNo);

        String inboundType = inboundOrderInfoQuery.getInboundType();
        queryWrapper.eq(StringUtils.isNotEmpty(inboundType), "inbound_type", inboundType);

        Long warehouseId = inboundOrderInfoQuery.getWarehouseId();
        queryWrapper.eq(StringUtils.isNotNull(warehouseId), "warehouse_id", warehouseId);

        Long orderId = inboundOrderInfoQuery.getOrderId();
        queryWrapper.eq(StringUtils.isNotNull(orderId), "order_id", orderId);

        Long supplierId = inboundOrderInfoQuery.getSupplierId();
        queryWrapper.eq(StringUtils.isNotNull(supplierId), "supplier_id", supplierId);

        Date inboundDate = inboundOrderInfoQuery.getInboundDate();
        queryWrapper.eq(StringUtils.isNotNull(inboundDate), "inbound_date", inboundDate);

        String inboundStatus = inboundOrderInfoQuery.getInboundStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(inboundStatus), "inbound_status", inboundStatus);

        String reviewStatus = inboundOrderInfoQuery.getReviewStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(reviewStatus), "review_status", reviewStatus);

        String createBy = inboundOrderInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy), "create_by", createBy);

        Date createTime = inboundOrderInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<InboundOrderInfoVo> convertVoList(List<InboundOrderInfo> inboundOrderInfoList) {
        if (StringUtils.isEmpty(inboundOrderInfoList)) {
            return Collections.emptyList();
        }
        return inboundOrderInfoList.stream().map(InboundOrderInfoVo::objToVo).collect(Collectors.toList());
    }
}
