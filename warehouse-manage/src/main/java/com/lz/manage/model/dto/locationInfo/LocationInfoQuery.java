package com.lz.manage.model.dto.locationInfo;

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
import com.lz.manage.model.domain.LocationInfo;
/**
 * 库位Query对象 tb_location_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class LocationInfoQuery implements Serializable
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

    /** 状态 */
    private String locationStatus;

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
     * @param locationInfoQuery 查询对象
     * @return LocationInfo
     */
    public static LocationInfo queryToObj(LocationInfoQuery locationInfoQuery) {
        if (locationInfoQuery == null) {
            return null;
        }
        LocationInfo locationInfo = new LocationInfo();
        BeanUtils.copyProperties(locationInfoQuery, locationInfo);
        return locationInfo;
    }
}
