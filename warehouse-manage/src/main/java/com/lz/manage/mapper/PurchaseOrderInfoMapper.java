package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.PurchaseOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.manage.model.domain.PurchaseOrderDetailInfo;

/**
 * 采购订单Mapper接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface PurchaseOrderInfoMapper extends BaseMapper<PurchaseOrderInfo>
{
    /**
     * 查询采购订单
     * 
     * @param orderId 采购订单主键
     * @return 采购订单
     */
    public PurchaseOrderInfo selectPurchaseOrderInfoByOrderId(Long orderId);

    /**
     * 查询采购订单列表
     * 
     * @param purchaseOrderInfo 采购订单
     * @return 采购订单集合
     */
    public List<PurchaseOrderInfo> selectPurchaseOrderInfoList(PurchaseOrderInfo purchaseOrderInfo);

    /**
     * 新增采购订单
     * 
     * @param purchaseOrderInfo 采购订单
     * @return 结果
     */
    public int insertPurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo);

    /**
     * 修改采购订单
     * 
     * @param purchaseOrderInfo 采购订单
     * @return 结果
     */
    public int updatePurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo);

    /**
     * 删除采购订单
     * 
     * @param orderId 采购订单主键
     * @return 结果
     */
    public int deletePurchaseOrderInfoByOrderId(Long orderId);

    /**
     * 批量删除采购订单
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseOrderInfoByOrderIds(Long[] orderIds);

    /**
     * 批量删除采购订单明细
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseOrderDetailInfoByOrderIds(Long[] orderIds);
    
    /**
     * 批量新增采购订单明细
     * 
     * @param purchaseOrderDetailInfoList 采购订单明细列表
     * @return 结果
     */
    public int batchPurchaseOrderDetailInfo(List<PurchaseOrderDetailInfo> purchaseOrderDetailInfoList);
    

    /**
     * 通过采购订单主键删除采购订单明细信息
     * 
     * @param orderId 采购订单ID
     * @return 结果
     */
    public int deletePurchaseOrderDetailInfoByOrderId(Long orderId);
}
