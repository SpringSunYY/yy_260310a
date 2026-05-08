package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.OutboundOrderDetailInfo;
import com.lz.common.utils.DateUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.ArrayList;
import com.lz.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.lz.manage.model.domain.OutboundOrderDetailInfo;
import com.lz.manage.mapper.OutboundOrderInfoMapper;
import com.lz.manage.model.domain.OutboundOrderInfo;
import com.lz.manage.service.IOutboundOrderInfoService;
import com.lz.manage.model.dto.outboundOrderInfo.OutboundOrderInfoQuery;
import com.lz.manage.model.vo.outboundOrderInfo.OutboundOrderInfoVo;

/**
 * 出库单Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class OutboundOrderInfoServiceImpl extends ServiceImpl<OutboundOrderInfoMapper, OutboundOrderInfo> implements IOutboundOrderInfoService
{

    @Resource
    private OutboundOrderInfoMapper outboundOrderInfoMapper;

    //region mybatis代码
    /**
     * 查询出库单
     *
     * @param id 出库单主键
     * @return 出库单
     */
    @Override
    public OutboundOrderInfo selectOutboundOrderInfoById(Long id)
    {
        return outboundOrderInfoMapper.selectOutboundOrderInfoById(id);
    }

    /**
     * 查询出库单列表
     *
     * @param outboundOrderInfo 出库单
     * @return 出库单
     */
    @Override
    public List<OutboundOrderInfo> selectOutboundOrderInfoList(OutboundOrderInfo outboundOrderInfo)
    {
        return outboundOrderInfoMapper.selectOutboundOrderInfoList(outboundOrderInfo);
    }

    /**
     * 新增出库单
     *
     * @param outboundOrderInfo 出库单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertOutboundOrderInfo(OutboundOrderInfo outboundOrderInfo)
    {
        outboundOrderInfo.setCreateTime(DateUtils.getNowDate());
        int rows = outboundOrderInfoMapper.insertOutboundOrderInfo(outboundOrderInfo);
        insertOutboundOrderDetailInfo(outboundOrderInfo);
        return rows;
    }

    /**
     * 修改出库单
     *
     * @param outboundOrderInfo 出库单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateOutboundOrderInfo(OutboundOrderInfo outboundOrderInfo)
    {
        outboundOrderInfo.setUpdateTime(DateUtils.getNowDate());
        outboundOrderInfoMapper.deleteOutboundOrderDetailInfoByOutboundId(outboundOrderInfo.getId());
        insertOutboundOrderDetailInfo(outboundOrderInfo);
        return outboundOrderInfoMapper.updateOutboundOrderInfo(outboundOrderInfo);
    }

    /**
     * 批量删除出库单
     *
     * @param ids 需要删除的出库单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOutboundOrderInfoByIds(Long[] ids)
    {
        outboundOrderInfoMapper.deleteOutboundOrderDetailInfoByOutboundIds(ids);
        return outboundOrderInfoMapper.deleteOutboundOrderInfoByIds(ids);
    }

    /**
     * 删除出库单信息
     *
     * @param id 出库单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOutboundOrderInfoById(Long id)
    {
        outboundOrderInfoMapper.deleteOutboundOrderDetailInfoByOutboundId(id);
        return outboundOrderInfoMapper.deleteOutboundOrderInfoById(id);
    }

    /**
     * 新增出库明细信息
     *
     * @param outboundOrderInfo 出库单对象
     */
    public void insertOutboundOrderDetailInfo(OutboundOrderInfo outboundOrderInfo)
    {
        List<OutboundOrderDetailInfo> outboundOrderDetailInfoList = outboundOrderInfo.getOutboundOrderDetailInfoList();
        Long id = outboundOrderInfo.getId();
        if (StringUtils.isNotNull(outboundOrderDetailInfoList))
        {
            List<OutboundOrderDetailInfo> list = new ArrayList<OutboundOrderDetailInfo>();
            for (OutboundOrderDetailInfo outboundOrderDetailInfo : outboundOrderDetailInfoList)
            {
                outboundOrderDetailInfo.setOutboundId(id);
                list.add(outboundOrderDetailInfo);
            }
            if (list.size() > 0)
            {
                outboundOrderInfoMapper.batchOutboundOrderDetailInfo(list);
            }
        }
    }
    //endregion
    @Override
    public QueryWrapper<OutboundOrderInfo> getQueryWrapper(OutboundOrderInfoQuery outboundOrderInfoQuery){
        QueryWrapper<OutboundOrderInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = outboundOrderInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = outboundOrderInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String outboundNo = outboundOrderInfoQuery.getOutboundNo();
        queryWrapper.eq(StringUtils.isNotEmpty(outboundNo) ,"outbound_no",outboundNo);

        String outboundType = outboundOrderInfoQuery.getOutboundType();
        queryWrapper.eq(StringUtils.isNotEmpty(outboundType) ,"outbound_type",outboundType);

        Date outboundDate = outboundOrderInfoQuery.getOutboundDate();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginOutboundDate"))&&StringUtils.isNotNull(params.get("endOutboundDate")),"outbound_date",params.get("beginOutboundDate"),params.get("endOutboundDate"));

        String outboundStatus = outboundOrderInfoQuery.getOutboundStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(outboundStatus) ,"outbound_status",outboundStatus);

        String isReturnable = outboundOrderInfoQuery.getIsReturnable();
        queryWrapper.eq(StringUtils.isNotEmpty(isReturnable) ,"is_returnable",isReturnable);

        String reviewStatus = outboundOrderInfoQuery.getReviewStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(reviewStatus) ,"review_status",reviewStatus);

        String createBy = outboundOrderInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = outboundOrderInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<OutboundOrderInfoVo> convertVoList(List<OutboundOrderInfo> outboundOrderInfoList) {
        if (StringUtils.isEmpty(outboundOrderInfoList)) {
            return Collections.emptyList();
        }
        return outboundOrderInfoList.stream().map(OutboundOrderInfoVo::objToVo).collect(Collectors.toList());
    }
}
