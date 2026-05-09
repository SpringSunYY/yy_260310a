package com.lz.manage.model.vo.outboundOrderInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.OutboundOrderDetailInfo;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.OutboundOrderInfo;
/**
 * 出库单Vo对象 tb_outbound_order_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class OutboundOrderInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 出库单号 */
    private String outboundNo;

    /** 出库类型 */
    private String outboundType;

    /** 仓库 */
    private Long warehouseId;

    /** 仓库名称 */
    private String warehouseName;

    /** 领用人 */
    private Long recipientId;

    /** 领用人名称 */
    private String recipientName;

    /** 领用部门 */
    private Long recipientDeptId;

    /** 领用部门名称 */
    private String recipientDeptName;

    /** 出库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date outboundDate;

    /** 出库状态 */
    private String outboundStatus;

    /** 是否可归还 */
    private String isReturnable;

    /** 经办人 */
    private Long operatorId;

    /** 经办人名称 */
    private String operatorName;

    /** 审核人 */
    private Long reviewerId;

    /** 审核人名称 */
    private String reviewerName;

    /** 审核状态 */
    private String reviewStatus;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reviewTime;

    /** 审批描述 */
    private String reviewRemark;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;

    /** 出库明细信息 */
    private List<OutboundOrderDetailInfo> outboundOrderDetailInfoList;


     /**
     * 对象转封装类
     *
     * @param outboundOrderInfo OutboundOrderInfo实体对象
     * @return OutboundOrderInfoVo
     */
    public static OutboundOrderInfoVo objToVo(OutboundOrderInfo outboundOrderInfo) {
        if (outboundOrderInfo == null) {
            return null;
        }
        OutboundOrderInfoVo outboundOrderInfoVo = new OutboundOrderInfoVo();
        BeanUtils.copyProperties(outboundOrderInfo, outboundOrderInfoVo);
        return outboundOrderInfoVo;
    }
}
