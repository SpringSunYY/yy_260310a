package com.lz.manage.model.dto.inboundOrderInfo;

import java.util.Map;
import java.io.Serializable;
import java.util.List;
import com.lz.manage.model.domain.InboundOrderDetailInfo;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.InboundOrderInfo;
/**
 * 入库单Query对象 tb_inbound_order_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class InboundOrderInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

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

    /** 审核状态 */
    private String reviewStatus;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 入库明细信息 */
    private List<InboundOrderDetailInfo> inboundOrderDetailInfoList;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param inboundOrderInfoQuery 查询对象
     * @return InboundOrderInfo
     */
    public static InboundOrderInfo queryToObj(InboundOrderInfoQuery inboundOrderInfoQuery) {
        if (inboundOrderInfoQuery == null) {
            return null;
        }
        InboundOrderInfo inboundOrderInfo = new InboundOrderInfo();
        BeanUtils.copyProperties(inboundOrderInfoQuery, inboundOrderInfo);
        return inboundOrderInfo;
    }
}
