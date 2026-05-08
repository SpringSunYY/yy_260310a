package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.WarehouseInfoMapper;
import com.lz.manage.model.domain.WarehouseInfo;
import com.lz.manage.model.dto.warehouseInfo.WarehouseInfoQuery;
import com.lz.manage.model.vo.warehouseInfo.WarehouseInfoVo;
import com.lz.manage.service.IWarehouseInfoService;
import com.lz.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 仓库Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class WarehouseInfoServiceImpl extends ServiceImpl<WarehouseInfoMapper, WarehouseInfo> implements IWarehouseInfoService {

    @Resource
    private WarehouseInfoMapper warehouseInfoMapper;

    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询仓库
     *
     * @param id 仓库主键
     * @return 仓库
     */
    @Override
    public WarehouseInfo selectWarehouseInfoById(Long id) {
        return warehouseInfoMapper.selectWarehouseInfoById(id);
    }

    /**
     * 查询仓库列表
     *
     * @param warehouseInfo 仓库
     * @return 仓库
     */
    @Override
    public List<WarehouseInfo> selectWarehouseInfoList(WarehouseInfo warehouseInfo) {
        List<WarehouseInfo> warehouseInfos = warehouseInfoMapper.selectWarehouseInfoList(warehouseInfo);
        for (WarehouseInfo info : warehouseInfos) {
            if (StringUtils.isNotNull(info.getUserId())) {
                SysUser sysUser = sysUserService.selectUserById(info.getUserId());
                if (StringUtils.isNotNull(sysUser)) {
                    info.setUserName(sysUser.getUserName());
                }
            }
        }
        return warehouseInfos;
    }

    /**
     * 新增仓库
     *
     * @param warehouseInfo 仓库
     * @return 结果
     */
    @Override
    public int insertWarehouseInfo(WarehouseInfo warehouseInfo) {
        initWareHouse(warehouseInfo);

        //查询编号是否存在
        WarehouseInfo warehouseInfoByCode = warehouseInfoMapper.selectWarehouseInfoByCode(warehouseInfo.getWarehouseCode());
        ThrowUtils.throwIf(StringUtils.isNotNull(warehouseInfoByCode), "编号已存在");

        warehouseInfo.setInboundQuantity(0L);
        warehouseInfo.setOutboundQuantity(0L);
        warehouseInfo.setTotalCapacity(0L);
        warehouseInfo.setCreateBy(SecurityUtils.getUsername());
        warehouseInfo.setCreateTime(DateUtils.getNowDate());
        return warehouseInfoMapper.insertWarehouseInfo(warehouseInfo);
    }

    private void initWareHouse(WarehouseInfo warehouseInfo) {
        //如果有用户查询用户是否存在
        if (StringUtils.isNotNull(warehouseInfo.getUserId())) {
            SysUser sysUser = sysUserService.selectUserById(warehouseInfo.getUserId());
            ThrowUtils.throwIf(StringUtils.isNull(sysUser), "用户不存在");
        }


    }

    /**
     * 修改仓库
     *
     * @param warehouseInfo 仓库
     * @return 结果
     */
    @Override
    public int updateWarehouseInfo(WarehouseInfo warehouseInfo) {
        initWareHouse(warehouseInfo);

        //查询编号是否存在
        WarehouseInfo warehouseInfoByCode = warehouseInfoMapper.selectWarehouseInfoByCode(warehouseInfo.getWarehouseCode());
        ThrowUtils.throwIf(StringUtils.isNotNull(warehouseInfoByCode)
                           && !warehouseInfo.getId().equals(warehouseInfoByCode.getId()),
                "编号已存在");

        warehouseInfo.setUpdateBy(SecurityUtils.getUsername());
        warehouseInfo.setUpdateTime(DateUtils.getNowDate());
        return warehouseInfoMapper.updateWarehouseInfo(warehouseInfo);
    }

    /**
     * 批量删除仓库
     *
     * @param ids 需要删除的仓库主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseInfoByIds(Long[] ids) {
        return warehouseInfoMapper.deleteWarehouseInfoByIds(ids);
    }

    /**
     * 删除仓库信息
     *
     * @param id 仓库主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseInfoById(Long id) {
        return warehouseInfoMapper.deleteWarehouseInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<WarehouseInfo> getQueryWrapper(WarehouseInfoQuery warehouseInfoQuery) {
        QueryWrapper<WarehouseInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = warehouseInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = warehouseInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        String warehouseCode = warehouseInfoQuery.getWarehouseCode();
        queryWrapper.eq(StringUtils.isNotEmpty(warehouseCode), "warehouse_code", warehouseCode);

        String warehouseName = warehouseInfoQuery.getWarehouseName();
        queryWrapper.like(StringUtils.isNotEmpty(warehouseName), "warehouse_name", warehouseName);

        String warehouseType = warehouseInfoQuery.getWarehouseType();
        queryWrapper.eq(StringUtils.isNotEmpty(warehouseType), "warehouse_type", warehouseType);

        String contactPhone = warehouseInfoQuery.getContactPhone();
        queryWrapper.eq(StringUtils.isNotEmpty(contactPhone), "contact_phone", contactPhone);

        String warehouseStatus = warehouseInfoQuery.getWarehouseStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(warehouseStatus), "warehouse_status", warehouseStatus);

        String createBy = warehouseInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy), "create_by", createBy);

        Date createTime = warehouseInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<WarehouseInfoVo> convertVoList(List<WarehouseInfo> warehouseInfoList) {
        if (StringUtils.isEmpty(warehouseInfoList)) {
            return Collections.emptyList();
        }
        return warehouseInfoList.stream().map(WarehouseInfoVo::objToVo).collect(Collectors.toList());
    }
}
