package com.lz.manage.model.dto.inboundOrderInfo;

import java.io.Serializable;
import java.util.List;
import com.lz.manage.model.domain.InboundOrderDetailInfo;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.InboundOrderInfo;
/**
 * 入库单Vo对象 tb_inbound_order_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class InboundOrderInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 入库单号 */
    private String inboundNo;

    /** 入库类型 */
    private String inboundType;

    /** 仓库 */
    private Long warehouseId;

    /** 关联订单 */
    private Long orderId;

    /** 供应商 */
    private Long supplierId;

    /** 入库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date inboundDate;

    /** 入库状态 */
    private String inboundStatus;

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

    /** 入库明细信息 */
    private List<InboundOrderDetailInfo> inboundOrderDetailInfoList;

    /**
     * 对象转封装类
     *
     * @param inboundOrderInfoInsert 插入对象
     * @return InboundOrderInfoInsert
     */
    public static InboundOrderInfo insertToObj(InboundOrderInfoInsert inboundOrderInfoInsert) {
        if (inboundOrderInfoInsert == null) {
            return null;
        }
        InboundOrderInfo inboundOrderInfo = new InboundOrderInfo();
        BeanUtils.copyProperties(inboundOrderInfoInsert, inboundOrderInfo);
        return inboundOrderInfo;
    }
}
