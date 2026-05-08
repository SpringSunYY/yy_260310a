package com.lz.manage.model.dto.purchaseOrderInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import com.lz.manage.model.domain.PurchaseOrderDetailInfo;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.PurchaseOrderInfo;
/**
 * 采购订单Vo对象 tb_purchase_order_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class PurchaseOrderInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 采购订单号 */
    private String orderNo;

    /** 供应商 */
    private Long supplierId;

    /** 订单日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    /** 预计到货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expectedArrivalDate;

    /** 订单总金额 */
    private BigDecimal totalAmount;

    /** 订单状态 */
    private String orderStatus;

    /** 申请人 */
    private Long applicantId;

    /** 审批状态 */
    private String applicantStatus;

    /** 审批人 */
    private Long approverId;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date approvalTime;

    /** 审批描述 */
    private String approvalDesc;

    /** 备注 */
    private String remark;

    /** 采购订单明细信息 */
    private List<PurchaseOrderDetailInfo> purchaseOrderDetailInfoList;

    /**
     * 对象转封装类
     *
     * @param purchaseOrderInfoInsert 插入对象
     * @return PurchaseOrderInfoInsert
     */
    public static PurchaseOrderInfo insertToObj(PurchaseOrderInfoInsert purchaseOrderInfoInsert) {
        if (purchaseOrderInfoInsert == null) {
            return null;
        }
        PurchaseOrderInfo purchaseOrderInfo = new PurchaseOrderInfo();
        BeanUtils.copyProperties(purchaseOrderInfoInsert, purchaseOrderInfo);
        return purchaseOrderInfo;
    }
}
