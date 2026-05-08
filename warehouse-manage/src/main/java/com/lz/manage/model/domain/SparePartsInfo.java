package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.math.BigDecimal;
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
 * 备品备件信息对象 tb_spare_parts_info
 *
 * @author YY
 * @date 2026-05-08
 */
@TableName("tb_spare_parts_info")
@Data
public class SparePartsInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 备件编码 */
    @Excel(name = "备件编码")
    private String partsCode;

    /** 备件名称 */
    @Excel(name = "备件名称")
    private String partsName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specificationModel;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 分类 */
    @Excel(name = "分类", dictType = "warehouse_parts_category")
    private String category;

    /** 状态 */
    @Excel(name = "状态", dictType = "warehouse_common_status")
    private String partsStatus;

    /** 存储条件 */
    @Excel(name = "存储条件")
    private String storageCondition;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal unitPrice;

    /** 最小库存量 */
    @Excel(name = "最小库存量")
    private Long minStock;

    /** 最大库存量 */
    @Excel(name = "最大库存量")
    private Long maxStock;

    /** 当前库存量 */
    @Excel(name = "当前库存量")
    private Long currentStock;

    /** 主供应商 */
    @Excel(name = "主供应商")
    private Long supplierId;

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
