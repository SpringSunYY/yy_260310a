package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.enums.WarehouseOrderApplicantStatusEnum;
import com.lz.manage.enums.WarehouseOrderStatusEnum;
import com.lz.manage.mapper.PurchaseOrderInfoMapper;
import com.lz.manage.model.domain.PurchaseOrderDetailInfo;
import com.lz.manage.model.domain.PurchaseOrderInfo;
import com.lz.manage.model.domain.SupplierInfo;
import com.lz.manage.model.dto.purchaseOrderInfo.PurchaseOrderInfoQuery;
import com.lz.manage.model.vo.purchaseOrderInfo.PurchaseOrderInfoVo;
import com.lz.manage.service.IPurchaseOrderInfoService;
import com.lz.manage.service.ISupplierInfoService;
import com.lz.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 采购订单Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class PurchaseOrderInfoServiceImpl extends ServiceImpl<PurchaseOrderInfoMapper, PurchaseOrderInfo> implements IPurchaseOrderInfoService {

    @Resource
    private PurchaseOrderInfoMapper purchaseOrderInfoMapper;

    @Resource
    private ISupplierInfoService supplierInfoService;

    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询采购订单
     *
     * @param orderId 采购订单主键
     * @return 采购订单
     */
    @Override
    public PurchaseOrderInfo selectPurchaseOrderInfoByOrderId(Long orderId) {
        return purchaseOrderInfoMapper.selectPurchaseOrderInfoByOrderId(orderId);
    }

    /**
     * 查询采购订单列表
     *
     * @param purchaseOrderInfo 采购订单
     * @return 采购订单
     */
    @Override
    public List<PurchaseOrderInfo> selectPurchaseOrderInfoList(PurchaseOrderInfo purchaseOrderInfo) {
        List<PurchaseOrderInfo> purchaseOrderInfos = purchaseOrderInfoMapper.selectPurchaseOrderInfoList(purchaseOrderInfo);
        for (PurchaseOrderInfo info : purchaseOrderInfos) {
            SupplierInfo supplierInfo = supplierInfoService.selectSupplierInfoById(info.getSupplierId());
            if (StringUtils.isNotNull(supplierInfo)) {
                info.setSupplierName(supplierInfo.getSupplierName());
            }
            if (StringUtils.isNotNull(info.getApproverId())) {
                SysUser sysUser = sysUserService.selectUserById(info.getApproverId());
                if (StringUtils.isNotNull(sysUser)) {
                    info.setApproverName(sysUser.getNickName());
                }
            }
            if (StringUtils.isNotNull(info.getApplicantId())) {
                SysUser sysUser = sysUserService.selectUserById(info.getApplicantId());
                if (StringUtils.isNotNull(sysUser)) {
                    info.setApplicantName(sysUser.getNickName());
                }
            }
        }
        return purchaseOrderInfos;
    }

    /**
     * 新增采购订单
     *
     * @param purchaseOrderInfo 采购订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertPurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo) {
        //判断供应商是否存在
        SupplierInfo supplierInfo = supplierInfoService.selectSupplierInfoById(purchaseOrderInfo.getSupplierId());
        ThrowUtils.throwIf(StringUtils.isNull(supplierInfo), "供应商不存在");
        //判断编号是否存在
        PurchaseOrderInfo purchaseOrderInfoByNo = purchaseOrderInfoMapper.selectPurchaseOrderInfoByNo(purchaseOrderInfo.getOrderNo());
        ThrowUtils.throwIf(StringUtils.isNotNull(purchaseOrderInfoByNo), "采购订单编号已存在");
        purchaseOrderInfo.setOrderStatus(WarehouseOrderStatusEnum.WAREHOUSE_ORDER_STATUS_0.getValue());
        purchaseOrderInfo.setApplicantStatus(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_0.getValue());
        purchaseOrderInfo.setCreateBy(SecurityUtils.getUsername());
        purchaseOrderInfo.setCreateTime(DateUtils.getNowDate());
        purchaseOrderInfo.setTotalAmount(calculateTotalAmount(purchaseOrderInfo.getPurchaseOrderDetailInfoList()));
        int i = purchaseOrderInfoMapper.insertPurchaseOrderInfo(purchaseOrderInfo);
        insertPurchaseOrderDetailInfo(purchaseOrderInfo);
        return i;
    }

    /**
     * 修改采购订单
     *
     * @param purchaseOrderInfo 采购订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updatePurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo) {
        //判断供应商是否存在
        SupplierInfo supplierInfo = supplierInfoService.selectSupplierInfoById(purchaseOrderInfo.getSupplierId());
        ThrowUtils.throwIf(StringUtils.isNull(supplierInfo), "供应商不存在");
        //判断编号是否存在
        PurchaseOrderInfo purchaseOrderInfoByNo = purchaseOrderInfoMapper.selectPurchaseOrderInfoByNo(purchaseOrderInfo.getOrderNo());
        ThrowUtils.throwIf(StringUtils.isNotNull(purchaseOrderInfoByNo)
                           && !purchaseOrderInfoByNo.getOrderId().equals(purchaseOrderInfo.getOrderId()),
                "采购订单编号已存在");
        purchaseOrderInfo.setUpdateTime(DateUtils.getNowDate());
        purchaseOrderInfo.setCreateBy(purchaseOrderInfoByNo.getCreateBy());
        purchaseOrderInfo.setCreateTime(purchaseOrderInfoByNo.getCreateTime());
        purchaseOrderInfoMapper.deletePurchaseOrderDetailInfoByOrderId(purchaseOrderInfo.getOrderId());
        insertPurchaseOrderDetailInfo(purchaseOrderInfo);
        purchaseOrderInfo.setTotalAmount(calculateTotalAmount(purchaseOrderInfo.getPurchaseOrderDetailInfoList()));
        return purchaseOrderInfoMapper.updatePurchaseOrderInfo(purchaseOrderInfo);
    }

    @Override
    public int auditPurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo) {
        //判断供应商是否存在
        SupplierInfo supplierInfo = supplierInfoService.selectSupplierInfoById(purchaseOrderInfo.getSupplierId());
        ThrowUtils.throwIf(StringUtils.isNull(supplierInfo), "供应商不存在");
        //判断编号是否存在
        PurchaseOrderInfo purchaseOrderInfoByNo = purchaseOrderInfoMapper.selectPurchaseOrderInfoByNo(purchaseOrderInfo.getOrderNo());
        ThrowUtils.throwIf(StringUtils.isNotNull(purchaseOrderInfoByNo)
                           && !purchaseOrderInfoByNo.getOrderId().equals(purchaseOrderInfo.getOrderId()),
                "采购订单编号已存在");
        //是否已经同意审核
        ThrowUtils.throwIf(purchaseOrderInfoByNo.getApplicantStatus().equals(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue()),
                "采购订单已同意，不可修改");
        //如果两者状态不一致
        Date nowDate = DateUtils.getNowDate();
        if (!purchaseOrderInfo.getApplicantStatus().equals(purchaseOrderInfoByNo.getApplicantStatus())) {
            purchaseOrderInfo.setApprovalTime(nowDate);
            purchaseOrderInfo.setApproverId(SecurityUtils.getUserId());
        }
        //如果是拒绝，直接更新状态为取消
        if (purchaseOrderInfo.getApplicantStatus().equals(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_2.getValue())) {
            purchaseOrderInfo.setOrderStatus(WarehouseOrderStatusEnum.WAREHOUSE_ORDER_STATUS_2.getValue());
        }
        purchaseOrderInfo.setUpdateTime(nowDate);
        purchaseOrderInfoMapper.deletePurchaseOrderDetailInfoByOrderId(purchaseOrderInfo.getOrderId());
        insertPurchaseOrderDetailInfo(purchaseOrderInfo);
        purchaseOrderInfo.setTotalAmount(calculateTotalAmount(purchaseOrderInfo.getPurchaseOrderDetailInfoList()));
        return purchaseOrderInfoMapper.updatePurchaseOrderInfo(purchaseOrderInfo);
    }

    /**
     * 批量删除采购订单
     *
     * @param orderIds 需要删除的采购订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePurchaseOrderInfoByOrderIds(Long[] orderIds) {
        purchaseOrderInfoMapper.deletePurchaseOrderDetailInfoByOrderIds(orderIds);
        return purchaseOrderInfoMapper.deletePurchaseOrderInfoByOrderIds(orderIds);
    }

    /**
     * 删除采购订单信息
     *
     * @param orderId 采购订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePurchaseOrderInfoByOrderId(Long orderId) {
        purchaseOrderInfoMapper.deletePurchaseOrderDetailInfoByOrderId(orderId);
        return purchaseOrderInfoMapper.deletePurchaseOrderInfoByOrderId(orderId);
    }

    /**
     * 计算采购订单明细总金额
     *
     * @param purchaseOrderDetailInfoList 采购订单明细列表
     * @return 总金额
     */
    public BigDecimal calculateTotalAmount(List<PurchaseOrderDetailInfo> purchaseOrderDetailInfoList) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (StringUtils.isNotNull(purchaseOrderDetailInfoList)) {
            for (PurchaseOrderDetailInfo purchaseOrderDetailInfo : purchaseOrderDetailInfoList) {
                totalAmount = totalAmount.add(purchaseOrderDetailInfo.getPurchasePrice().multiply(BigDecimal.valueOf(purchaseOrderDetailInfo.getPurchaseQuantity())));
            }
        }
        return totalAmount;
    }

    /**
     * 新增采购订单明细信息
     *
     * @param purchaseOrderInfo 采购订单对象
     */
    public void insertPurchaseOrderDetailInfo(PurchaseOrderInfo purchaseOrderInfo) {
        List<PurchaseOrderDetailInfo> purchaseOrderDetailInfoList = purchaseOrderInfo.getPurchaseOrderDetailInfoList();
        Long orderId = purchaseOrderInfo.getOrderId();
        if (StringUtils.isNotNull(purchaseOrderDetailInfoList)) {
            List<PurchaseOrderDetailInfo> list = new ArrayList<PurchaseOrderDetailInfo>();
            for (PurchaseOrderDetailInfo purchaseOrderDetailInfo : purchaseOrderDetailInfoList) {
                //判断备件编号、采购数量、采购单价、金额、已收数量是否为空
                ThrowUtils.throwIf(StringUtils.isEmpty(purchaseOrderDetailInfo.getPartsCode()), "备件编号不能为空");
                ThrowUtils.throwIf(StringUtils.isNull(purchaseOrderDetailInfo.getPurchaseQuantity()), "采购数量不能为空");
                ThrowUtils.throwIf(StringUtils.isNull(purchaseOrderDetailInfo.getPurchasePrice()), "采购单价不能为空");
                ThrowUtils.throwIf(StringUtils.isNull(purchaseOrderDetailInfo.getAmount()), "金额不能为空");
                ThrowUtils.throwIf(StringUtils.isNull(purchaseOrderDetailInfo.getReceivedQuantity()), "已收数量不能为空");
                purchaseOrderDetailInfo.setOrderId(orderId);
                if (StringUtils.isNotEmpty(purchaseOrderInfo.getCreateBy())) {
                    purchaseOrderDetailInfo.setCreateBy(purchaseOrderInfo.getCreateBy());
                }
                if (StringUtils.isNotEmpty(purchaseOrderInfo.getUpdateBy())) {
                    purchaseOrderDetailInfo.setUpdateBy(purchaseOrderInfo.getUpdateBy());
                }
                if (StringUtils.isNotNull(purchaseOrderInfo.getCreateTime())) {
                    purchaseOrderDetailInfo.setCreateTime(purchaseOrderInfo.getCreateTime());
                }
                if (StringUtils.isNotNull(purchaseOrderInfo.getUpdateTime())) {
                    purchaseOrderDetailInfo.setUpdateTime(purchaseOrderInfo.getUpdateTime());
                }
                list.add(purchaseOrderDetailInfo);
            }
            if (!list.isEmpty()) {
                purchaseOrderInfoMapper.batchPurchaseOrderDetailInfo(list);
            }
        }
    }

    //endregion
    @Override
    public QueryWrapper<PurchaseOrderInfo> getQueryWrapper(PurchaseOrderInfoQuery purchaseOrderInfoQuery) {
        QueryWrapper<PurchaseOrderInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = purchaseOrderInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long orderId = purchaseOrderInfoQuery.getOrderId();
        queryWrapper.eq(StringUtils.isNotNull(orderId), "order_id", orderId);

        String orderNo = purchaseOrderInfoQuery.getOrderNo();
        queryWrapper.eq(StringUtils.isNotEmpty(orderNo), "order_no", orderNo);

        Long supplierId = purchaseOrderInfoQuery.getSupplierId();
        queryWrapper.eq(StringUtils.isNotNull(supplierId), "supplier_id", supplierId);

        String orderStatus = purchaseOrderInfoQuery.getOrderStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(orderStatus), "order_status", orderStatus);

        String applicantStatus = purchaseOrderInfoQuery.getApplicantStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(applicantStatus), "applicant_status", applicantStatus);

        String createBy = purchaseOrderInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy), "create_by", createBy);

        Date createTime = purchaseOrderInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<PurchaseOrderInfoVo> convertVoList(List<PurchaseOrderInfo> purchaseOrderInfoList) {
        if (StringUtils.isEmpty(purchaseOrderInfoList)) {
            return Collections.emptyList();
        }
        return purchaseOrderInfoList.stream().map(PurchaseOrderInfoVo::objToVo).collect(Collectors.toList());
    }
}
