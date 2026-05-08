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
import java.util.List;
import java.util.Map;

/**
 * 入库单对象 tb_inbound_order_info
 *
 * @author YY
 * @date 2026-05-08
 */
@TableName("tb_inbound_order_info")
@Data
public class InboundOrderInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 入库单号
     */
    @Excel(name = "入库单号")
    private String inboundNo;

    /**
     * 入库类型
     */
    @Excel(name = "入库类型", dictType = "warehouse_inbound_type")
    private String inboundType;

    /**
     * 仓库
     */
    @Excel(name = "仓库")
    private Long warehouseId;

    /**
     * 仓库名称
     */
    @Excel(name = "仓库名称")
    @TableField(exist = false)
    private String warehouseName;

    /**
     * 关联订单
     */
    @Excel(name = "关联订单")
    private Long orderId;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    @TableField(exist = false)
    private String orderNo;

    /**
     * 供应商
     */
    @Excel(name = "供应商")
    private Long supplierId;

    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称")
    @TableField(exist = false)
    private String supplierName;

    /**
     * 入库日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inboundDate;

    /**
     * 入库状态
     */
    @Excel(name = "入库状态", dictType = "warehouse_inbound_status")
    private String inboundStatus;

    /**
     * 经办人
     */
    @Excel(name = "经办人")
    private Long operatorId;

    /**
     * 经办人姓名
     */
    @Excel(name = "经办人姓名")
    @TableField(exist = false)
    private String operatorName;

    /**
     * 审核人
     */
    @Excel(name = "审核人")
    private Long reviewerId;

    /**
     * 审核人姓名
     */
    @Excel(name = "审核人姓名")
    @TableField(exist = false)
    private String reviewerName;

    /**
     * 审核状态
     */
    @Excel(name = "审核状态", dictType = "warehouse_applicant_status")
    private String reviewStatus;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date reviewTime;

    /**
     * 审批描述
     */
    @Excel(name = "审批描述")
    private String reviewRemark;

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
     * 入库明细信息
     */
    private List<InboundOrderDetailInfo> inboundOrderDetailInfoList;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
