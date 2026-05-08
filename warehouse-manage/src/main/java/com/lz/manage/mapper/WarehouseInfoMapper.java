package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.WarehouseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 仓库Mapper接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface WarehouseInfoMapper extends BaseMapper<WarehouseInfo>
{
    /**
     * 查询仓库
     * 
     * @param id 仓库主键
     * @return 仓库
     */
    public WarehouseInfo selectWarehouseInfoById(Long id);

    /**
     * 查询仓库列表
     * 
     * @param warehouseInfo 仓库
     * @return 仓库集合
     */
    public List<WarehouseInfo> selectWarehouseInfoList(WarehouseInfo warehouseInfo);

    /**
     * 新增仓库
     * 
     * @param warehouseInfo 仓库
     * @return 结果
     */
    public int insertWarehouseInfo(WarehouseInfo warehouseInfo);

    /**
     * 修改仓库
     * 
     * @param warehouseInfo 仓库
     * @return 结果
     */
    public int updateWarehouseInfo(WarehouseInfo warehouseInfo);

    /**
     * 删除仓库
     * 
     * @param id 仓库主键
     * @return 结果
     */
    public int deleteWarehouseInfoById(Long id);

    /**
     * 批量删除仓库
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseInfoByIds(Long[] ids);
}
