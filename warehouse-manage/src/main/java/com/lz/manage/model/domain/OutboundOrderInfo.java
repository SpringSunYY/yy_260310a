package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.OutboundOrderDetailInfo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.lz.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * 出库单对象 tb_outbound_order_info
 *
 * @author YY
 * @date 2026-05-08
 */
@TableName("tb_outbound_order_info")
@Data
public class OutboundOrderInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 出库单号 */
    @Excel(name = "出库单号")
    private String outboundNo;

    /** 出库类型 */
    @Excel(name = "出库类型", dictType = "warehouse_outbound_type")
    private String outboundType;

    /** 仓库 */
    @Excel(name = "仓库")
    private Long warehouseId;

    /** 领用人 */
    @Excel(name = "领用人")
    private Long recipientId;

    /** 领用部门 */
    @Excel(name = "领用部门")
    private Long recipientDeptId;

    /** 出库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outboundDate;

    /** 出库状态 */
    @Excel(name = "出库状态", dictType = "warehouse_outbound_status")
    private String outboundStatus;

    /** 是否可归还 */
    @Excel(name = "是否可归还", dictType = "warehouse_is_returnable")
    private String isReturnable;

    /** 经办人 */
    @Excel(name = "经办人")
    private Long operatorId;

    /** 审核人 */
    @Excel(name = "审核人")
    private Long reviewerId;

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

    /** 出库明细信息 */
    private List<OutboundOrderDetailInfo> outboundOrderDetailInfoList;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
