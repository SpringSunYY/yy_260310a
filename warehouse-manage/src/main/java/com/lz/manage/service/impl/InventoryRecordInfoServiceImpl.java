package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.InventoryRecordInfoMapper;
import com.lz.manage.model.domain.InventoryRecordInfo;
import com.lz.manage.service.IInventoryRecordInfoService;
import com.lz.manage.model.dto.inventoryRecordInfo.InventoryRecordInfoQuery;
import com.lz.manage.model.vo.inventoryRecordInfo.InventoryRecordInfoVo;

/**
 * 库存记录Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class InventoryRecordInfoServiceImpl extends ServiceImpl<InventoryRecordInfoMapper, InventoryRecordInfo> implements IInventoryRecordInfoService
{

    @Resource
    private InventoryRecordInfoMapper inventoryRecordInfoMapper;

    //region mybatis代码
    /**
     * 查询库存记录
     *
     * @param id 库存记录主键
     * @return 库存记录
     */
    @Override
    public InventoryRecordInfo selectInventoryRecordInfoById(Long id)
    {
        return inventoryRecordInfoMapper.selectInventoryRecordInfoById(id);
    }

    /**
     * 查询库存记录列表
     *
     * @param inventoryRecordInfo 库存记录
     * @return 库存记录
     */
    @Override
    public List<InventoryRecordInfo> selectInventoryRecordInfoList(InventoryRecordInfo inventoryRecordInfo)
    {
        return inventoryRecordInfoMapper.selectInventoryRecordInfoList(inventoryRecordInfo);
    }

    /**
     * 新增库存记录
     *
     * @param inventoryRecordInfo 库存记录
     * @return 结果
     */
    @Override
    public int insertInventoryRecordInfo(InventoryRecordInfo inventoryRecordInfo)
    {
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
    public int updateInventoryRecordInfo(InventoryRecordInfo inventoryRecordInfo)
    {
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
    public int deleteInventoryRecordInfoByIds(Long[] ids)
    {
        return inventoryRecordInfoMapper.deleteInventoryRecordInfoByIds(ids);
    }

    /**
     * 删除库存记录信息
     *
     * @param id 库存记录主键
     * @return 结果
     */
    @Override
    public int deleteInventoryRecordInfoById(Long id)
    {
        return inventoryRecordInfoMapper.deleteInventoryRecordInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<InventoryRecordInfo> getQueryWrapper(InventoryRecordInfoQuery inventoryRecordInfoQuery){
        QueryWrapper<InventoryRecordInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = inventoryRecordInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = inventoryRecordInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String partsCode = inventoryRecordInfoQuery.getPartsCode();
        queryWrapper.eq(StringUtils.isNotEmpty(partsCode) ,"parts_code",partsCode);

        Long warehouseId = inventoryRecordInfoQuery.getWarehouseId();
        queryWrapper.eq( StringUtils.isNotNull(warehouseId),"warehouse_id",warehouseId);

        Long locationId = inventoryRecordInfoQuery.getLocationId();
        queryWrapper.eq( StringUtils.isNotNull(locationId),"location_id",locationId);

        String createBy = inventoryRecordInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = inventoryRecordInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

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
