package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.List;
import java.util.Date;
import com.lz.manage.model.domain.StocktakingOrderDetailInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.ArrayList;
import com.lz.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.lz.manage.model.domain.StocktakingOrderDetailInfo;
import com.lz.manage.mapper.StocktakingOrderInfoMapper;
import com.lz.manage.model.domain.StocktakingOrderInfo;
import com.lz.manage.service.IStocktakingOrderInfoService;
import com.lz.manage.model.dto.stocktakingOrderInfo.StocktakingOrderInfoQuery;
import com.lz.manage.model.vo.stocktakingOrderInfo.StocktakingOrderInfoVo;

/**
 * 盘点单Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class StocktakingOrderInfoServiceImpl extends ServiceImpl<StocktakingOrderInfoMapper, StocktakingOrderInfo> implements IStocktakingOrderInfoService
{

    @Resource
    private StocktakingOrderInfoMapper stocktakingOrderInfoMapper;

    //region mybatis代码
    /**
     * 查询盘点单
     *
     * @param id 盘点单主键
     * @return 盘点单
     */
    @Override
    public StocktakingOrderInfo selectStocktakingOrderInfoById(Long id)
    {
        return stocktakingOrderInfoMapper.selectStocktakingOrderInfoById(id);
    }

    /**
     * 查询盘点单列表
     *
     * @param stocktakingOrderInfo 盘点单
     * @return 盘点单
     */
    @Override
    public List<StocktakingOrderInfo> selectStocktakingOrderInfoList(StocktakingOrderInfo stocktakingOrderInfo)
    {
        return stocktakingOrderInfoMapper.selectStocktakingOrderInfoList(stocktakingOrderInfo);
    }

    /**
     * 新增盘点单
     *
     * @param stocktakingOrderInfo 盘点单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertStocktakingOrderInfo(StocktakingOrderInfo stocktakingOrderInfo)
    {
        stocktakingOrderInfo.setCreateTime(DateUtils.getNowDate());
        int rows = stocktakingOrderInfoMapper.insertStocktakingOrderInfo(stocktakingOrderInfo);
        insertStocktakingOrderDetailInfo(stocktakingOrderInfo);
        return rows;
    }

    /**
     * 修改盘点单
     *
     * @param stocktakingOrderInfo 盘点单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateStocktakingOrderInfo(StocktakingOrderInfo stocktakingOrderInfo)
    {
        stocktakingOrderInfo.setUpdateTime(DateUtils.getNowDate());
        stocktakingOrderInfoMapper.deleteStocktakingOrderDetailInfoByStocktakingId(stocktakingOrderInfo.getId());
        insertStocktakingOrderDetailInfo(stocktakingOrderInfo);
        return stocktakingOrderInfoMapper.updateStocktakingOrderInfo(stocktakingOrderInfo);
    }

    /**
     * 批量删除盘点单
     *
     * @param ids 需要删除的盘点单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteStocktakingOrderInfoByIds(Long[] ids)
    {
        stocktakingOrderInfoMapper.deleteStocktakingOrderDetailInfoByStocktakingIds(ids);
        return stocktakingOrderInfoMapper.deleteStocktakingOrderInfoByIds(ids);
    }

    /**
     * 删除盘点单信息
     *
     * @param id 盘点单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteStocktakingOrderInfoById(Long id)
    {
        stocktakingOrderInfoMapper.deleteStocktakingOrderDetailInfoByStocktakingId(id);
        return stocktakingOrderInfoMapper.deleteStocktakingOrderInfoById(id);
    }

    /**
     * 新增盘点明细信息
     *
     * @param stocktakingOrderInfo 盘点单对象
     */
    public void insertStocktakingOrderDetailInfo(StocktakingOrderInfo stocktakingOrderInfo)
    {
        List<StocktakingOrderDetailInfo> stocktakingOrderDetailInfoList = stocktakingOrderInfo.getStocktakingOrderDetailInfoList();
        Long id = stocktakingOrderInfo.getId();
        if (StringUtils.isNotNull(stocktakingOrderDetailInfoList))
        {
            List<StocktakingOrderDetailInfo> list = new ArrayList<StocktakingOrderDetailInfo>();
            for (StocktakingOrderDetailInfo stocktakingOrderDetailInfo : stocktakingOrderDetailInfoList)
            {
                stocktakingOrderDetailInfo.setStocktakingId(id);
                list.add(stocktakingOrderDetailInfo);
            }
            if (list.size() > 0)
            {
                stocktakingOrderInfoMapper.batchStocktakingOrderDetailInfo(list);
            }
        }
    }
    //endregion
    @Override
    public QueryWrapper<StocktakingOrderInfo> getQueryWrapper(StocktakingOrderInfoQuery stocktakingOrderInfoQuery){
        QueryWrapper<StocktakingOrderInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = stocktakingOrderInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = stocktakingOrderInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String stocktakingNo = stocktakingOrderInfoQuery.getStocktakingNo();
        queryWrapper.eq(StringUtils.isNotEmpty(stocktakingNo) ,"stocktaking_no",stocktakingNo);

        String stocktakingType = stocktakingOrderInfoQuery.getStocktakingType();
        queryWrapper.eq(StringUtils.isNotEmpty(stocktakingType) ,"stocktaking_type",stocktakingType);

        Long warehouseId = stocktakingOrderInfoQuery.getWarehouseId();
        queryWrapper.eq( StringUtils.isNotNull(warehouseId),"warehouse_id",warehouseId);

        String stocktakingStatus = stocktakingOrderInfoQuery.getStocktakingStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(stocktakingStatus) ,"stocktaking_status",stocktakingStatus);

        String reviewStatus = stocktakingOrderInfoQuery.getReviewStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(reviewStatus) ,"review_status",reviewStatus);

        String createBy = stocktakingOrderInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = stocktakingOrderInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<StocktakingOrderInfoVo> convertVoList(List<StocktakingOrderInfo> stocktakingOrderInfoList) {
        if (StringUtils.isEmpty(stocktakingOrderInfoList)) {
            return Collections.emptyList();
        }
        return stocktakingOrderInfoList.stream().map(StocktakingOrderInfoVo::objToVo).collect(Collectors.toList());
    }
}
