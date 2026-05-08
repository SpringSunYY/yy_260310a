package com.lz.manage.model.vo.purchaseOrderInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.PurchaseOrderDetailInfo;
import com.lz.manage.model.domain.PurchaseOrderInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 采购订单Vo对象 tb_purchase_order_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class PurchaseOrderInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long orderId;

    /**
     * 采购订单号
     */
    private String orderNo;

    /**
     * 供应商
     */
    private Long supplierId;
    private String supplierName;

    /**
     * 订单日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    /**
     * 预计到货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expectedArrivalDate;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 申请人
     */
    private Long applicantId;
    private String applicantName;

    /**
     * 审批状态
     */
    private String applicantStatus;

    /**
     * 审批人
     */
    private Long approverId;
    private String approverName;

    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approvalTime;

    /**
     * 审批描述
     */
    private String approvalDesc;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 采购订单明细信息
     */
    private List<PurchaseOrderDetailInfo> purchaseOrderDetailInfoList;


    /**
     * 对象转封装类
     *
     * @param purchaseOrderInfo PurchaseOrderInfo实体对象
     * @return PurchaseOrderInfoVo
     */
    public static PurchaseOrderInfoVo objToVo(PurchaseOrderInfo purchaseOrderInfo) {
        if (purchaseOrderInfo == null) {
            return null;
        }
        PurchaseOrderInfoVo purchaseOrderInfoVo = new PurchaseOrderInfoVo();
        BeanUtils.copyProperties(purchaseOrderInfo, purchaseOrderInfoVo);
        return purchaseOrderInfoVo;
    }
}
