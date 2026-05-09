package com.lz.manage.model.dto.inventoryTransactionInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.InventoryTransactionInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存流水dto对象 tb_inventory_transaction_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class InventoryTransactionInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 备件编号
     */
    private String partsCode;

    /**
     * 仓库
     */
    private Long warehouseId;

    /**
     * 库位
     */
    private Long locationId;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 流水类型
     */
    private String transactionType;


    /**
     * 变动数量
     */
    private Long changeQuantity;

    /**
     * 关联单号
     */
    private String relatedOrderNo;

    /**
     * 操作人
     */
    private Long operatorId;

    /**
     * 流水时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transactionTime;
    /**
     * 有效期至
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;


    /**
     * 备注
     */
    private String remark;

    private String createBy;

    private Date createTime;

    /**
     * 对象转封装类
     *
     * @param inventoryTransactionInfoEdit 编辑对象
     * @return InventoryTransactionInfo
     */
    public static InventoryTransactionInfo editToObj(InventoryTransactionInfoDto inventoryTransactionInfoEdit) {
        if (inventoryTransactionInfoEdit == null) {
            return null;
        }
        InventoryTransactionInfo inventoryTransactionInfo = new InventoryTransactionInfo();
        BeanUtils.copyProperties(inventoryTransactionInfoEdit, inventoryTransactionInfo);
        return inventoryTransactionInfo;
    }
}
