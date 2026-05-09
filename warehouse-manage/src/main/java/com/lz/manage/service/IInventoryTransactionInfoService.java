package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.InventoryTransactionInfo;
import com.lz.manage.model.dto.inventoryTransactionInfo.InventoryTransactionInfoDto;
import com.lz.manage.model.vo.inventoryTransactionInfo.InventoryTransactionInfoVo;
import com.lz.manage.model.dto.inventoryTransactionInfo.InventoryTransactionInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 库存流水Service接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface IInventoryTransactionInfoService extends IService<InventoryTransactionInfo>
{
    //region mybatis代码
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
     * 批量新增库存流水
     *
     * @param transactionInfoDtos 库存流水DTO集合
     */
    void insertInventoryTransactionInfos(List<InventoryTransactionInfoDto> transactionInfoDtos);

    /**
     * 修改库存流水
     * 
     * @param inventoryTransactionInfo 库存流水
     * @return 结果
     */
    public int updateInventoryTransactionInfo(InventoryTransactionInfo inventoryTransactionInfo);

    /**
     * 批量删除库存流水
     * 
     * @param ids 需要删除的库存流水主键集合
     * @return 结果
     */
    public int deleteInventoryTransactionInfoByIds(Long[] ids);

    /**
     * 删除库存流水信息
     * 
     * @param id 库存流水主键
     * @return 结果
     */
    public int deleteInventoryTransactionInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param inventoryTransactionInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<InventoryTransactionInfo> getQueryWrapper(InventoryTransactionInfoQuery inventoryTransactionInfoQuery);

    /**
     * 转换vo
     *
     * @param inventoryTransactionInfoList InventoryTransactionInfo集合
     * @return InventoryTransactionInfoVO集合
     */
    List<InventoryTransactionInfoVo> convertVoList(List<InventoryTransactionInfo> inventoryTransactionInfoList);
}
