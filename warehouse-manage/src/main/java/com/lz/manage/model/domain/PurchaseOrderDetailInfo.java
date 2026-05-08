package com.lz.manage.model.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lz.common.annotation.Excel;
import com.lz.common.core.domain.BaseEntity;

/**
 * 采购订单明细对象 tb_purchase_order_detail_info
 * 
 * @author YY
 * @date 2026-05-08
 */
public class PurchaseOrderDetailInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 订单 */
    @Excel(name = "订单")
    private Long orderId;

    /** 备件编号 */
    @Excel(name = "备件编号")
    private String partsCode;

    /** 采购数量 */
    @Excel(name = "采购数量")
    private Long purchaseQuantity;

    /** 采购单价 */
    @Excel(name = "采购单价")
    private BigDecimal purchasePrice;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;

    /** 已收货数量 */
    @Excel(name = "已收货数量")
    private Long receivedQuantity;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setPartsCode(String partsCode) 
    {
        this.partsCode = partsCode;
    }

    public String getPartsCode() 
    {
        return partsCode;
    }
    public void setPurchaseQuantity(Long purchaseQuantity) 
    {
        this.purchaseQuantity = purchaseQuantity;
    }

    public Long getPurchaseQuantity() 
    {
        return purchaseQuantity;
    }
    public void setPurchasePrice(BigDecimal purchasePrice) 
    {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPurchasePrice() 
    {
        return purchasePrice;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setReceivedQuantity(Long receivedQuantity) 
    {
        this.receivedQuantity = receivedQuantity;
    }

    public Long getReceivedQuantity() 
    {
        return receivedQuantity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("partsCode", getPartsCode())
            .append("purchaseQuantity", getPurchaseQuantity())
            .append("purchasePrice", getPurchasePrice())
            .append("amount", getAmount())
            .append("receivedQuantity", getReceivedQuantity())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
