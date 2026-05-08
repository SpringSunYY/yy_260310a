package com.lz.manage.model.dto.locationInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.LocationInfo;
/**
 * 库位Vo对象 tb_location_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class LocationInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 仓库 */
    private Long warehouseId;

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
    private Long outbountQuantiy;

    /** 状态 */
    private String locationStatus;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param locationInfoEdit 编辑对象
     * @return LocationInfo
     */
    public static LocationInfo editToObj(LocationInfoEdit locationInfoEdit) {
        if (locationInfoEdit == null) {
            return null;
        }
        LocationInfo locationInfo = new LocationInfo();
        BeanUtils.copyProperties(locationInfoEdit, locationInfo);
        return locationInfo;
    }
}
