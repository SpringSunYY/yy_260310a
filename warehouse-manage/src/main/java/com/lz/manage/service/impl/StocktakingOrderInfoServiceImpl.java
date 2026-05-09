package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.List;
import java.util.Date;
import com.lz.manage.model.domain.StocktakingOrderDetailInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.enums.*;
import com.lz.manage.model.domain.*;
import com.lz.manage.model.dto.inventoryTransactionInfo.InventoryTransactionInfoDto;
import com.lz.manage.service.*;
import com.lz.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.ArrayList;
import com.lz.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.lz.manage.model.domain.StocktakingOrderDetailInfo;
import com.lz.manage.mapper.StocktakingOrderInfoMapper;
import com.lz.manage.model.domain.StocktakingOrderInfo;
import com.lz.manage.service.IStocktakingOrderInfoService;
import com.lz.manage.model.dto.stocktakingOrderInfo.StocktakingOrderInfoQuery;
import com.lz.manage.model.vo.stocktakingOrderInfo.StocktakingOrderInfoVo;

/**
 * 盘点单Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class StocktakingOrderInfoServiceImpl extends ServiceImpl<StocktakingOrderInfoMapper, StocktakingOrderInfo> implements IStocktakingOrderInfoService
{

    @Resource
    private StocktakingOrderInfoMapper stocktakingOrderInfoMapper;

    @Resource
    private IInventoryRecordInfoService inventoryRecordInfoService;

    @Resource
    private IInventoryTransactionInfoService inventoryTransactionInfoService;

    @Resource
    private IWarehouseInfoService warehouseInfoService;

    @Resource
    private ISysUserService userService;

    //region mybatis代码
    /**
     * 查询盘点单
     *
     * @param id 盘点单主键
     * @return 盘点单
     */
    @Override
    public StocktakingOrderInfo selectStocktakingOrderInfoById(Long id)
    {
        return stocktakingOrderInfoMapper.selectStocktakingOrderInfoById(id);
    }

    /**
     * 查询盘点单列表
     *
     * @param stocktakingOrderInfo 盘点单
     * @return 盘点单
     */
    @Override
    public List<StocktakingOrderInfo> selectStocktakingOrderInfoList(StocktakingOrderInfo stocktakingOrderInfo)
    {
        List<StocktakingOrderInfo> stocktakingOrderInfoList = stocktakingOrderInfoMapper.selectStocktakingOrderInfoList(stocktakingOrderInfo);
        for (StocktakingOrderInfo info : stocktakingOrderInfoList) {
            // 获取仓库名称
            if (StringUtils.isNotNull(info.getWarehouseId())) {
                WarehouseInfo warehouseInfo = warehouseInfoService.selectWarehouseInfoById(info.getWarehouseId());
                if (StringUtils.isNotNull(warehouseInfo)) {
                    info.setWarehouseName(warehouseInfo.getWarehouseName());
                }
            }
            // 获取盘点人姓名
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
        }
        return stocktakingOrderInfoList;
    }

    /**
     * 新增盘点单
     *
     * @param stocktakingOrderInfo 盘点单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertStocktakingOrderInfo(StocktakingOrderInfo stocktakingOrderInfo)
    {
        // 校验盘点单号是否存在
        StocktakingOrderInfo stocktakingOrderInfoByNo = stocktakingOrderInfoMapper.selectStocktakingOrderInfoByNo(stocktakingOrderInfo.getStocktakingNo());
        ThrowUtils.throwIf(StringUtils.isNotNull(stocktakingOrderInfoByNo), "盘点单号已存在");

        // 设置默认值
        stocktakingOrderInfo.setStocktakingStatus(WarehouseStocktakingStatusEnum.WAREHOUSE_STOCKTAKING_STATUS_0.getValue());
        stocktakingOrderInfo.setReviewStatus(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_0.getValue());
        stocktakingOrderInfo.setCreateBy(SecurityUtils.getUsername());
        stocktakingOrderInfo.setCreateTime(DateUtils.getNowDate());
        int rows = stocktakingOrderInfoMapper.insertStocktakingOrderInfo(stocktakingOrderInfo);
        insertStocktakingOrderDetailInfo(stocktakingOrderInfo);
        return rows;
    }

    /**
     * 修改盘点单
     *
     * @param stocktakingOrderInfo 盘点单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateStocktakingOrderInfo(StocktakingOrderInfo stocktakingOrderInfo)
    {
        // 判断盘点单是否存在且处于可修改状态
        StocktakingOrderInfo existingOrder = stocktakingOrderInfoMapper.selectStocktakingOrderInfoById(stocktakingOrderInfo.getId());
        ThrowUtils.throwIf(StringUtils.isNull(existingOrder), "盘点单不存在");
        ThrowUtils.throwIf(
                WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue().equals(existingOrder.getReviewStatus()),
                "盘点单已同意，禁止修改");

        // 判断仓库是否存在
        if (StringUtils.isNotNull(stocktakingOrderInfo.getWarehouseId())) {
            WarehouseInfo warehouseInfo = warehouseInfoService.selectWarehouseInfoById(stocktakingOrderInfo.getWarehouseId());
            ThrowUtils.throwIf(StringUtils.isNull(warehouseInfo), "仓库不存在");
        }

        // 校验盘点单号是否存在（排除自身）
        StocktakingOrderInfo stocktakingOrderInfoByNo = stocktakingOrderInfoMapper.selectStocktakingOrderInfoByNo(stocktakingOrderInfo.getStocktakingNo());
        ThrowUtils.throwIf(StringUtils.isNotNull(stocktakingOrderInfoByNo)
                           && !stocktakingOrderInfoByNo.getId().equals(stocktakingOrderInfo.getId()),
                "盘点单号已存在");

        // 校验明细
        validateStocktakingOrderDetailInfo(stocktakingOrderInfo);

        stocktakingOrderInfo.setCreateBy(existingOrder.getCreateBy());
        stocktakingOrderInfo.setCreateTime(existingOrder.getCreateTime());
        stocktakingOrderInfo.setStocktakingNo(existingOrder.getStocktakingNo());
        stocktakingOrderInfo.setUpdateTime(DateUtils.getNowDate());
        stocktakingOrderInfoMapper.deleteStocktakingOrderDetailInfoByStocktakingId(stocktakingOrderInfo.getId());
        insertStocktakingOrderDetailInfo(stocktakingOrderInfo);
        return stocktakingOrderInfoMapper.updateStocktakingOrderInfo(stocktakingOrderInfo);
    }

    /**
     * 校验盘点明细
     */
    private void validateStocktakingOrderDetailInfo(StocktakingOrderInfo stocktakingOrderInfo) {
        List<StocktakingOrderDetailInfo> detailList = stocktakingOrderInfo.getStocktakingOrderDetailInfoList();
        ThrowUtils.throwIf(StringUtils.isNull(detailList) || detailList.isEmpty(), "盘点明细不能为空");

        for (StocktakingOrderDetailInfo detail : detailList) {
            ThrowUtils.throwIf(StringUtils.isEmpty(detail.getPartsCode()), "备件编号不能为空");
            ThrowUtils.throwIf(StringUtils.isNull(detail.getLocationId()), "库位不能为空");
            ThrowUtils.throwIf(StringUtils.isNull(detail.getActualQuantity()), "实盘数量不能为空");
            ThrowUtils.throwIf(detail.getActualQuantity() < 0, "实盘数量不能为负数");
        }
    }

    /**
     * 批量删除盘点单
     *
     * @param ids 需要删除的盘点单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteStocktakingOrderInfoByIds(Long[] ids)
    {
        for (Long id : ids) {
            StocktakingOrderInfo existingOrder = stocktakingOrderInfoMapper.selectStocktakingOrderInfoById(id);
            ThrowUtils.throwIf(StringUtils.isNull(existingOrder), "盘点单不存在");
            // 判断盘点单是否已审核通过
            ThrowUtils.throwIf(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue().equals(existingOrder.getReviewStatus()),
                    "已审核通过的盘点单不可删除");
        }
        stocktakingOrderInfoMapper.deleteStocktakingOrderDetailInfoByStocktakingIds(ids);
        return stocktakingOrderInfoMapper.deleteStocktakingOrderInfoByIds(ids);
    }

    /**
     * 删除盘点单信息
     *
     * @param id 盘点单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteStocktakingOrderInfoById(Long id)
    {
        StocktakingOrderInfo existingOrder = stocktakingOrderInfoMapper.selectStocktakingOrderInfoById(id);
        ThrowUtils.throwIf(StringUtils.isNull(existingOrder), "盘点单不存在");
        // 判断盘点单是否已审核通过
        ThrowUtils.throwIf(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue().equals(existingOrder.getReviewStatus()),
                "已审核通过的盘点单不可删除");
        stocktakingOrderInfoMapper.deleteStocktakingOrderDetailInfoByStocktakingId(id);
        return stocktakingOrderInfoMapper.deleteStocktakingOrderInfoById(id);
    }

    /**
     * 新增盘点明细信息
     *
     * @param stocktakingOrderInfo 盘点单对象
     */
    public void insertStocktakingOrderDetailInfo(StocktakingOrderInfo stocktakingOrderInfo)
    {
        List<StocktakingOrderDetailInfo> stocktakingOrderDetailInfoList = stocktakingOrderInfo.getStocktakingOrderDetailInfoList();
        Long id = stocktakingOrderInfo.getId();
        if (StringUtils.isNotNull(stocktakingOrderDetailInfoList))
        {
            List<StocktakingOrderDetailInfo> list = new ArrayList<StocktakingOrderDetailInfo>();
            for (StocktakingOrderDetailInfo stocktakingOrderDetailInfo : stocktakingOrderDetailInfoList)
            {
                stocktakingOrderDetailInfo.setStocktakingId(id);
                if (StringUtils.isNotEmpty(stocktakingOrderInfo.getCreateBy())) {
                    stocktakingOrderDetailInfo.setCreateBy(stocktakingOrderInfo.getCreateBy());
                }
                if (StringUtils.isNotNull(stocktakingOrderInfo.getCreateTime())) {
                    stocktakingOrderDetailInfo.setCreateTime(stocktakingOrderInfo.getCreateTime());
                }
                if (StringUtils.isNotEmpty(stocktakingOrderInfo.getUpdateBy())) {
                    stocktakingOrderDetailInfo.setUpdateBy(stocktakingOrderInfo.getUpdateBy());
                }
                if (StringUtils.isNotNull(stocktakingOrderInfo.getUpdateTime())) {
                    stocktakingOrderDetailInfo.setUpdateTime(stocktakingOrderInfo.getUpdateTime());
                }
                list.add(stocktakingOrderDetailInfo);
            }
            if (!list.isEmpty())
            {
                stocktakingOrderInfoMapper.batchStocktakingOrderDetailInfo(list);
            }
        }
    }
    //endregion
    @Override
    public QueryWrapper<StocktakingOrderInfo> getQueryWrapper(StocktakingOrderInfoQuery stocktakingOrderInfoQuery){
        QueryWrapper<StocktakingOrderInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = stocktakingOrderInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = stocktakingOrderInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String stocktakingNo = stocktakingOrderInfoQuery.getStocktakingNo();
        queryWrapper.eq(StringUtils.isNotEmpty(stocktakingNo) ,"stocktaking_no",stocktakingNo);

        String stocktakingType = stocktakingOrderInfoQuery.getStocktakingType();
        queryWrapper.eq(StringUtils.isNotEmpty(stocktakingType) ,"stocktaking_type",stocktakingType);

        Long warehouseId = stocktakingOrderInfoQuery.getWarehouseId();
        queryWrapper.eq( StringUtils.isNotNull(warehouseId),"warehouse_id",warehouseId);

        String stocktakingStatus = stocktakingOrderInfoQuery.getStocktakingStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(stocktakingStatus) ,"stocktaking_status",stocktakingStatus);

        String reviewStatus = stocktakingOrderInfoQuery.getReviewStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(reviewStatus) ,"review_status",reviewStatus);

        String createBy = stocktakingOrderInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = stocktakingOrderInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<StocktakingOrderInfoVo> convertVoList(List<StocktakingOrderInfo> stocktakingOrderInfoList) {
        if (StringUtils.isEmpty(stocktakingOrderInfoList)) {
            return Collections.emptyList();
        }
        return stocktakingOrderInfoList.stream().map(StocktakingOrderInfoVo::objToVo).collect(Collectors.toList());
    }

    /**
     * 审核盘点单
     */
    @Override
    @Transactional
    public int auditStocktakingOrderInfo(StocktakingOrderInfo stocktakingOrderInfo) {
        // 判断盘点单是否存在
        StocktakingOrderInfo existingOrder = stocktakingOrderInfoMapper.selectStocktakingOrderInfoById(stocktakingOrderInfo.getId());
        ThrowUtils.throwIf(StringUtils.isNull(existingOrder), "盘点单不存在");

        // 判断盘点单是否已审核
        ThrowUtils.throwIf(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue().equals(existingOrder.getReviewStatus()),
                "盘点单已同意，不可重复审核");

        Date nowDate = DateUtils.getNowDate();

        // 如果审核状态发生变化，设置审核人和审核时间
        if (!stocktakingOrderInfo.getReviewStatus().equals(existingOrder.getReviewStatus())) {
            stocktakingOrderInfo.setReviewerId(SecurityUtils.getUserId());
            stocktakingOrderInfo.setReviewTime(nowDate);
        }

        // 如果审核同意，同时更新盘点状态为已完成
        if (WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue().equals(stocktakingOrderInfo.getReviewStatus())) {
            stocktakingOrderInfo.setStocktakingStatus(WarehouseStocktakingStatusEnum.WAREHOUSE_STOCKTAKING_STATUS_1.getValue());

            // 处理库存和流水
            processStocktakingInventory(stocktakingOrderInfo, existingOrder, nowDate);
        }

        // 如果审核拒绝，更新盘点状态为已取消
        if (WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_2.getValue().equals(stocktakingOrderInfo.getReviewStatus())) {
            stocktakingOrderInfo.setStocktakingStatus(WarehouseStocktakingStatusEnum.WAREHOUSE_STOCKTAKING_STATUS_2.getValue());
        }

        // 删除原有明细并插入新明细
        stocktakingOrderInfoMapper.deleteStocktakingOrderDetailInfoByStocktakingId(stocktakingOrderInfo.getId());
        insertStocktakingOrderDetailInfo(stocktakingOrderInfo);

        stocktakingOrderInfo.setCreateBy(existingOrder.getCreateBy());
        stocktakingOrderInfo.setCreateTime(existingOrder.getCreateTime());
        stocktakingOrderInfo.setStocktakingNo(existingOrder.getStocktakingNo());
        stocktakingOrderInfo.setUpdateTime(nowDate);
        return stocktakingOrderInfoMapper.updateStocktakingOrderInfo(stocktakingOrderInfo);
    }

    /**
     * 处理盘点审核时的库存更新和流水创建
     */
    private void processStocktakingInventory(StocktakingOrderInfo stocktakingOrderInfo, StocktakingOrderInfo existingOrder, Date nowDate) {
        List<StocktakingOrderDetailInfo> detailList = stocktakingOrderInfo.getStocktakingOrderDetailInfoList();
        if (StringUtils.isNull(detailList) || detailList.isEmpty()) {
            return;
        }
        List<InventoryTransactionInfoDto> transactionInfoDtos = new ArrayList<>();


        for (StocktakingOrderDetailInfo detail : detailList) {
            InventoryTransactionInfoDto transactionInfoDto = getInventoryTransactionInfoDto(stocktakingOrderInfo, nowDate, detail);
            if (transactionInfoDto == null) continue;
            transactionInfoDtos.add(transactionInfoDto);
        }
        inventoryTransactionInfoService.insertInventoryTransactionInfos(transactionInfoDtos);
    }

    private InventoryTransactionInfoDto getInventoryTransactionInfoDto(StocktakingOrderInfo stocktakingOrderInfo, Date nowDate, StocktakingOrderDetailInfo detail) {
        // 根据备件编码、仓库、库位、批次号查询库存记录
        InventoryRecordInfo inventoryRecord = inventoryRecordInfoService.selectInventoryRecordInfoByKey(
                detail.getPartsCode(),
                stocktakingOrderInfo.getWarehouseId(),
                detail.getLocationId(),
                detail.getBatchNo()
        );

        if (StringUtils.isNull(inventoryRecord)) {
            // 如果没有找到库存记录，跳过
            return null;
        }

        // 计算差异数量
        long difference = (detail.getActualQuantity() != null ? detail.getActualQuantity() : 0)
                - (inventoryRecord.getQuantity() != null ? inventoryRecord.getQuantity() : 0);

        // 如果差异为0，不需要创建流水
        if (difference == 0) {
            return null;
        }

        InventoryTransactionInfoDto transactionInfoDto = new InventoryTransactionInfoDto();
        transactionInfoDto.setPartsCode(detail.getPartsCode());
        transactionInfoDto.setWarehouseId(stocktakingOrderInfo.getWarehouseId());
        transactionInfoDto.setLocationId(detail.getLocationId());
        transactionInfoDto.setBatchNo(detail.getBatchNo());
        // 出库流水类型
        transactionInfoDto.setTransactionType(WarehouseTransactionTypeEnum.WAREHOUSE_TRANSACTION_TYPE_2.getValue());
        transactionInfoDto.setRelatedOrderNo(stocktakingOrderInfo.getStocktakingNo());
        transactionInfoDto.setOperatorId(stocktakingOrderInfo.getOperatorId());
        transactionInfoDto.setTransactionTime(stocktakingOrderInfo.getStartDate());
        transactionInfoDto.setCreateBy(stocktakingOrderInfo.getCreateBy());
        transactionInfoDto.setCreateTime(nowDate);
        // 变动数量为负数（出库）
        transactionInfoDto.setChangeQuantity(difference);
        return transactionInfoDto;
    }
}
