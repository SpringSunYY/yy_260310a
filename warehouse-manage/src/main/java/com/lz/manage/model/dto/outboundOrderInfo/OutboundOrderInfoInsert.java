package com.lz.manage.model.dto.outboundOrderInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.OutboundOrderDetailInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.OutboundOrderInfo;
/**
 * 出库单Vo对象 tb_outbound_order_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class OutboundOrderInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 出库单号 */
    private String outboundNo;

    /** 出库类型 */
    private String outboundType;

    /** 仓库 */
    private Long warehouseId;

    /** 领用人 */
    private Long recipientId;

    /** 领用部门 */
    private Long recipientDeptId;

    /** 出库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date outboundDate;

    /** 出库状态 */
    private String outboundStatus;

    /** 是否可归还 */
    private String isReturnable;

    /** 经办人 */
    private Long operatorId;

    /** 审核人 */
    private Long reviewerId;

    /** 审核状态 */
    private String reviewStatus;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reviewTime;

    /** 审批描述 */
    private String reviewRemark;

    /** 备注 */
    private String remark;

    /** 出库明细信息 */
    private List<OutboundOrderDetailInfo> outboundOrderDetailInfoList;

    /**
     * 对象转封装类
     *
     * @param outboundOrderInfoInsert 插入对象
     * @return OutboundOrderInfoInsert
     */
    public static OutboundOrderInfo insertToObj(OutboundOrderInfoInsert outboundOrderInfoInsert) {
        if (outboundOrderInfoInsert == null) {
            return null;
        }
        OutboundOrderInfo outboundOrderInfo = new OutboundOrderInfo();
        BeanUtils.copyProperties(outboundOrderInfoInsert, outboundOrderInfo);
        return outboundOrderInfo;
    }
}
