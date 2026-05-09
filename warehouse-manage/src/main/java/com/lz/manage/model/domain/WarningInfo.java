package com.lz.manage.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lz.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 库存预警对象 tb_warning_info
 *
 * @author YY
 * @date 2026-05-08
 */
@TableName("tb_warning_info")
@Data
public class WarningInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 备件编码
     */
    @Excel(name = "备件编码")
    private String partsCode;

    /**
     * 备件名称
     */
    @Excel(name = "备件名称")
    private String partsName;

    /**
     * 预警类型
     */
    @Excel(name = "预警类型", dictType = "warehouse_warning_type")
    private String warnType;

    /**
     * 当前库存
     */
    @Excel(name = "当前库存")
    private Long currentStock;

    /**
     * 预警阈值
     */
    @Excel(name = "预警阈值")
    private Long thresholdStock;

    /**
     * 差异数量
     */
    @Excel(name = "差异数量")
    private Long differenceStock;

    /**
     * 预警状态
     */
    @Excel(name = "预警状态", dictType = "warehouse_warning_status")
    private String warningStatus;

    /**
     * 处理方式
     */
    @Excel(name = "处理方式")
    private String handlingMethod;

    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date handingDate;

    /**
     * 处理人
     */
    @Excel(name = "处理人", type = Excel.Type.IMPORT)
    private Long handingUserId;
    @TableField(exist = false)
    @Excel(name = "处理人", type = Excel.Type.EXPORT)
    private String handingUserName;

    /**
     * 处理描述
     */
    @Excel(name = "处理描述")
    private String handingRemark;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新人
     */
    @Excel(name = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
