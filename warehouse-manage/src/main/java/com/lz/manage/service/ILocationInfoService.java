package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.LocationInfo;
import com.lz.manage.model.vo.locationInfo.LocationInfoVo;
import com.lz.manage.model.dto.locationInfo.LocationInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 库位Service接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface ILocationInfoService extends IService<LocationInfo>
{
    //region mybatis代码
    /**
     * 查询库位
     * 
     * @param id 库位主键
     * @return 库位
     */
    public LocationInfo selectLocationInfoById(Long id);

    /**
     * 查询库位列表
     * 
     * @param locationInfo 库位
     * @return 库位集合
     */
    public List<LocationInfo> selectLocationInfoList(LocationInfo locationInfo);

    /**
     * 新增库位
     * 
     * @param locationInfo 库位
     * @return 结果
     */
    public int insertLocationInfo(LocationInfo locationInfo);

    /**
     * 修改库位
     * 
     * @param locationInfo 库位
     * @return 结果
     */
    public int updateLocationInfo(LocationInfo locationInfo);

    /**
     * 批量删除库位
     * 
     * @param ids 需要删除的库位主键集合
     * @return 结果
     */
    public int deleteLocationInfoByIds(Long[] ids);

    /**
     * 删除库位信息
     * 
     * @param id 库位主键
     * @return 结果
     */
    public int deleteLocationInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param locationInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<LocationInfo> getQueryWrapper(LocationInfoQuery locationInfoQuery);

    /**
     * 转换vo
     *
     * @param locationInfoList LocationInfo集合
     * @return LocationInfoVO集合
     */
    List<LocationInfoVo> convertVoList(List<LocationInfo> locationInfoList);
}
