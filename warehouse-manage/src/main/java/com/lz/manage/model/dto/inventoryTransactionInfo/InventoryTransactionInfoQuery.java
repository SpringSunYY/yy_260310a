package com.lz.manage.model.dto.inventoryTransactionInfo;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.InventoryTransactionInfo;
/**
 * 库存流水Query对象 tb_inventory_transaction_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class InventoryTransactionInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 备件编号 */
    private String partsCode;

    /** 仓库 */
    private Long warehouseId;

    /** 库位 */
    private Long locationId;

    /** 批次号 */
    private String batchNo;

    /** 流水类型 */
    private String transactionType;

    /** 关联单号 */
    private String relatedOrderNo;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param inventoryTransactionInfoQuery 查询对象
     * @return InventoryTransactionInfo
     */
    public static InventoryTransactionInfo queryToObj(InventoryTransactionInfoQuery inventoryTransactionInfoQuery) {
        if (inventoryTransactionInfoQuery == null) {
            return null;
        }
        InventoryTransactionInfo inventoryTransactionInfo = new InventoryTransactionInfo();
        BeanUtils.copyProperties(inventoryTransactionInfoQuery, inventoryTransactionInfo);
        return inventoryTransactionInfo;
    }
}
