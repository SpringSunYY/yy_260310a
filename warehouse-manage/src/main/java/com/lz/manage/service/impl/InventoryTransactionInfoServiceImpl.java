package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.InventoryTransactionInfoMapper;
import com.lz.manage.model.domain.*;
import com.lz.manage.model.dto.inventoryTransactionInfo.InventoryTransactionInfoDto;
import com.lz.manage.model.dto.inventoryTransactionInfo.InventoryTransactionInfoQuery;
import com.lz.manage.model.vo.inventoryTransactionInfo.InventoryTransactionInfoVo;
import com.lz.manage.service.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 库存流水Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class InventoryTransactionInfoServiceImpl extends ServiceImpl<InventoryTransactionInfoMapper, InventoryTransactionInfo> implements IInventoryTransactionInfoService {

    @Resource
    private InventoryTransactionInfoMapper inventoryTransactionInfoMapper;

    @Resource
    private IInventoryRecordInfoService inventoryRecordInfoService;

    @Resource
    private ILocationInfoService locationInfoService;

    @Resource
    private IWarehouseInfoService warehouseInfoService;

    @Resource
    private ISparePartsInfoService sparePartsInfoService;

    //region mybatis代码

    /**
     * 查询库存流水
     *
     * @param id 库存流水主键
     * @return 库存流水
     */
    @Override
    public InventoryTransactionInfo selectInventoryTransactionInfoById(Long id) {
        return inventoryTransactionInfoMapper.selectInventoryTransactionInfoById(id);
    }

    /**
     * 查询库存流水列表
     *
     * @param inventoryTransactionInfo 库存流水
     * @return 库存流水
     */
    @Override
    public List<InventoryTransactionInfo> selectInventoryTransactionInfoList(InventoryTransactionInfo inventoryTransactionInfo) {
        List<InventoryTransactionInfo> inventoryTransactionInfos = inventoryTransactionInfoMapper.selectInventoryTransactionInfoList(inventoryTransactionInfo);
        for (InventoryTransactionInfo info : inventoryTransactionInfos) {
            WarehouseInfo warehouseInfo = warehouseInfoService.selectWarehouseInfoById(info.getWarehouseId());
            if (StringUtils.isNotNull(warehouseInfo)) {
                info.setWarehouseName(warehouseInfo.getWarehouseName());
            }
            LocationInfo locationInfo = locationInfoService.selectLocationInfoById(info.getLocationId());
            if (StringUtils.isNotNull(locationInfo)) {
                info.setLocationName(locationInfo.getLocationName());
            }
        }
        return inventoryTransactionInfos;
    }

    /**
     * 新增库存流水
     *
     * @param inventoryTransactionInfo 库存流水
     * @return 结果
     */
    @Override
    public int insertInventoryTransactionInfo(InventoryTransactionInfo inventoryTransactionInfo) {
        inventoryTransactionInfo.setCreateTime(DateUtils.getNowDate());
        return inventoryTransactionInfoMapper.insertInventoryTransactionInfo(inventoryTransactionInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertInventoryTransactionInfos(List<InventoryTransactionInfoDto> transactionInfoDtos) {
        if (transactionInfoDtos == null || transactionInfoDtos.isEmpty()) {
            return;
        }

        Date nowDate = DateUtils.getNowDate();

        for (InventoryTransactionInfoDto dto : transactionInfoDtos) {
            Long changeQuantity = dto.getChangeQuantity();
            // 变动数量为0不处理
            if (changeQuantity == null || changeQuantity == 0) {
                continue;
            }

            // 查询库存记录
            InventoryRecordInfo record = inventoryRecordInfoService.selectInventoryRecordInfoByKey(
                    dto.getPartsCode(), dto.getWarehouseId(), dto.getLocationId(), dto.getBatchNo());

            long beforeQuantity = 0L;
            Long afterQuantity;

            if (record == null) {
                // 不存在则新增
                if (changeQuantity < 0) {
                    // 出库时库存记录不存在，提示数量不足
                    ThrowUtils.throwServiceException("库存不足：备件[" + dto.getPartsCode() + "]在指定库位无可用库存");
                }

                record = new InventoryRecordInfo();
                record.setPartsCode(dto.getPartsCode());
                record.setWarehouseId(dto.getWarehouseId());
                record.setLocationId(dto.getLocationId());
                record.setQuantity(changeQuantity);
                record.setFrozenQuantity(0L);
                record.setAvailableQuantity(changeQuantity);
                record.setLastInboundDate(nowDate);
                record.setCreateTime(nowDate);
                record.setBatchNo(dto.getBatchNo());
                record.setExpiryDate(dto.getExpiryDate());
                if (StringUtils.isNotEmpty(dto.getCreateBy())) {
                    record.setCreateBy(dto.getCreateBy());
                }
                record.setCreateBy(dto.getCreateBy());
                record.setCreateTime(nowDate);
                inventoryRecordInfoService.insertInventoryRecordInfo(record);

                afterQuantity = changeQuantity;
            } else {
                // 存在则更新
                beforeQuantity = record.getAvailableQuantity() != null ? record.getAvailableQuantity() : 0L;
                afterQuantity = beforeQuantity + changeQuantity;

                if (afterQuantity < 0) {
                    ThrowUtils.throwServiceException("库存不足：备件[" + dto.getPartsCode() + "]当前可用数量为" + beforeQuantity + "，需要出库" + Math.abs(changeQuantity));
                }

                record.setQuantity(afterQuantity);
                record.setAvailableQuantity(afterQuantity);
                record.setUpdateBy(dto.getCreateBy());
                record.setUpdateTime(nowDate);
                inventoryRecordInfoService.updateInventoryRecordInfo(record);
            }

            // 更新库位统计
            updateLocationStats(dto.getWarehouseId(), dto.getLocationId(), changeQuantity, nowDate);

            // 更新仓库统计
            updateWarehouseStats(dto.getWarehouseId(), changeQuantity, nowDate);

            // 更新备件库存
            updateSparePartsStock(dto.getPartsCode(), changeQuantity, nowDate);

            // 新增库存流水记录
            InventoryTransactionInfo transaction = new InventoryTransactionInfo();
            transaction.setPartsCode(dto.getPartsCode());
            transaction.setWarehouseId(dto.getWarehouseId());
            transaction.setLocationId(dto.getLocationId());
            transaction.setBatchNo(dto.getBatchNo());
            transaction.setTransactionType(dto.getTransactionType());
            transaction.setChangeQuantity(changeQuantity);
            transaction.setBeforeQuantity(beforeQuantity);
            transaction.setAfterQuantity(afterQuantity);
            transaction.setRelatedOrderNo(dto.getRelatedOrderNo());
            transaction.setOperatorId(dto.getOperatorId());
            transaction.setTransactionTime(dto.getTransactionTime() != null ? dto.getTransactionTime() : nowDate);
            transaction.setRemark(dto.getRemark());
            transaction.setCreateBy(dto.getCreateBy());
            transaction.setCreateTime(nowDate);
            inventoryTransactionInfoMapper.insertInventoryTransactionInfo(transaction);
        }
    }

    /**
     * 更新库位统计
     */
    private void updateLocationStats(Long warehouseId, Long locationId, Long changeQuantity, Date nowDate) {
        if (locationId == null) {
            return;
        }
        LocationInfo location = locationInfoService.selectLocationInfoById(locationId);
        if (location != null) {
            Long currentQuantity = location.getCurrentQuantity() != null ? location.getCurrentQuantity() : 0L;
            if (changeQuantity > 0) {
                // 入库
                Long inboundQuantity = location.getInboundQuantity() != null ? location.getInboundQuantity() : 0L;
                location.setInboundQuantity(inboundQuantity + changeQuantity);
            } else {
                // 出库
                Long outboundQuantity = location.getOutboundQuantity() != null ? location.getOutboundQuantity() : 0L;
                location.setOutboundQuantity(outboundQuantity + Math.abs(changeQuantity));
            }
            location.setCurrentQuantity(currentQuantity + changeQuantity);
            location.setUpdateTime(nowDate);
            locationInfoService.updateLocationInfo(location);
        }
    }

    /**
     * 更新仓库统计
     */
    private void updateWarehouseStats(Long warehouseId, Long changeQuantity, Date nowDate) {
        if (warehouseId == null) {
            return;
        }
        WarehouseInfo warehouse = warehouseInfoService.selectWarehouseInfoById(warehouseId);
        if (warehouse != null) {
            Long currentQuantity = warehouse.getCurrentQuantity() != null ? warehouse.getCurrentQuantity() : 0L;
            if (changeQuantity > 0) {
                Long inboundQuantity = warehouse.getInboundQuantity() != null ? warehouse.getInboundQuantity() : 0L;
                warehouse.setInboundQuantity(inboundQuantity + changeQuantity);
            } else {
                Long outboundQuantity = warehouse.getOutboundQuantity() != null ? warehouse.getOutboundQuantity() : 0L;
                warehouse.setOutboundQuantity(outboundQuantity + Math.abs(changeQuantity));
            }
            warehouse.setCurrentQuantity(currentQuantity + changeQuantity);
            warehouse.setUpdateTime(nowDate);
            warehouseInfoService.updateWarehouseInfo(warehouse);
        }
    }

    /**
     * 更新备件库存
     */
    private void updateSparePartsStock(String partsCode, Long changeQuantity, Date nowDate) {
        if (StringUtils.isEmpty(partsCode)) {
            return;
        }
        SparePartsInfo query = new SparePartsInfo();
        query.setPartsCode(partsCode);
        List<SparePartsInfo> list = sparePartsInfoService.selectSparePartsInfoList(query);
        SparePartsInfo spareParts = list != null && !list.isEmpty() ? list.get(0) : null;
        if (spareParts != null) {
            Long currentStock = spareParts.getCurrentStock() != null ? spareParts.getCurrentStock() : 0L;
            spareParts.setCurrentStock(currentStock + changeQuantity);
            spareParts.setUpdateTime(nowDate);
            sparePartsInfoService.updateSparePartsInfo(spareParts);
        }
    }

    /**
     * 修改库存流水
     *
     * @param inventoryTransactionInfo 库存流水
     * @return 结果
     */
    @Override
    public int updateInventoryTransactionInfo(InventoryTransactionInfo inventoryTransactionInfo) {
        inventoryTransactionInfo.setUpdateTime(DateUtils.getNowDate());
        return inventoryTransactionInfoMapper.updateInventoryTransactionInfo(inventoryTransactionInfo);
    }

    /**
     * 批量删除库存流水
     *
     * @param ids 需要删除的库存流水主键
     * @return 结果
     */
    @Override
    public int deleteInventoryTransactionInfoByIds(Long[] ids) {
        return inventoryTransactionInfoMapper.deleteInventoryTransactionInfoByIds(ids);
    }

    /**
     * 删除库存流水信息
     *
     * @param id 库存流水主键
     * @return 结果
     */
    @Override
    public int deleteInventoryTransactionInfoById(Long id) {
        return inventoryTransactionInfoMapper.deleteInventoryTransactionInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<InventoryTransactionInfo> getQueryWrapper(InventoryTransactionInfoQuery inventoryTransactionInfoQuery) {
        QueryWrapper<InventoryTransactionInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = inventoryTransactionInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        String partsCode = inventoryTransactionInfoQuery.getPartsCode();
        queryWrapper.eq(StringUtils.isNotEmpty(partsCode), "parts_code", partsCode);

        Long warehouseId = inventoryTransactionInfoQuery.getWarehouseId();
        queryWrapper.eq(StringUtils.isNotNull(warehouseId), "warehouse_id", warehouseId);

        Long locationId = inventoryTransactionInfoQuery.getLocationId();
        queryWrapper.eq(StringUtils.isNotNull(locationId), "location_id", locationId);

        String batchNo = inventoryTransactionInfoQuery.getBatchNo();
        queryWrapper.eq(StringUtils.isNotEmpty(batchNo), "batch_no", batchNo);

        String transactionType = inventoryTransactionInfoQuery.getTransactionType();
        queryWrapper.eq(StringUtils.isNotEmpty(transactionType), "transaction_type", transactionType);

        String relatedOrderNo = inventoryTransactionInfoQuery.getRelatedOrderNo();
        queryWrapper.like(StringUtils.isNotEmpty(relatedOrderNo), "related_order_no", relatedOrderNo);

        String createBy = inventoryTransactionInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy), "create_by", createBy);

        Date createTime = inventoryTransactionInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<InventoryTransactionInfoVo> convertVoList(List<InventoryTransactionInfo> inventoryTransactionInfoList) {
        if (StringUtils.isEmpty(inventoryTransactionInfoList)) {
            return Collections.emptyList();
        }
        return inventoryTransactionInfoList.stream().map(InventoryTransactionInfoVo::objToVo).collect(Collectors.toList());
    }
}
