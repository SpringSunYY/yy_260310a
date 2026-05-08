package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.SupplierInfoMapper;
import com.lz.manage.model.domain.SupplierInfo;
import com.lz.manage.service.ISupplierInfoService;
import com.lz.manage.model.dto.supplierInfo.SupplierInfoQuery;
import com.lz.manage.model.vo.supplierInfo.SupplierInfoVo;

/**
 * 供应商信息Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class SupplierInfoServiceImpl extends ServiceImpl<SupplierInfoMapper, SupplierInfo> implements ISupplierInfoService
{

    @Resource
    private SupplierInfoMapper supplierInfoMapper;

    //region mybatis代码
    /**
     * 查询供应商信息
     *
     * @param id 供应商信息主键
     * @return 供应商信息
     */
    @Override
    public SupplierInfo selectSupplierInfoById(Long id)
    {
        return supplierInfoMapper.selectSupplierInfoById(id);
    }

    /**
     * 查询供应商信息列表
     *
     * @param supplierInfo 供应商信息
     * @return 供应商信息
     */
    @Override
    public List<SupplierInfo> selectSupplierInfoList(SupplierInfo supplierInfo)
    {
        return supplierInfoMapper.selectSupplierInfoList(supplierInfo);
    }

    /**
     * 新增供应商信息
     *
     * @param supplierInfo 供应商信息
     * @return 结果
     */
    @Override
    public int insertSupplierInfo(SupplierInfo supplierInfo)
    {
        supplierInfo.setCreateTime(DateUtils.getNowDate());
        return supplierInfoMapper.insertSupplierInfo(supplierInfo);
    }

    /**
     * 修改供应商信息
     *
     * @param supplierInfo 供应商信息
     * @return 结果
     */
    @Override
    public int updateSupplierInfo(SupplierInfo supplierInfo)
    {
        supplierInfo.setUpdateTime(DateUtils.getNowDate());
        return supplierInfoMapper.updateSupplierInfo(supplierInfo);
    }

    /**
     * 批量删除供应商信息
     *
     * @param ids 需要删除的供应商信息主键
     * @return 结果
     */
    @Override
    public int deleteSupplierInfoByIds(Long[] ids)
    {
        return supplierInfoMapper.deleteSupplierInfoByIds(ids);
    }

    /**
     * 删除供应商信息信息
     *
     * @param id 供应商信息主键
     * @return 结果
     */
    @Override
    public int deleteSupplierInfoById(Long id)
    {
        return supplierInfoMapper.deleteSupplierInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<SupplierInfo> getQueryWrapper(SupplierInfoQuery supplierInfoQuery){
        QueryWrapper<SupplierInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = supplierInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = supplierInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String supplierCode = supplierInfoQuery.getSupplierCode();
        queryWrapper.eq(StringUtils.isNotEmpty(supplierCode) ,"supplier_code",supplierCode);

        String supplierName = supplierInfoQuery.getSupplierName();
        queryWrapper.like(StringUtils.isNotEmpty(supplierName) ,"supplier_name",supplierName);

        String creditLevel = supplierInfoQuery.getCreditLevel();
        queryWrapper.eq(StringUtils.isNotEmpty(creditLevel) ,"credit_level",creditLevel);

        String supplierStatus = supplierInfoQuery.getSupplierStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(supplierStatus) ,"supplier_status",supplierStatus);

        String createBy = supplierInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = supplierInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<SupplierInfoVo> convertVoList(List<SupplierInfo> supplierInfoList) {
        if (StringUtils.isEmpty(supplierInfoList)) {
            return Collections.emptyList();
        }
        return supplierInfoList.stream().map(SupplierInfoVo::objToVo).collect(Collectors.toList());
    }
}
