package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.InventoryTransactionInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 库存流水Mapper接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface InventoryTransactionInfoMapper extends BaseMapper<InventoryTransactionInfo>
{
    /**
     * 查询库存流水
     * 
     * @param id 库存流水主键
     * @return 库存流水
     */
    public InventoryTransactionInfo selectInventoryTransactionInfoById(Long id);

    /**
     * 查询库存流水列表
     * 
     * @param inventoryTransactionInfo 库存流水
     * @return 库存流水集合
     */
    public List<InventoryTransactionInfo> selectInventoryTransactionInfoList(InventoryTransactionInfo inventoryTransactionInfo);

    /**
     * 新增库存流水
     * 
     * @param inventoryTransactionInfo 库存流水
     * @return 结果
     */
    public int insertInventoryTransactionInfo(InventoryTransactionInfo inventoryTransactionInfo);

    /**
     * 修改库存流水
     * 
     * @param inventoryTransactionInfo 库存流水
     * @return 结果
     */
    public int updateInventoryTransactionInfo(InventoryTransactionInfo inventoryTransactionInfo);

    /**
     * 删除库存流水
     * 
     * @param id 库存流水主键
     * @return 结果
     */
    public int deleteInventoryTransactionInfoById(Long id);

    /**
     * 批量删除库存流水
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInventoryTransactionInfoByIds(Long[] ids);

    /**
     * 统计库位总入库数量
     */
    public Long sumInboundByLocation(Long locationId);

    /**
     * 统计库位总出库数量
     */
    public Long sumOutboundByLocation(Long locationId);

    /**
     * 统计仓库总入库数量
     */
    public Long sumInboundByWarehouse(Long warehouseId);

    /**
     * 统计仓库总出库数量
     */
    public Long sumOutboundByWarehouse(Long warehouseId);

    /**
     * 统计备件当前库存
     */
    public Long sumStockByPartsCode(String partsCode);
}
