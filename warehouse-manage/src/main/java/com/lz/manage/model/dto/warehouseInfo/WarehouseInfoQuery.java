package com.lz.manage.model.dto.warehouseInfo;

import java.util.Map;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.WarehouseInfo;
/**
 * 仓库Query对象 tb_warehouse_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class WarehouseInfoQuery implements Serializable
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

    /** 联系电话 */
    private String contactPhone;

    /** 状态 */
    private String warehouseStatus;

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
     * @param warehouseInfoQuery 查询对象
     * @return WarehouseInfo
     */
    public static WarehouseInfo queryToObj(WarehouseInfoQuery warehouseInfoQuery) {
        if (warehouseInfoQuery == null) {
            return null;
        }
        WarehouseInfo warehouseInfo = new WarehouseInfo();
        BeanUtils.copyProperties(warehouseInfoQuery, warehouseInfo);
        return warehouseInfo;
    }
}
