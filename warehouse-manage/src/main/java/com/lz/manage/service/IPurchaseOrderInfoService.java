package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.PurchaseOrderInfo;
import com.lz.manage.model.vo.purchaseOrderInfo.PurchaseOrderInfoVo;
import com.lz.manage.model.dto.purchaseOrderInfo.PurchaseOrderInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 采购订单Service接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface IPurchaseOrderInfoService extends IService<PurchaseOrderInfo>
{
    //region mybatis代码
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
     * 批量删除采购订单
     * 
     * @param orderIds 需要删除的采购订单主键集合
     * @return 结果
     */
    public int deletePurchaseOrderInfoByOrderIds(Long[] orderIds);

    /**
     * 删除采购订单信息
     * 
     * @param orderId 采购订单主键
     * @return 结果
     */
    public int deletePurchaseOrderInfoByOrderId(Long orderId);
    //endregion
    /**
     * 获取查询条件
     *
     * @param purchaseOrderInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<PurchaseOrderInfo> getQueryWrapper(PurchaseOrderInfoQuery purchaseOrderInfoQuery);

    /**
     * 转换vo
     *
     * @param purchaseOrderInfoList PurchaseOrderInfo集合
     * @return PurchaseOrderInfoVO集合
     */
    List<PurchaseOrderInfoVo> convertVoList(List<PurchaseOrderInfo> purchaseOrderInfoList);
}
