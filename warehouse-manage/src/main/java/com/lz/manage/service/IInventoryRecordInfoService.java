package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.InventoryRecordInfo;
import com.lz.manage.model.vo.inventoryRecordInfo.InventoryRecordInfoVo;
import com.lz.manage.model.dto.inventoryRecordInfo.InventoryRecordInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 库存记录Service接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface IInventoryRecordInfoService extends IService<InventoryRecordInfo>
{
    //region mybatis代码
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
     * 批量删除库存记录
     * 
     * @param ids 需要删除的库存记录主键集合
     * @return 结果
     */
    public int deleteInventoryRecordInfoByIds(Long[] ids);

    /**
     * 删除库存记录信息
     * 
     * @param id 库存记录主键
     * @return 结果
     */
    public int deleteInventoryRecordInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param inventoryRecordInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<InventoryRecordInfo> getQueryWrapper(InventoryRecordInfoQuery inventoryRecordInfoQuery);

    /**
     * 转换vo
     *
     * @param inventoryRecordInfoList InventoryRecordInfo集合
     * @return InventoryRecordInfoVO集合
     */
    List<InventoryRecordInfoVo> convertVoList(List<InventoryRecordInfo> inventoryRecordInfoList);
}
