package com.lz.manage.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lz.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 库存流水对象 tb_inventory_transaction_info
 *
 * @author YY
 * @date 2026-05-08
 */
@TableName("tb_inventory_transaction_info")
@Data
public class InventoryTransactionInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 备件编号
     */
    @Excel(name = "备件编号", type = Excel.Type.IMPORT)
    private String partsCode;
    @TableField(exist = false)
    @Excel(name = "备件编号", type = Excel.Type.EXPORT)
    private String partsName;

    /**
     * 仓库
     */
    @Excel(name = "仓库", type = Excel.Type.IMPORT)
    private Long warehouseId;
    @TableField(exist = false)
    @Excel(name = "仓库", type = Excel.Type.EXPORT)
    private String warehouseName;

    /**
     * 库位
     */
    @Excel(name = "库位", type = Excel.Type.IMPORT)
    private Long locationId;
    @TableField(exist = false)
    @Excel(name = "库位", type = Excel.Type.EXPORT)
    private String locationName;

    /**
     * 批次号
     */
    @Excel(name = "批次号")
    private String batchNo;

    /**
     * 流水类型
     */
    @Excel(name = "流水类型", dictType = "warehouse_transaction_type")
    private String transactionType;

    /**
     * 变动数量
     */
    @Excel(name = "变动数量")
    private Long changeQuantity;

    /**
     * 变动前库存量
     */
    @Excel(name = "变动前库存量")
    private Long beforeQuantity;

    /**
     * 变动后库存量
     */
    @Excel(name = "变动后库存量")
    private Long afterQuantity;

    /**
     * 关联单号
     */
    @Excel(name = "关联单号")
    private String relatedOrderNo;

    /**
     * 操作人
     */
    @Excel(name = "操作人", type = Excel.Type.IMPORT)
    private Long operatorId;
    @TableField(exist = false)
    @Excel(name = "操作人", type = Excel.Type.EXPORT)
    private String operatorName;

    /**
     * 流水时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "流水时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date transactionTime;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    @Excel(name = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
