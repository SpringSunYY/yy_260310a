package com.lz.manage.model.vo.locationInfo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.LocationInfo;
/**
 * 库位Vo对象 tb_location_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class LocationInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 仓库 */
    private Long warehouseId;
    private String warehouseName;

    /** 库位编码 */
    private String locationCode;

    /** 库位名称 */
    private String locationName;

    /** 父级 */
    private Long parentId;

    /** 库位层级 */
    private Long level;

    /** 容量 */
    private BigDecimal capacity;

    /** 入库总数 */
    private Long inboundQuantity;

    /** 出库总数 */
    private Long outboundQuantity;

    /** 状态 */
    private String locationStatus;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;


     /**
     * 对象转封装类
     *
     * @param locationInfo LocationInfo实体对象
     * @return LocationInfoVo
     */
    public static LocationInfoVo objToVo(LocationInfo locationInfo) {
        if (locationInfo == null) {
            return null;
        }
        LocationInfoVo locationInfoVo = new LocationInfoVo();
        BeanUtils.copyProperties(locationInfo, locationInfoVo);
        return locationInfoVo;
    }
}
