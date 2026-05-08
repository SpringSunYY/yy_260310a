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
 * 库位对象 tb_location_info
 *
 * @author YY
 * @date 2026-05-08
 */
@TableName("tb_location_info")
@Data
public class LocationInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 仓库 */
    @Excel(name = "仓库")
    private Long warehouseId;

    /** 库位编码 */
    @Excel(name = "库位编码")
    private String locationCode;

    /** 库位名称 */
    @Excel(name = "库位名称")
    private String locationName;

    /** 父级 */
    @Excel(name = "父级")
    private Long parentId;

    /** 库位层级 */
    @Excel(name = "库位层级", dictType = "warehouse_location_level")
    private Long level;

    /** 容量 */
    @Excel(name = "容量")
    private BigDecimal capacity;

    /** 入库总数 */
    @Excel(name = "入库总数")
    private Long inboundQuantity;

    /** 出库总数 */
    @Excel(name = "出库总数")
    private Long outboundQuantity;

    /** 状态 */
    @Excel(name = "状态", dictType = "warehouse_common_status")
    private String locationStatus;

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
