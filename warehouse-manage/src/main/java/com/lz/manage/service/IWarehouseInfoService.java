package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.WarehouseInfo;
import com.lz.manage.model.vo.warehouseInfo.WarehouseInfoVo;
import com.lz.manage.model.dto.warehouseInfo.WarehouseInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 仓库Service接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface IWarehouseInfoService extends IService<WarehouseInfo>
{
    //region mybatis代码
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
     * 批量删除仓库
     * 
     * @param ids 需要删除的仓库主键集合
     * @return 结果
     */
    public int deleteWarehouseInfoByIds(Long[] ids);

    /**
     * 删除仓库信息
     * 
     * @param id 仓库主键
     * @return 结果
     */
    public int deleteWarehouseInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param warehouseInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<WarehouseInfo> getQueryWrapper(WarehouseInfoQuery warehouseInfoQuery);

    /**
     * 转换vo
     *
     * @param warehouseInfoList WarehouseInfo集合
     * @return WarehouseInfoVO集合
     */
    List<WarehouseInfoVo> convertVoList(List<WarehouseInfo> warehouseInfoList);
}
