package com.lz.manage.model.dto.inventoryRecordInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.InventoryRecordInfo;
/**
 * 库存记录Vo对象 tb_inventory_record_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class InventoryRecordInfoInsert implements Serializable
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

    /** 库存数量 */
    private Long quantity;

    /** 冻结数量 */
    private Long frozenQuantity;

    /** 可用数量 */
    private Long availableQuantity;

    /** 有效期至 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;

    /** 最后入库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastInboundDate;

    /** 最后出库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastOutboundDate;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param inventoryRecordInfoInsert 插入对象
     * @return InventoryRecordInfoInsert
     */
    public static InventoryRecordInfo insertToObj(InventoryRecordInfoInsert inventoryRecordInfoInsert) {
        if (inventoryRecordInfoInsert == null) {
            return null;
        }
        InventoryRecordInfo inventoryRecordInfo = new InventoryRecordInfo();
        BeanUtils.copyProperties(inventoryRecordInfoInsert, inventoryRecordInfo);
        return inventoryRecordInfo;
    }
}
