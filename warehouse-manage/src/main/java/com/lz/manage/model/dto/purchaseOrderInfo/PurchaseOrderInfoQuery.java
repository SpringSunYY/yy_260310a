package com.lz.manage.model.dto.purchaseOrderInfo;

import java.util.Map;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import com.lz.manage.model.domain.PurchaseOrderDetailInfo;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.PurchaseOrderInfo;
/**
 * 采购订单Query对象 tb_purchase_order_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class PurchaseOrderInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long orderId;

    /** 采购订单号 */
    private String orderNo;

    /** 供应商 */
    private Long supplierId;

    /** 订单状态 */
    private String orderStatus;

    /** 审批状态 */
    private String applicantStatus;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 采购订单明细信息 */
    private List<PurchaseOrderDetailInfo> purchaseOrderDetailInfoList;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param purchaseOrderInfoQuery 查询对象
     * @return PurchaseOrderInfo
     */
    public static PurchaseOrderInfo queryToObj(PurchaseOrderInfoQuery purchaseOrderInfoQuery) {
        if (purchaseOrderInfoQuery == null) {
            return null;
        }
        PurchaseOrderInfo purchaseOrderInfo = new PurchaseOrderInfo();
        BeanUtils.copyProperties(purchaseOrderInfoQuery, purchaseOrderInfo);
        return purchaseOrderInfo;
    }
}
