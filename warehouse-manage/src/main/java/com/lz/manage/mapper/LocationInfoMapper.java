package com.lz.manage.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.manage.model.domain.LocationInfo;

import java.util.List;

/**
 * 库位Mapper接口
 *
 * @author YY
 * @date 2026-05-08
 */
public interface LocationInfoMapper extends BaseMapper<LocationInfo> {
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
     * 删除库位
     *
     * @param id 库位主键
     * @return 结果
     */
    public int deleteLocationInfoById(Long id);

    /**
     * 批量删除库位
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLocationInfoByIds(Long[] ids);

    default LocationInfo selectLocationInfoByCode(String locationCode, Long warehouseId) {
        return selectOne(new LambdaQueryWrapper<LocationInfo>()
                .eq(LocationInfo::getLocationCode, locationCode)
                .eq(LocationInfo::getWarehouseId, warehouseId));
    }
}
