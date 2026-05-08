package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.InventoryRecordInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 库存记录Mapper接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface InventoryRecordInfoMapper extends BaseMapper<InventoryRecordInfo>
{
    /**
     * 查询库存记录
     * 
     * @param id 库存记录主键
     * @return 库存记录
     */
    public InventoryRecordInfo selectInventoryRecordInfoById(Long id);

    /**
     * 查询库存记录列表
     * 
     * @param inventoryRecordInfo 库存记录
     * @return 库存记录集合
     */
    public List<InventoryRecordInfo> selectInventoryRecordInfoList(InventoryRecordInfo inventoryRecordInfo);

    /**
     * 新增库存记录
     * 
     * @param inventoryRecordInfo 库存记录
     * @return 结果
     */
    public int insertInventoryRecordInfo(InventoryRecordInfo inventoryRecordInfo);

    /**
     * 修改库存记录
     * 
     * @param inventoryRecordInfo 库存记录
     * @return 结果
     */
    public int updateInventoryRecordInfo(InventoryRecordInfo inventoryRecordInfo);

    /**
     * 删除库存记录
     * 
     * @param id 库存记录主键
     * @return 结果
     */
    public int deleteInventoryRecordInfoById(Long id);

    /**
     * 批量删除库存记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInventoryRecordInfoByIds(Long[] ids);
}
