package com.lz.manage.model.dto.stocktakingOrderInfo;

import java.util.Map;
import java.io.Serializable;
import java.util.List;
import java.util.Date;
import com.lz.manage.model.domain.StocktakingOrderDetailInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.StocktakingOrderInfo;
/**
 * 盘点单Query对象 tb_stocktaking_order_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class StocktakingOrderInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 盘点单号 */
    private String stocktakingNo;

    /** 盘点类型 */
    private String stocktakingType;

    /** 仓库 */
    private Long warehouseId;

    /** 盘点状态 */
    private String stocktakingStatus;

    /** 审核状态 */
    private String reviewStatus;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 盘点明细信息 */
    private List<StocktakingOrderDetailInfo> stocktakingOrderDetailInfoList;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param stocktakingOrderInfoQuery 查询对象
     * @return StocktakingOrderInfo
     */
    public static StocktakingOrderInfo queryToObj(StocktakingOrderInfoQuery stocktakingOrderInfoQuery) {
        if (stocktakingOrderInfoQuery == null) {
            return null;
        }
        StocktakingOrderInfo stocktakingOrderInfo = new StocktakingOrderInfo();
        BeanUtils.copyProperties(stocktakingOrderInfoQuery, stocktakingOrderInfo);
        return stocktakingOrderInfo;
    }
}
