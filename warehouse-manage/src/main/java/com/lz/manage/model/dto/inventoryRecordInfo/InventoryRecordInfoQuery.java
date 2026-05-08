package com.lz.manage.model.dto.inventoryRecordInfo;

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
import com.lz.manage.model.domain.InventoryRecordInfo;
/**
 * 库存记录Query对象 tb_inventory_record_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class InventoryRecordInfoQuery implements Serializable
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
     * @param inventoryRecordInfoQuery 查询对象
     * @return InventoryRecordInfo
     */
    public static InventoryRecordInfo queryToObj(InventoryRecordInfoQuery inventoryRecordInfoQuery) {
        if (inventoryRecordInfoQuery == null) {
            return null;
        }
        InventoryRecordInfo inventoryRecordInfo = new InventoryRecordInfo();
        BeanUtils.copyProperties(inventoryRecordInfoQuery, inventoryRecordInfo);
        return inventoryRecordInfo;
    }
}
