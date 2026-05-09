package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.util.List;
import java.util.Date;
import com.lz.manage.model.domain.StocktakingOrderDetailInfo;
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
 * 盘点单对象 tb_stocktaking_order_info
 *
 * @author YY
 * @date 2026-05-08
 */
@TableName("tb_stocktaking_order_info")
@Data
public class StocktakingOrderInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 盘点单号 */
    @Excel(name = "盘点单号")
    private String stocktakingNo;

    /** 盘点类型 */
    @Excel(name = "盘点类型", dictType = "warehouse_stocktaking_type")
    private String stocktakingType;

    /** 仓库 */
    @Excel(name = "仓库")
    private Long warehouseId;

    /** 仓库名称 */
    @TableField(exist = false)
    @Excel(name = "仓库名称", type = Excel.Type.EXPORT)
    private String warehouseName;

    /** 盘点开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "盘点开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 盘点结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "盘点结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 盘点状态 */
    @Excel(name = "盘点状态", dictType = "warehouse_stocktaking_status")
    private String stocktakingStatus;

    /** 盘点人 */
    @Excel(name = "盘点人")
    private Long operatorId;

    /** 盘点人姓名 */
    @TableField(exist = false)
    @Excel(name = "盘点人姓名", type = Excel.Type.EXPORT)
    private String operatorName;

    /** 审核人 */
    @Excel(name = "审核人")
    private Long reviewerId;

    /** 审核人姓名 */
    @TableField(exist = false)
    @Excel(name = "审核人姓名", type = Excel.Type.EXPORT)
    private String reviewerName;

    /** 审核状态 */
    @Excel(name = "审核状态", dictType = "warehouse_applicant_status")
    private String reviewStatus;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date reviewTime;

    /** 审批描述 */
    @Excel(name = "审批描述")
    private String reviewRemark;

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

    /** 盘点明细信息 */
    private List<StocktakingOrderDetailInfo> stocktakingOrderDetailInfoList;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
