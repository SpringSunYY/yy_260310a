package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.SparePartsInfoMapper;
import com.lz.manage.model.domain.SparePartsInfo;
import com.lz.manage.model.domain.SupplierInfo;
import com.lz.manage.model.dto.sparePartsInfo.SparePartsInfoQuery;
import com.lz.manage.model.vo.sparePartsInfo.SparePartsInfoVo;
import com.lz.manage.service.ISparePartsInfoService;
import com.lz.manage.service.ISupplierInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 备品备件信息Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class SparePartsInfoServiceImpl extends ServiceImpl<SparePartsInfoMapper, SparePartsInfo> implements ISparePartsInfoService {

    @Resource
    private SparePartsInfoMapper sparePartsInfoMapper;

    @Resource
    private ISupplierInfoService supplierInfoService;

    //region mybatis代码

    /**
     * 查询备品备件信息
     *
     * @param id 备品备件信息主键
     * @return 备品备件信息
     */
    @Override
    public SparePartsInfo selectSparePartsInfoById(Long id) {
        return sparePartsInfoMapper.selectSparePartsInfoById(id);
    }

    /**
     * 查询备品备件信息列表
     *
     * @param sparePartsInfo 备品备件信息
     * @return 备品备件信息
     */
    @Override
    public List<SparePartsInfo> selectSparePartsInfoList(SparePartsInfo sparePartsInfo) {
        List<SparePartsInfo> sparePartsInfos = sparePartsInfoMapper.selectSparePartsInfoList(sparePartsInfo);
        for (SparePartsInfo info : sparePartsInfos) {
            if (info.getSupplierId() != null) {
                SupplierInfo supplierInfo = supplierInfoService.selectSupplierInfoById(info.getSupplierId());
                if (supplierInfo != null) {
                    info.setSupplierName(supplierInfo.getSupplierName());
                }
            }
        }
        return sparePartsInfos;
    }

    /**
     * 新增备品备件信息
     *
     * @param sparePartsInfo 备品备件信息
     * @return 结果
     */
    @Override
    public int insertSparePartsInfo(SparePartsInfo sparePartsInfo) {
        //查看是否传递供应商
        if (sparePartsInfo.getSupplierId() != null) {
            SupplierInfo supplierInfo = supplierInfoService.selectSupplierInfoById(sparePartsInfo.getSupplierId());
            ThrowUtils.throwIf(StringUtils.isNull(supplierInfo), "供应商不存在");
        }
        //查看编号是否存在
        SparePartsInfo sparePartsInfoByCode = sparePartsInfoMapper.selectSparePartsInfoByCode(sparePartsInfo.getPartsCode());
        ThrowUtils.throwIf(StringUtils.isNotNull(sparePartsInfoByCode), "编号已存在");

        sparePartsInfo.setCurrentStock(0L);

        sparePartsInfo.setCreateBy(SecurityUtils.getUsername());
        sparePartsInfo.setCreateTime(DateUtils.getNowDate());
        return sparePartsInfoMapper.insertSparePartsInfo(sparePartsInfo);
    }

    /**
     * 修改备品备件信息
     *
     * @param sparePartsInfo 备品备件信息
     * @return 结果
     */
    @Override
    public int updateSparePartsInfo(SparePartsInfo sparePartsInfo) {
        //查看是否传递供应商
        if (sparePartsInfo.getSupplierId() != null) {
            SupplierInfo supplierInfo = supplierInfoService.selectSupplierInfoById(sparePartsInfo.getSupplierId());
            ThrowUtils.throwIf(StringUtils.isNull(supplierInfo), "供应商不存在");
        }
        SparePartsInfo sparePartsInfoByCode = sparePartsInfoMapper.selectSparePartsInfoByCode(sparePartsInfo.getPartsCode());
        ThrowUtils.throwIf(StringUtils.isNotNull(sparePartsInfoByCode)
                           && !sparePartsInfoByCode.getId().equals(sparePartsInfo.getId()),
                "编号已存在");
        sparePartsInfo.setUpdateBy(SecurityUtils.getUsername());
        sparePartsInfo.setUpdateTime(DateUtils.getNowDate());
        return sparePartsInfoMapper.updateSparePartsInfo(sparePartsInfo);
    }

    /**
     * 批量删除备品备件信息
     *
     * @param ids 需要删除的备品备件信息主键
     * @return 结果
     */
    @Override
    public int deleteSparePartsInfoByIds(Long[] ids) {
        return sparePartsInfoMapper.deleteSparePartsInfoByIds(ids);
    }

    /**
     * 删除备品备件信息信息
     *
     * @param id 备品备件信息主键
     * @return 结果
     */
    @Override
    public int deleteSparePartsInfoById(Long id) {
        return sparePartsInfoMapper.deleteSparePartsInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<SparePartsInfo> getQueryWrapper(SparePartsInfoQuery sparePartsInfoQuery) {
        QueryWrapper<SparePartsInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = sparePartsInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = sparePartsInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        String partsCode = sparePartsInfoQuery.getPartsCode();
        queryWrapper.eq(StringUtils.isNotEmpty(partsCode), "parts_code", partsCode);

        String partsName = sparePartsInfoQuery.getPartsName();
        queryWrapper.like(StringUtils.isNotEmpty(partsName), "parts_name", partsName);

        String specificationModel = sparePartsInfoQuery.getSpecificationModel();
        queryWrapper.eq(StringUtils.isNotEmpty(specificationModel), "specification_model", specificationModel);

        String category = sparePartsInfoQuery.getCategory();
        queryWrapper.eq(StringUtils.isNotEmpty(category), "category", category);

        String partsStatus = sparePartsInfoQuery.getPartsStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(partsStatus), "parts_status", partsStatus);

        Long supplierId = sparePartsInfoQuery.getSupplierId();
        queryWrapper.eq(StringUtils.isNotNull(supplierId), "supplier_id", supplierId);

        String createBy = sparePartsInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy), "create_by", createBy);

        Date createTime = sparePartsInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<SparePartsInfoVo> convertVoList(List<SparePartsInfo> sparePartsInfoList) {
        if (StringUtils.isEmpty(sparePartsInfoList)) {
            return Collections.emptyList();
        }
        return sparePartsInfoList.stream().map(SparePartsInfoVo::objToVo).collect(Collectors.toList());
    }
}
