package com.lz.manage.model.dto.stocktakingOrderInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import com.lz.manage.model.domain.StocktakingOrderDetailInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.StocktakingOrderInfo;
/**
 * 盘点单Vo对象 tb_stocktaking_order_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class StocktakingOrderInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 盘点单号 */
    private String stocktakingNo;

    /** 盘点类型 */
    private String stocktakingType;

    /** 仓库 */
    private Long warehouseId;

    /** 盘点开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 盘点结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 盘点状态 */
    private String stocktakingStatus;

    /** 盘点人 */
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

    /** 盘点明细信息 */
    private List<StocktakingOrderDetailInfo> stocktakingOrderDetailInfoList;

    /**
     * 对象转封装类
     *
     * @param stocktakingOrderInfoInsert 插入对象
     * @return StocktakingOrderInfoInsert
     */
    public static StocktakingOrderInfo insertToObj(StocktakingOrderInfoInsert stocktakingOrderInfoInsert) {
        if (stocktakingOrderInfoInsert == null) {
            return null;
        }
        StocktakingOrderInfo stocktakingOrderInfo = new StocktakingOrderInfo();
        BeanUtils.copyProperties(stocktakingOrderInfoInsert, stocktakingOrderInfo);
        return stocktakingOrderInfo;
    }
}
