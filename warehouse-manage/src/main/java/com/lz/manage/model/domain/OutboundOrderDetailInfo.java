package com.lz.manage.model.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lz.common.annotation.Excel;
import com.lz.common.core.domain.BaseEntity;

/**
 * 出库明细对象 tb_outbound_order_detail_info
 * 
 * @author YY
 * @date 2026-05-08
 */
public class OutboundOrderDetailInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 明细ID */
    private Long id;

    /** 出库单 */
    @Excel(name = "出库单")
    private Long outboundId;

    /** 备件编号 */
    @Excel(name = "备件编号")
    private String partsCode;

    /** 出库数量 */
    @Excel(name = "出库数量")
    private Long outboundQuantity;

    /** 批次号 */
    @Excel(name = "批次号")
    private String batchNo;

    /** 仓库 */
    @Excel(name = "仓库")
    private Long warehouseId;

    /** 库位 */
    @Excel(name = "库位")
    private Long locationId;

    /** 出库单价 */
    @Excel(name = "出库单价")
    private BigDecimal unitPrice;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOutboundId(Long outboundId) 
    {
        this.outboundId = outboundId;
    }

    public Long getOutboundId() 
    {
        return outboundId;
    }
    public void setPartsCode(String partsCode) 
    {
        this.partsCode = partsCode;
    }

    public String getPartsCode() 
    {
        return partsCode;
    }
    public void setOutboundQuantity(Long outboundQuantity) 
    {
        this.outboundQuantity = outboundQuantity;
    }

    public Long getOutboundQuantity() 
    {
        return outboundQuantity;
    }
    public void setBatchNo(String batchNo) 
    {
        this.batchNo = batchNo;
    }

    public String getBatchNo() 
    {
        return batchNo;
    }
    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }
    public void setLocationId(Long locationId) 
    {
        this.locationId = locationId;
    }

    public Long getLocationId() 
    {
        return locationId;
    }
    public void setUnitPrice(BigDecimal unitPrice) 
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() 
    {
        return unitPrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("outboundId", getOutboundId())
            .append("partsCode", getPartsCode())
            .append("outboundQuantity", getOutboundQuantity())
            .append("batchNo", getBatchNo())
            .append("warehouseId", getWarehouseId())
            .append("locationId", getLocationId())
            .append("unitPrice", getUnitPrice())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
