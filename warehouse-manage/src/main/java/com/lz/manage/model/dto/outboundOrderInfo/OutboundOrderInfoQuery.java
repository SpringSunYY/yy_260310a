package com.lz.manage.model.dto.outboundOrderInfo;

import java.util.Map;
import java.io.Serializable;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.OutboundOrderDetailInfo;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.OutboundOrderInfo;
/**
 * 出库单Query对象 tb_outbound_order_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class OutboundOrderInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 出库单号 */
    private String outboundNo;

    /** 出库类型 */
    private String outboundType;

    /** 出库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date outboundDate;

    /** 出库状态 */
    private String outboundStatus;

    /** 是否可归还 */
    private String isReturnable;

    /** 审核状态 */
    private String reviewStatus;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 出库明细信息 */
    private List<OutboundOrderDetailInfo> outboundOrderDetailInfoList;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param outboundOrderInfoQuery 查询对象
     * @return OutboundOrderInfo
     */
    public static OutboundOrderInfo queryToObj(OutboundOrderInfoQuery outboundOrderInfoQuery) {
        if (outboundOrderInfoQuery == null) {
            return null;
        }
        OutboundOrderInfo outboundOrderInfo = new OutboundOrderInfo();
        BeanUtils.copyProperties(outboundOrderInfoQuery, outboundOrderInfo);
        return outboundOrderInfo;
    }
}
