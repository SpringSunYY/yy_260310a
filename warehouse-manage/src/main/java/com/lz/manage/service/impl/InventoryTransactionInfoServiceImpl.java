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
import com.lz.manage.mapper.InventoryTransactionInfoMapper;
import com.lz.manage.model.domain.InventoryTransactionInfo;
import com.lz.manage.service.IInventoryTransactionInfoService;
import com.lz.manage.model.dto.inventoryTransactionInfo.InventoryTransactionInfoQuery;
import com.lz.manage.model.vo.inventoryTransactionInfo.InventoryTransactionInfoVo;

/**
 * 库存流水Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class InventoryTransactionInfoServiceImpl extends ServiceImpl<InventoryTransactionInfoMapper, InventoryTransactionInfo> implements IInventoryTransactionInfoService
{

    @Resource
    private InventoryTransactionInfoMapper inventoryTransactionInfoMapper;

    //region mybatis代码
    /**
     * 查询库存流水
     *
     * @param id 库存流水主键
     * @return 库存流水
     */
    @Override
    public InventoryTransactionInfo selectInventoryTransactionInfoById(Long id)
    {
        return inventoryTransactionInfoMapper.selectInventoryTransactionInfoById(id);
    }

    /**
     * 查询库存流水列表
     *
     * @param inventoryTransactionInfo 库存流水
     * @return 库存流水
     */
    @Override
    public List<InventoryTransactionInfo> selectInventoryTransactionInfoList(InventoryTransactionInfo inventoryTransactionInfo)
    {
        return inventoryTransactionInfoMapper.selectInventoryTransactionInfoList(inventoryTransactionInfo);
    }

    /**
     * 新增库存流水
     *
     * @param inventoryTransactionInfo 库存流水
     * @return 结果
     */
    @Override
    public int insertInventoryTransactionInfo(InventoryTransactionInfo inventoryTransactionInfo)
    {
        inventoryTransactionInfo.setCreateTime(DateUtils.getNowDate());
        return inventoryTransactionInfoMapper.insertInventoryTransactionInfo(inventoryTransactionInfo);
    }

    /**
     * 修改库存流水
     *
     * @param inventoryTransactionInfo 库存流水
     * @return 结果
     */
    @Override
    public int updateInventoryTransactionInfo(InventoryTransactionInfo inventoryTransactionInfo)
    {
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
    public int deleteInventoryTransactionInfoByIds(Long[] ids)
    {
        return inventoryTransactionInfoMapper.deleteInventoryTransactionInfoByIds(ids);
    }

    /**
     * 删除库存流水信息
     *
     * @param id 库存流水主键
     * @return 结果
     */
    @Override
    public int deleteInventoryTransactionInfoById(Long id)
    {
        return inventoryTransactionInfoMapper.deleteInventoryTransactionInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<InventoryTransactionInfo> getQueryWrapper(InventoryTransactionInfoQuery inventoryTransactionInfoQuery){
        QueryWrapper<InventoryTransactionInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = inventoryTransactionInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        String partsCode = inventoryTransactionInfoQuery.getPartsCode();
        queryWrapper.eq(StringUtils.isNotEmpty(partsCode) ,"parts_code",partsCode);

        Long warehouseId = inventoryTransactionInfoQuery.getWarehouseId();
        queryWrapper.eq( StringUtils.isNotNull(warehouseId),"warehouse_id",warehouseId);

        Long locationId = inventoryTransactionInfoQuery.getLocationId();
        queryWrapper.eq( StringUtils.isNotNull(locationId),"location_id",locationId);

        String batchNo = inventoryTransactionInfoQuery.getBatchNo();
        queryWrapper.eq(StringUtils.isNotEmpty(batchNo) ,"batch_no",batchNo);

        String transactionType = inventoryTransactionInfoQuery.getTransactionType();
        queryWrapper.eq(StringUtils.isNotEmpty(transactionType) ,"transaction_type",transactionType);

        String relatedOrderNo = inventoryTransactionInfoQuery.getRelatedOrderNo();
        queryWrapper.like(StringUtils.isNotEmpty(relatedOrderNo) ,"related_order_no",relatedOrderNo);

        String createBy = inventoryTransactionInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = inventoryTransactionInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

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
