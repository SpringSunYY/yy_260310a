package com.lz.manage.model.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lz.common.annotation.Excel;
import com.lz.common.core.domain.BaseEntity;

/**
 * 入库明细对象 tb_inbound_order_detail_info
 * 
 * @author YY
 * @date 2026-05-08
 */
public class InboundOrderDetailInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 入库单 */
    @Excel(name = "入库单")
    private Long inboundId;

    /** 备件编号 */
    @Excel(name = "备件编号")
    private String partsCode;

    /** 入库数量 */
    @Excel(name = "入库数量")
    private Long inboundQuantity;

    /** 批次号 */
    @Excel(name = "批次号")
    private String batchNo;

    /** 仓库 */
    @Excel(name = "仓库")
    private Long warehouseId;

    /** 库位 */
    @Excel(name = "库位")
    private Long locationId;

    /** 质量状态 */
    @Excel(name = "质量状态")
    private String qualityStatus;

    /** 生产日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productionDate;

    /** 有效期至 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效期至", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expiryDate;

    /** 入库单价 */
    @Excel(name = "入库单价")
    private BigDecimal unitPrice;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setInboundId(Long inboundId) 
    {
        this.inboundId = inboundId;
    }

    public Long getInboundId() 
    {
        return inboundId;
    }
    public void setPartsCode(String partsCode) 
    {
        this.partsCode = partsCode;
    }

    public String getPartsCode() 
    {
        return partsCode;
    }
    public void setInboundQuantity(Long inboundQuantity) 
    {
        this.inboundQuantity = inboundQuantity;
    }

    public Long getInboundQuantity() 
    {
        return inboundQuantity;
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
    public void setQualityStatus(String qualityStatus) 
    {
        this.qualityStatus = qualityStatus;
    }

    public String getQualityStatus() 
    {
        return qualityStatus;
    }
    public void setProductionDate(Date productionDate) 
    {
        this.productionDate = productionDate;
    }

    public Date getProductionDate() 
    {
        return productionDate;
    }
    public void setExpiryDate(Date expiryDate) 
    {
        this.expiryDate = expiryDate;
    }

    public Date getExpiryDate() 
    {
        return expiryDate;
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
            .append("inboundId", getInboundId())
            .append("partsCode", getPartsCode())
            .append("inboundQuantity", getInboundQuantity())
            .append("batchNo", getBatchNo())
            .append("warehouseId", getWarehouseId())
            .append("locationId", getLocationId())
            .append("qualityStatus", getQualityStatus())
            .append("productionDate", getProductionDate())
            .append("expiryDate", getExpiryDate())
            .append("unitPrice", getUnitPrice())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
