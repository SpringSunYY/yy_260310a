package com.lz.manage.model.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lz.common.annotation.Excel;
import com.lz.common.core.domain.BaseEntity;

/**
 * 盘点明细对象 tb_stocktaking_order_detail_info
 * 
 * @author YY
 * @date 2026-05-08
 */
public class StocktakingOrderDetailInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 盘点单 */
    @Excel(name = "盘点单")
    private Long stocktakingId;

    /** 备件编号 */
    @Excel(name = "备件编号")
    private String partsCode;

    /** 仓库 */
    @Excel(name = "仓库")
    private Long warehouseId;

    /** 库位 */
    @Excel(name = "库位")
    private Long locationId;

    /** 批次号 */
    @Excel(name = "批次号")
    private String batchNo;

    /** 账面数量 */
    @Excel(name = "账面数量")
    private Long bookQuantity;

    /** 实际盘点数量 */
    @Excel(name = "实际盘点数量")
    private Long actualQuantity;

    /** 盈亏数量 */
    @Excel(name = "盈亏数量")
    private Long differenceQuantity;

    /** 差异原因 */
    @Excel(name = "差异原因")
    private String differenceReason;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setStocktakingId(Long stocktakingId) 
    {
        this.stocktakingId = stocktakingId;
    }

    public Long getStocktakingId() 
    {
        return stocktakingId;
    }
    public void setPartsCode(String partsCode) 
    {
        this.partsCode = partsCode;
    }

    public String getPartsCode() 
    {
        return partsCode;
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
    public void setBatchNo(String batchNo) 
    {
        this.batchNo = batchNo;
    }

    public String getBatchNo() 
    {
        return batchNo;
    }
    public void setBookQuantity(Long bookQuantity) 
    {
        this.bookQuantity = bookQuantity;
    }

    public Long getBookQuantity() 
    {
        return bookQuantity;
    }
    public void setActualQuantity(Long actualQuantity) 
    {
        this.actualQuantity = actualQuantity;
    }

    public Long getActualQuantity() 
    {
        return actualQuantity;
    }
    public void setDifferenceQuantity(Long differenceQuantity) 
    {
        this.differenceQuantity = differenceQuantity;
    }

    public Long getDifferenceQuantity() 
    {
        return differenceQuantity;
    }
    public void setDifferenceReason(String differenceReason) 
    {
        this.differenceReason = differenceReason;
    }

    public String getDifferenceReason() 
    {
        return differenceReason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("stocktakingId", getStocktakingId())
            .append("partsCode", getPartsCode())
            .append("warehouseId", getWarehouseId())
            .append("locationId", getLocationId())
            .append("batchNo", getBatchNo())
            .append("bookQuantity", getBookQuantity())
            .append("actualQuantity", getActualQuantity())
            .append("differenceQuantity", getDifferenceQuantity())
            .append("differenceReason", getDifferenceReason())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
