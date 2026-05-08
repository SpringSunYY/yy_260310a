package com.lz.manage.model.dto.warehouseInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.WarehouseInfo;
/**
 * 仓库Vo对象 tb_warehouse_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class WarehouseInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 仓库编码 */
    private String warehouseCode;

    /** 仓库名称 */
    private String warehouseName;

    /** 仓库类型 */
    private String warehouseType;

    /** 仓库位置 */
    private String location;

    /** 仓库面积 */
    private BigDecimal area;

    /** 仓库管理员 */
    private Long userId;

    /** 总容量 */
    private Long totalCapacity;

    /** 入库总数 */
    private Long inboundQuantity;

    /** 出库总数 */
    private Long outbountQuantiy;

    /** 联系电话 */
    private String contactPhone;

    /** 状态 */
    private String warehouseStatus;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param warehouseInfoInsert 插入对象
     * @return WarehouseInfoInsert
     */
    public static WarehouseInfo insertToObj(WarehouseInfoInsert warehouseInfoInsert) {
        if (warehouseInfoInsert == null) {
            return null;
        }
        WarehouseInfo warehouseInfo = new WarehouseInfo();
        BeanUtils.copyProperties(warehouseInfoInsert, warehouseInfo);
        return warehouseInfo;
    }
}
