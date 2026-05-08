package com.lz.manage.model.dto.sparePartsInfo;

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
import com.lz.manage.model.domain.SparePartsInfo;
/**
 * 备品备件信息Query对象 tb_spare_parts_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class SparePartsInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 备件编码 */
    private String partsCode;

    /** 备件名称 */
    private String partsName;

    /** 规格型号 */
    private String specificationModel;

    /** 分类 */
    private String category;

    /** 状态 */
    private String partsStatus;

    /** 主供应商 */
    private Long supplierId;

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
     * @param sparePartsInfoQuery 查询对象
     * @return SparePartsInfo
     */
    public static SparePartsInfo queryToObj(SparePartsInfoQuery sparePartsInfoQuery) {
        if (sparePartsInfoQuery == null) {
            return null;
        }
        SparePartsInfo sparePartsInfo = new SparePartsInfo();
        BeanUtils.copyProperties(sparePartsInfoQuery, sparePartsInfo);
        return sparePartsInfo;
    }
}
