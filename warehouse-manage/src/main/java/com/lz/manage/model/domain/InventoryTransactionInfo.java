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
 * 库存流水对象 tb_inventory_transaction_info
 *
 * @author YY
 * @date 2026-05-08
 */
@TableName("tb_inventory_transaction_info")
@Data
public class InventoryTransactionInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
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

    /** 批次号 */
    @Excel(name = "批次号")
    private String batchNo;

    /** 流水类型 */
    @Excel(name = "流水类型", dictType = "warehouse_transaction_type")
    private String transactionType;

    /** 变动数量 */
    @Excel(name = "变动数量")
    private Long changeQuantity;

    /** 变动前库存量 */
    @Excel(name = "变动前库存量")
    private Long beforeQuantity;

    /** 变动后库存量 */
    @Excel(name = "变动后库存量")
    private Long afterQuantity;

    /** 关联单号 */
    @Excel(name = "关联单号")
    private String relatedOrderNo;

    /** 操作人 */
    @Excel(name = "操作人")
    private Long operatorId;

    /** 流水时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "流水时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date transactionTime;

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
