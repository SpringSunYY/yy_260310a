package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.InventoryRecordInfoMapper;
import com.lz.manage.model.domain.InventoryRecordInfo;
import com.lz.manage.model.domain.LocationInfo;
import com.lz.manage.model.domain.WarehouseInfo;
import com.lz.manage.model.dto.inventoryRecordInfo.InventoryRecordInfoQuery;
import com.lz.manage.model.vo.inventoryRecordInfo.InventoryRecordInfoVo;
import com.lz.manage.service.IInventoryRecordInfoService;
import com.lz.manage.service.ILocationInfoService;
import com.lz.manage.service.IWarehouseInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 库存记录Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class InventoryRecordInfoServiceImpl extends ServiceImpl<InventoryRecordInfoMapper, InventoryRecordInfo> implements IInventoryRecordInfoService {

    @Resource
    private InventoryRecordInfoMapper inventoryRecordInfoMapper;

    @Resource
    private IWarehouseInfoService warehouseInfoService;

    @Resource
    private ILocationInfoService locationInfoService;

    //region mybatis代码

    /**
     * 查询库存记录
     *
     * @param id 库存记录主键
     * @return 库存记录
     */
    @Override
    public InventoryRecordInfo selectInventoryRecordInfoById(Long id) {
        return inventoryRecordInfoMapper.selectInventoryRecordInfoById(id);
    }

    /**
     * 查询库存记录列表
     *
     * @param inventoryRecordInfo 库存记录
     * @return 库存记录
     */
    @Override
    public List<InventoryRecordInfo> selectInventoryRecordInfoList(InventoryRecordInfo inventoryRecordInfo) {
        List<InventoryRecordInfo> inventoryRecordInfos = inventoryRecordInfoMapper.selectInventoryRecordInfoList(inventoryRecordInfo);
        for (InventoryRecordInfo info : inventoryRecordInfos) {
            WarehouseInfo warehouseInfo = warehouseInfoService.selectWarehouseInfoById(info.getWarehouseId());
            if (StringUtils.isNotNull(warehouseInfo)) {
                info.setWarehouseName(warehouseInfo.getWarehouseName());
            }
            LocationInfo locationInfo = locationInfoService.selectLocationInfoById(info.getLocationId());
            if (StringUtils.isNotNull(locationInfo)) {
                info.setLocationName(locationInfo.getLocationName());
            }
        }
        return inventoryRecordInfos;
    }

    /**
     * 新增库存记录
     *
     * @param inventoryRecordInfo 库存记录
     * @return 结果
     */
    @Override
    public int insertInventoryRecordInfo(InventoryRecordInfo inventoryRecordInfo) {
        inventoryRecordInfo.setCreateTime(DateUtils.getNowDate());
        return inventoryRecordInfoMapper.insertInventoryRecordInfo(inventoryRecordInfo);
    }

    /**
     * 修改库存记录
     *
     * @param inventoryRecordInfo 库存记录
     * @return 结果
     */
    @Override
    public int updateInventoryRecordInfo(InventoryRecordInfo inventoryRecordInfo) {
        inventoryRecordInfo.setUpdateTime(DateUtils.getNowDate());
        return inventoryRecordInfoMapper.updateInventoryRecordInfo(inventoryRecordInfo);
    }

    /**
     * 批量删除库存记录
     *
     * @param ids 需要删除的库存记录主键
     * @return 结果
     */
    @Override
    public int deleteInventoryRecordInfoByIds(Long[] ids) {
        return inventoryRecordInfoMapper.deleteInventoryRecordInfoByIds(ids);
    }

    /**
     * 删除库存记录信息
     *
     * @param id 库存记录主键
     * @return 结果
     */
    @Override
    public int deleteInventoryRecordInfoById(Long id) {
        return inventoryRecordInfoMapper.deleteInventoryRecordInfoById(id);
    }

    /**
     * 查询库存记录（按备件编码、仓库、库位、批次号）
     */
    @Override
    public InventoryRecordInfo selectInventoryRecordInfoByKey(String partsCode, Long warehouseId, Long locationId, String batchNo) {
        return inventoryRecordInfoMapper.selectInventoryRecordInfoByKey(partsCode, warehouseId, locationId, batchNo);
    }

    //endregion
    @Override
    public QueryWrapper<InventoryRecordInfo> getQueryWrapper(InventoryRecordInfoQuery inventoryRecordInfoQuery) {
        QueryWrapper<InventoryRecordInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = inventoryRecordInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = inventoryRecordInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        String partsCode = inventoryRecordInfoQuery.getPartsCode();
        queryWrapper.eq(StringUtils.isNotEmpty(partsCode), "parts_code", partsCode);

        Long warehouseId = inventoryRecordInfoQuery.getWarehouseId();
        queryWrapper.eq(StringUtils.isNotNull(warehouseId), "warehouse_id", warehouseId);

        Long locationId = inventoryRecordInfoQuery.getLocationId();
        queryWrapper.eq(StringUtils.isNotNull(locationId), "location_id", locationId);

        String createBy = inventoryRecordInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy), "create_by", createBy);

        Date createTime = inventoryRecordInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<InventoryRecordInfoVo> convertVoList(List<InventoryRecordInfo> inventoryRecordInfoList) {
        if (StringUtils.isEmpty(inventoryRecordInfoList)) {
            return Collections.emptyList();
        }
        return inventoryRecordInfoList.stream().map(InventoryRecordInfoVo::objToVo).collect(Collectors.toList());
    }
}
