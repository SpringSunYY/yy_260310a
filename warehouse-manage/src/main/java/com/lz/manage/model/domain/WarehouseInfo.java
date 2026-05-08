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
 * 仓库对象 tb_warehouse_info
 *
 * @author YY
 * @date 2026-05-08
 */
@TableName("tb_warehouse_info")
@Data
public class WarehouseInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String warehouseCode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String warehouseName;

    /** 仓库类型 */
    @Excel(name = "仓库类型", dictType = "warehouse_warehouse_type")
    private String warehouseType;

    /** 仓库位置 */
    @Excel(name = "仓库位置")
    private String location;

    /** 仓库面积 */
    @Excel(name = "仓库面积")
    private BigDecimal area;

    /** 仓库管理员 */
    @Excel(name = "仓库管理员",type = Excel.Type.IMPORT)
    private Long userId;
    @TableField(exist = false)
    @Excel(name = "仓库管理员",type = Excel.Type.EXPORT)
    private String userName;

    /** 总容量 */
    @Excel(name = "总容量")
    private Long totalCapacity;

    /** 入库总数 */
    @Excel(name = "入库总数")
    private Long inboundQuantity;

    /** 出库总数 */
    @Excel(name = "出库总数")
    private Long outboundQuantity;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactPhone;

    /** 状态 */
    @Excel(name = "状态", dictType = "warehouse_common_status")
    private String warehouseStatus;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
