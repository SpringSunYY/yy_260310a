package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.lz.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * 库存记录对象 tb_inventory_record_info
 *
 * @author YY
 * @date 2026-05-08
 */
@TableName("tb_inventory_record_info")
@Data
public class InventoryRecordInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    @Excel(name = "记录ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 备件编号 */
    @Excel(name = "备件编号")
    private String partsCode;

    /** 仓库 */
    @Excel(name = "仓库")
    private Long warehouseId;

    /** 库位 */
    @Excel(name = "库位")
    private Long locationId;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private Long quantity;

    /** 冻结数量 */
    @Excel(name = "冻结数量")
    private Long frozenQuantity;

    /** 可用数量 */
    @Excel(name = "可用数量")
    private Long availableQuantity;

    /** 有效期至 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效期至", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expiryDate;

    /** 最后入库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后入库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastInboundDate;

    /** 最后出库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后出库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastOutboundDate;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
