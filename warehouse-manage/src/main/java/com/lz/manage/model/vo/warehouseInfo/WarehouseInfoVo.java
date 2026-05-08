package com.lz.manage.model.vo.warehouseInfo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.WarehouseInfo;
/**
 * 仓库Vo对象 tb_warehouse_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class WarehouseInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

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
     * @param warehouseInfo WarehouseInfo实体对象
     * @return WarehouseInfoVo
     */
    public static WarehouseInfoVo objToVo(WarehouseInfo warehouseInfo) {
        if (warehouseInfo == null) {
            return null;
        }
        WarehouseInfoVo warehouseInfoVo = new WarehouseInfoVo();
        BeanUtils.copyProperties(warehouseInfo, warehouseInfoVo);
        return warehouseInfoVo;
    }
}
