package com.lz.manage.model.vo.warningInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.WarningInfo;
/**
 * 库存预警Vo对象 tb_warning_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class WarningInfoVo implements Serializable
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

    /** 当前库存 */
    private Long currentStock;

    /** 预警阈值 */
    private Long thresholdStock;

    /** 差异数量 */
    private Long differenceStock;

    /** 预警状态 */
    private String warningStatus;

    /** 处理方式 */
    private String handlingMethod;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date handingDate;

    /** 处理人 */
    private Long handingUserId;

    /** 处理描述 */
    private String handingRemark;

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
     * @param warningInfo WarningInfo实体对象
     * @return WarningInfoVo
     */
    public static WarningInfoVo objToVo(WarningInfo warningInfo) {
        if (warningInfo == null) {
            return null;
        }
        WarningInfoVo warningInfoVo = new WarningInfoVo();
        BeanUtils.copyProperties(warningInfo, warningInfoVo);
        return warningInfoVo;
    }
}
