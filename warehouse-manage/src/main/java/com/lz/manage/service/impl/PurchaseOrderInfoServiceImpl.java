package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.math.BigDecimal;
import java.util.List;
import com.lz.manage.model.domain.PurchaseOrderDetailInfo;
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
import com.lz.manage.model.domain.PurchaseOrderDetailInfo;
import com.lz.manage.mapper.PurchaseOrderInfoMapper;
import com.lz.manage.model.domain.PurchaseOrderInfo;
import com.lz.manage.service.IPurchaseOrderInfoService;
import com.lz.manage.model.dto.purchaseOrderInfo.PurchaseOrderInfoQuery;
import com.lz.manage.model.vo.purchaseOrderInfo.PurchaseOrderInfoVo;

/**
 * 采购订单Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class PurchaseOrderInfoServiceImpl extends ServiceImpl<PurchaseOrderInfoMapper, PurchaseOrderInfo> implements IPurchaseOrderInfoService
{

    @Resource
    private PurchaseOrderInfoMapper purchaseOrderInfoMapper;

    //region mybatis代码
    /**
     * 查询采购订单
     *
     * @param orderId 采购订单主键
     * @return 采购订单
     */
    @Override
    public PurchaseOrderInfo selectPurchaseOrderInfoByOrderId(Long orderId)
    {
        return purchaseOrderInfoMapper.selectPurchaseOrderInfoByOrderId(orderId);
    }

    /**
     * 查询采购订单列表
     *
     * @param purchaseOrderInfo 采购订单
     * @return 采购订单
     */
    @Override
    public List<PurchaseOrderInfo> selectPurchaseOrderInfoList(PurchaseOrderInfo purchaseOrderInfo)
    {
        return purchaseOrderInfoMapper.selectPurchaseOrderInfoList(purchaseOrderInfo);
    }

    /**
     * 新增采购订单
     *
     * @param purchaseOrderInfo 采购订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertPurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo)
    {
        purchaseOrderInfo.setCreateTime(DateUtils.getNowDate());
        int rows = purchaseOrderInfoMapper.insertPurchaseOrderInfo(purchaseOrderInfo);
        insertPurchaseOrderDetailInfo(purchaseOrderInfo);
        return rows;
    }

    /**
     * 修改采购订单
     *
     * @param purchaseOrderInfo 采购订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updatePurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo)
    {
        purchaseOrderInfo.setUpdateTime(DateUtils.getNowDate());
        purchaseOrderInfoMapper.deletePurchaseOrderDetailInfoByOrderId(purchaseOrderInfo.getOrderId());
        insertPurchaseOrderDetailInfo(purchaseOrderInfo);
        return purchaseOrderInfoMapper.updatePurchaseOrderInfo(purchaseOrderInfo);
    }

    /**
     * 批量删除采购订单
     *
     * @param orderIds 需要删除的采购订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePurchaseOrderInfoByOrderIds(Long[] orderIds)
    {
        purchaseOrderInfoMapper.deletePurchaseOrderDetailInfoByOrderIds(orderIds);
        return purchaseOrderInfoMapper.deletePurchaseOrderInfoByOrderIds(orderIds);
    }

    /**
     * 删除采购订单信息
     *
     * @param orderId 采购订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePurchaseOrderInfoByOrderId(Long orderId)
    {
        purchaseOrderInfoMapper.deletePurchaseOrderDetailInfoByOrderId(orderId);
        return purchaseOrderInfoMapper.deletePurchaseOrderInfoByOrderId(orderId);
    }

    /**
     * 新增采购订单明细信息
     *
     * @param purchaseOrderInfo 采购订单对象
     */
    public void insertPurchaseOrderDetailInfo(PurchaseOrderInfo purchaseOrderInfo)
    {
        List<PurchaseOrderDetailInfo> purchaseOrderDetailInfoList = purchaseOrderInfo.getPurchaseOrderDetailInfoList();
        Long orderId = purchaseOrderInfo.getOrderId();
        if (StringUtils.isNotNull(purchaseOrderDetailInfoList))
        {
            List<PurchaseOrderDetailInfo> list = new ArrayList<PurchaseOrderDetailInfo>();
            for (PurchaseOrderDetailInfo purchaseOrderDetailInfo : purchaseOrderDetailInfoList)
            {
                purchaseOrderDetailInfo.setOrderId(orderId);
                list.add(purchaseOrderDetailInfo);
            }
            if (list.size() > 0)
            {
                purchaseOrderInfoMapper.batchPurchaseOrderDetailInfo(list);
            }
        }
    }
    //endregion
    @Override
    public QueryWrapper<PurchaseOrderInfo> getQueryWrapper(PurchaseOrderInfoQuery purchaseOrderInfoQuery){
        QueryWrapper<PurchaseOrderInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = purchaseOrderInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long orderId = purchaseOrderInfoQuery.getOrderId();
        queryWrapper.eq( StringUtils.isNotNull(orderId),"order_id",orderId);

        String orderNo = purchaseOrderInfoQuery.getOrderNo();
        queryWrapper.eq(StringUtils.isNotEmpty(orderNo) ,"order_no",orderNo);

        Long supplierId = purchaseOrderInfoQuery.getSupplierId();
        queryWrapper.eq( StringUtils.isNotNull(supplierId),"supplier_id",supplierId);

        String orderStatus = purchaseOrderInfoQuery.getOrderStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(orderStatus) ,"order_status",orderStatus);

        String applicantStatus = purchaseOrderInfoQuery.getApplicantStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(applicantStatus) ,"applicant_status",applicantStatus);

        String createBy = purchaseOrderInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = purchaseOrderInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<PurchaseOrderInfoVo> convertVoList(List<PurchaseOrderInfo> purchaseOrderInfoList) {
        if (StringUtils.isEmpty(purchaseOrderInfoList)) {
            return Collections.emptyList();
        }
        return purchaseOrderInfoList.stream().map(PurchaseOrderInfoVo::objToVo).collect(Collectors.toList());
    }
}
