package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.List;
import com.lz.manage.model.domain.InboundOrderDetailInfo;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.ArrayList;
import com.lz.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.lz.manage.model.domain.InboundOrderDetailInfo;
import com.lz.manage.mapper.InboundOrderInfoMapper;
import com.lz.manage.model.domain.InboundOrderInfo;
import com.lz.manage.service.IInboundOrderInfoService;
import com.lz.manage.model.dto.inboundOrderInfo.InboundOrderInfoQuery;
import com.lz.manage.model.vo.inboundOrderInfo.InboundOrderInfoVo;

/**
 * 入库单Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class InboundOrderInfoServiceImpl extends ServiceImpl<InboundOrderInfoMapper, InboundOrderInfo> implements IInboundOrderInfoService
{

    @Resource
    private InboundOrderInfoMapper inboundOrderInfoMapper;

    //region mybatis代码
    /**
     * 查询入库单
     *
     * @param id 入库单主键
     * @return 入库单
     */
    @Override
    public InboundOrderInfo selectInboundOrderInfoById(Long id)
    {
        return inboundOrderInfoMapper.selectInboundOrderInfoById(id);
    }

    /**
     * 查询入库单列表
     *
     * @param inboundOrderInfo 入库单
     * @return 入库单
     */
    @Override
    public List<InboundOrderInfo> selectInboundOrderInfoList(InboundOrderInfo inboundOrderInfo)
    {
        return inboundOrderInfoMapper.selectInboundOrderInfoList(inboundOrderInfo);
    }

    /**
     * 新增入库单
     *
     * @param inboundOrderInfo 入库单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertInboundOrderInfo(InboundOrderInfo inboundOrderInfo)
    {
        inboundOrderInfo.setCreateTime(DateUtils.getNowDate());
        int rows = inboundOrderInfoMapper.insertInboundOrderInfo(inboundOrderInfo);
        insertInboundOrderDetailInfo(inboundOrderInfo);
        return rows;
    }

    /**
     * 修改入库单
     *
     * @param inboundOrderInfo 入库单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateInboundOrderInfo(InboundOrderInfo inboundOrderInfo)
    {
        inboundOrderInfo.setUpdateTime(DateUtils.getNowDate());
        inboundOrderInfoMapper.deleteInboundOrderDetailInfoByInboundId(inboundOrderInfo.getId());
        insertInboundOrderDetailInfo(inboundOrderInfo);
        return inboundOrderInfoMapper.updateInboundOrderInfo(inboundOrderInfo);
    }

    /**
     * 批量删除入库单
     *
     * @param ids 需要删除的入库单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteInboundOrderInfoByIds(Long[] ids)
    {
        inboundOrderInfoMapper.deleteInboundOrderDetailInfoByInboundIds(ids);
        return inboundOrderInfoMapper.deleteInboundOrderInfoByIds(ids);
    }

    /**
     * 删除入库单信息
     *
     * @param id 入库单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteInboundOrderInfoById(Long id)
    {
        inboundOrderInfoMapper.deleteInboundOrderDetailInfoByInboundId(id);
        return inboundOrderInfoMapper.deleteInboundOrderInfoById(id);
    }

    /**
     * 新增入库明细信息
     *
     * @param inboundOrderInfo 入库单对象
     */
    public void insertInboundOrderDetailInfo(InboundOrderInfo inboundOrderInfo)
    {
        List<InboundOrderDetailInfo> inboundOrderDetailInfoList = inboundOrderInfo.getInboundOrderDetailInfoList();
        Long id = inboundOrderInfo.getId();
        if (StringUtils.isNotNull(inboundOrderDetailInfoList))
        {
            List<InboundOrderDetailInfo> list = new ArrayList<InboundOrderDetailInfo>();
            for (InboundOrderDetailInfo inboundOrderDetailInfo : inboundOrderDetailInfoList)
            {
                inboundOrderDetailInfo.setInboundId(id);
                list.add(inboundOrderDetailInfo);
            }
            if (list.size() > 0)
            {
                inboundOrderInfoMapper.batchInboundOrderDetailInfo(list);
            }
        }
    }
    //endregion
    @Override
    public QueryWrapper<InboundOrderInfo> getQueryWrapper(InboundOrderInfoQuery inboundOrderInfoQuery){
        QueryWrapper<InboundOrderInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = inboundOrderInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = inboundOrderInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String inboundNo = inboundOrderInfoQuery.getInboundNo();
        queryWrapper.eq(StringUtils.isNotEmpty(inboundNo) ,"inbound_no",inboundNo);

        String inboundType = inboundOrderInfoQuery.getInboundType();
        queryWrapper.eq(StringUtils.isNotEmpty(inboundType) ,"inbound_type",inboundType);

        Long warehouseId = inboundOrderInfoQuery.getWarehouseId();
        queryWrapper.eq( StringUtils.isNotNull(warehouseId),"warehouse_id",warehouseId);

        Long orderId = inboundOrderInfoQuery.getOrderId();
        queryWrapper.eq( StringUtils.isNotNull(orderId),"order_id",orderId);

        Long supplierId = inboundOrderInfoQuery.getSupplierId();
        queryWrapper.eq( StringUtils.isNotNull(supplierId),"supplier_id",supplierId);

        Date inboundDate = inboundOrderInfoQuery.getInboundDate();
        queryWrapper.eq( StringUtils.isNotNull(inboundDate),"inbound_date",inboundDate);

        String inboundStatus = inboundOrderInfoQuery.getInboundStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(inboundStatus) ,"inbound_status",inboundStatus);

        String reviewStatus = inboundOrderInfoQuery.getReviewStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(reviewStatus) ,"review_status",reviewStatus);

        String createBy = inboundOrderInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = inboundOrderInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<InboundOrderInfoVo> convertVoList(List<InboundOrderInfo> inboundOrderInfoList) {
        if (StringUtils.isEmpty(inboundOrderInfoList)) {
            return Collections.emptyList();
        }
        return inboundOrderInfoList.stream().map(InboundOrderInfoVo::objToVo).collect(Collectors.toList());
    }
}
