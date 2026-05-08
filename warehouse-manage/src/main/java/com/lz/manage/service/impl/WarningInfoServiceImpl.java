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
import com.lz.manage.mapper.WarningInfoMapper;
import com.lz.manage.model.domain.WarningInfo;
import com.lz.manage.service.IWarningInfoService;
import com.lz.manage.model.dto.warningInfo.WarningInfoQuery;
import com.lz.manage.model.vo.warningInfo.WarningInfoVo;

/**
 * 库存预警Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class WarningInfoServiceImpl extends ServiceImpl<WarningInfoMapper, WarningInfo> implements IWarningInfoService
{

    @Resource
    private WarningInfoMapper warningInfoMapper;

    //region mybatis代码
    /**
     * 查询库存预警
     *
     * @param id 库存预警主键
     * @return 库存预警
     */
    @Override
    public WarningInfo selectWarningInfoById(Long id)
    {
        return warningInfoMapper.selectWarningInfoById(id);
    }

    /**
     * 查询库存预警列表
     *
     * @param warningInfo 库存预警
     * @return 库存预警
     */
    @Override
    public List<WarningInfo> selectWarningInfoList(WarningInfo warningInfo)
    {
        return warningInfoMapper.selectWarningInfoList(warningInfo);
    }

    /**
     * 新增库存预警
     *
     * @param warningInfo 库存预警
     * @return 结果
     */
    @Override
    public int insertWarningInfo(WarningInfo warningInfo)
    {
        warningInfo.setCreateTime(DateUtils.getNowDate());
        return warningInfoMapper.insertWarningInfo(warningInfo);
    }

    /**
     * 修改库存预警
     *
     * @param warningInfo 库存预警
     * @return 结果
     */
    @Override
    public int updateWarningInfo(WarningInfo warningInfo)
    {
        warningInfo.setUpdateTime(DateUtils.getNowDate());
        return warningInfoMapper.updateWarningInfo(warningInfo);
    }

    /**
     * 批量删除库存预警
     *
     * @param ids 需要删除的库存预警主键
     * @return 结果
     */
    @Override
    public int deleteWarningInfoByIds(Long[] ids)
    {
        return warningInfoMapper.deleteWarningInfoByIds(ids);
    }

    /**
     * 删除库存预警信息
     *
     * @param id 库存预警主键
     * @return 结果
     */
    @Override
    public int deleteWarningInfoById(Long id)
    {
        return warningInfoMapper.deleteWarningInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<WarningInfo> getQueryWrapper(WarningInfoQuery warningInfoQuery){
        QueryWrapper<WarningInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = warningInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = warningInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String partsCode = warningInfoQuery.getPartsCode();
        queryWrapper.eq(StringUtils.isNotEmpty(partsCode) ,"parts_code",partsCode);

        String partsName = warningInfoQuery.getPartsName();
        queryWrapper.like(StringUtils.isNotEmpty(partsName) ,"parts_name",partsName);

        String warnType = warningInfoQuery.getWarnType();
        queryWrapper.eq(StringUtils.isNotEmpty(warnType) ,"warn_type",warnType);

        String warningStatus = warningInfoQuery.getWarningStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(warningStatus) ,"warning_status",warningStatus);

        String createBy = warningInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = warningInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<WarningInfoVo> convertVoList(List<WarningInfo> warningInfoList) {
        if (StringUtils.isEmpty(warningInfoList)) {
            return Collections.emptyList();
        }
        return warningInfoList.stream().map(WarningInfoVo::objToVo).collect(Collectors.toList());
    }
}
