package com.lz.manage.model.dto.inventoryRecordInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.InventoryRecordInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存记录Vo对象 tb_inventory_record_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class InventoryRecordInfoEdit implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    private Long id;

    /**
     * 备件编号
     */
    private String partsCode;

    /**
     * 仓库
     */
    private Long warehouseId;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 有效期至
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;

    /**
     * 库位
     */
    private Long locationId;

    /**
     * 库存数量
     */
    private Long quantity;

    /**
     * 冻结数量
     */
    private Long frozenQuantity;

    /**
     * 可用数量
     */
    private Long availableQuantity;


    /**
     * 最后入库日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastInboundDate;

    /**
     * 最后出库日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastOutboundDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param inventoryRecordInfoEdit 编辑对象
     * @return InventoryRecordInfo
     */
    public static InventoryRecordInfo editToObj(InventoryRecordInfoEdit inventoryRecordInfoEdit) {
        if (inventoryRecordInfoEdit == null) {
            return null;
        }
        InventoryRecordInfo inventoryRecordInfo = new InventoryRecordInfo();
        BeanUtils.copyProperties(inventoryRecordInfoEdit, inventoryRecordInfo);
        return inventoryRecordInfo;
    }
}
