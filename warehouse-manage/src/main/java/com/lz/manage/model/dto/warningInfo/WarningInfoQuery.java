package com.lz.manage.model.dto.warningInfo;

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
import com.lz.manage.model.domain.WarningInfo;
/**
 * 库存预警Query对象 tb_warning_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class WarningInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 备件编码 */
    private String partsCode;

    /** 备件名称 */
    private String partsName;

    /** 预警类型 */
    private String warnType;

    /** 预警状态 */
    private String warningStatus;

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
     * @param warningInfoQuery 查询对象
     * @return WarningInfo
     */
    public static WarningInfo queryToObj(WarningInfoQuery warningInfoQuery) {
        if (warningInfoQuery == null) {
            return null;
        }
        WarningInfo warningInfo = new WarningInfo();
        BeanUtils.copyProperties(warningInfoQuery, warningInfo);
        return warningInfo;
    }
}
