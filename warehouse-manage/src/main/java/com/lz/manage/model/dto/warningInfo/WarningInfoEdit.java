package com.lz.manage.model.dto.warningInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.WarningInfo;
/**
 * 库存预警Vo对象 tb_warning_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class WarningInfoEdit implements Serializable
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

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param warningInfoEdit 编辑对象
     * @return WarningInfo
     */
    public static WarningInfo editToObj(WarningInfoEdit warningInfoEdit) {
        if (warningInfoEdit == null) {
            return null;
        }
        WarningInfo warningInfo = new WarningInfo();
        BeanUtils.copyProperties(warningInfoEdit, warningInfo);
        return warningInfo;
    }
}
