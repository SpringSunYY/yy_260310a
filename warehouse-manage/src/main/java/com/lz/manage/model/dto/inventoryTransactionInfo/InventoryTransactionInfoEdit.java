package com.lz.manage.model.dto.inventoryTransactionInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.InventoryTransactionInfo;
/**
 * 库存流水Vo对象 tb_inventory_transaction_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class InventoryTransactionInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

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

    /** 变动数量 */
    private Long changeQuantity;

    /** 变动前库存量 */
    private Long beforeQuantity;

    /** 变动后库存量 */
    private Long afterQuantity;

    /** 关联单号 */
    private String relatedOrderNo;

    /** 操作人 */
    private Long operatorId;

    /** 流水时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transactionTime;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param inventoryTransactionInfoEdit 编辑对象
     * @return InventoryTransactionInfo
     */
    public static InventoryTransactionInfo editToObj(InventoryTransactionInfoEdit inventoryTransactionInfoEdit) {
        if (inventoryTransactionInfoEdit == null) {
            return null;
        }
        InventoryTransactionInfo inventoryTransactionInfo = new InventoryTransactionInfo();
        BeanUtils.copyProperties(inventoryTransactionInfoEdit, inventoryTransactionInfo);
        return inventoryTransactionInfo;
    }
}
