package com.lz.manage.model.vo.inventoryRecordInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.InventoryRecordInfo;
/**
 * 库存记录Vo对象 tb_inventory_record_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class InventoryRecordInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long id;

    /** 备件编号 */
    private String partsCode;

    /** 仓库 */
    private Long warehouseId;

    /** 库位 */
    private Long locationId;

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

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    private String remark;


     /**
     * 对象转封装类
     *
     * @param inventoryRecordInfo InventoryRecordInfo实体对象
     * @return InventoryRecordInfoVo
     */
    public static InventoryRecordInfoVo objToVo(InventoryRecordInfo inventoryRecordInfo) {
        if (inventoryRecordInfo == null) {
            return null;
        }
        InventoryRecordInfoVo inventoryRecordInfoVo = new InventoryRecordInfoVo();
        BeanUtils.copyProperties(inventoryRecordInfo, inventoryRecordInfoVo);
        return inventoryRecordInfoVo;
    }
}
