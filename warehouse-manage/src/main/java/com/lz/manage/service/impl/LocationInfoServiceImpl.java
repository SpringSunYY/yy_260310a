package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.LocationInfoMapper;
import com.lz.manage.model.domain.LocationInfo;
import com.lz.manage.model.domain.WarehouseInfo;
import com.lz.manage.model.dto.locationInfo.LocationInfoQuery;
import com.lz.manage.model.vo.locationInfo.LocationInfoVo;
import com.lz.manage.service.ILocationInfoService;
import com.lz.manage.service.IWarehouseInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 库位Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class LocationInfoServiceImpl extends ServiceImpl<LocationInfoMapper, LocationInfo> implements ILocationInfoService {

    @Resource
    private LocationInfoMapper locationInfoMapper;

    @Resource
    private IWarehouseInfoService warehouseInfoService;

    //region mybatis代码

    /**
     * 查询库位
     *
     * @param id 库位主键
     * @return 库位
     */
    @Override
    public LocationInfo selectLocationInfoById(Long id) {
        return locationInfoMapper.selectLocationInfoById(id);
    }

    /**
     * 查询库位列表
     *
     * @param locationInfo 库位
     * @return 库位
     */
    @Override
    public List<LocationInfo> selectLocationInfoList(LocationInfo locationInfo) {
        List<LocationInfo> locationInfos = locationInfoMapper.selectLocationInfoList(locationInfo);
        for (LocationInfo info : locationInfos) {
            WarehouseInfo warehouseInfo = warehouseInfoService.selectWarehouseInfoById(info.getWarehouseId());
            if (StringUtils.isNotNull(warehouseInfo)) {
                info.setWarehouseName(warehouseInfo.getWarehouseName());
            }
        }
        return locationInfos;
    }

    /**
     * 新增库位
     *
     * @param locationInfo 库位
     * @return 结果
     */
    @Override
    public int insertLocationInfo(LocationInfo locationInfo) {
        initLocationInfo(locationInfo);

        //查询此仓库是否有此编号
        LocationInfo locationInfoByCode = locationInfoMapper.selectLocationInfoByCode(locationInfo.getLocationCode(), locationInfo.getWarehouseId());
        ThrowUtils.throwIf(StringUtils.isNotNull(locationInfoByCode), "此仓库已存在此编号");

        locationInfo.setInboundQuantity(0L);
        locationInfo.setOutboundQuantity(0L);
        locationInfo.setCreateBy(SecurityUtils.getUsername());
        locationInfo.setCreateTime(DateUtils.getNowDate());
        return locationInfoMapper.insertLocationInfo(locationInfo);
    }

    private void initLocationInfo(LocationInfo locationInfo) {
        //查询仓库是否存在
        WarehouseInfo warehouseInfo = warehouseInfoService.selectWarehouseInfoById(locationInfo.getWarehouseId());
        ThrowUtils.throwIf(StringUtils.isNull(warehouseInfo), "仓库不存在");
        //除非顶级父类，如果不是判断是否合父级仓库相同
        if (StringUtils.isNull(locationInfo.getParentId()) || locationInfo.getParentId().equals(0L)) {
            return;
        }
        LocationInfo parentLocationInfo = locationInfoMapper.selectLocationInfoById(locationInfo.getParentId());
        ThrowUtils.throwIf(!warehouseInfo.getId().equals(parentLocationInfo.getWarehouseId()), "库位仓库与父级仓库不同");
    }

    /**
     * 修改库位
     *
     * @param locationInfo 库位
     * @return 结果
     */
    @Override
    public int updateLocationInfo(LocationInfo locationInfo) {
        initLocationInfo(locationInfo);

        //查询此仓库是否有此编号
        LocationInfo locationInfoByCode = locationInfoMapper.selectLocationInfoByCode(locationInfo.getLocationCode(), locationInfo.getWarehouseId());
        ThrowUtils.throwIf(StringUtils.isNotNull(locationInfoByCode)
                           && !locationInfoByCode.getId().equals(locationInfo.getId()),
                "此仓库已存在此编号");
        locationInfo.setUpdateBy(SecurityUtils.getUsername());
        locationInfo.setUpdateTime(DateUtils.getNowDate());
        return locationInfoMapper.updateLocationInfo(locationInfo);
    }

    /**
     * 批量删除库位
     *
     * @param ids 需要删除的库位主键
     * @return 结果
     */
    @Override
    public int deleteLocationInfoByIds(Long[] ids) {
        return locationInfoMapper.deleteLocationInfoByIds(ids);
    }

    /**
     * 删除库位信息
     *
     * @param id 库位主键
     * @return 结果
     */
    @Override
    public int deleteLocationInfoById(Long id) {
        return locationInfoMapper.deleteLocationInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<LocationInfo> getQueryWrapper(LocationInfoQuery locationInfoQuery) {
        QueryWrapper<LocationInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = locationInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = locationInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long warehouseId = locationInfoQuery.getWarehouseId();
        queryWrapper.eq(StringUtils.isNotNull(warehouseId), "warehouse_id", warehouseId);

        String locationCode = locationInfoQuery.getLocationCode();
        queryWrapper.eq(StringUtils.isNotEmpty(locationCode), "location_code", locationCode);

        String locationName = locationInfoQuery.getLocationName();
        queryWrapper.like(StringUtils.isNotEmpty(locationName), "location_name", locationName);

        Long parentId = locationInfoQuery.getParentId();
        queryWrapper.eq(StringUtils.isNotNull(parentId), "parent_id", parentId);

        Long level = locationInfoQuery.getLevel();
        queryWrapper.eq(StringUtils.isNotNull(level), "level", level);

        String locationStatus = locationInfoQuery.getLocationStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(locationStatus), "location_status", locationStatus);

        String createBy = locationInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy), "create_by", createBy);

        Date createTime = locationInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<LocationInfoVo> convertVoList(List<LocationInfo> locationInfoList) {
        if (StringUtils.isEmpty(locationInfoList)) {
            return Collections.emptyList();
        }
        return locationInfoList.stream().map(LocationInfoVo::objToVo).collect(Collectors.toList());
    }
}
