package com.lz.manage.model.dto.supplierInfo;

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
import com.lz.manage.model.domain.SupplierInfo;
/**
 * 供应商信息Query对象 tb_supplier_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class SupplierInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 供应商编码 */
    private String supplierCode;

    /** 供应商名称 */
    private String supplierName;

    /** 信用等级 */
    private String creditLevel;

    /** 状态 */
    private String supplierStatus;

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
     * @param supplierInfoQuery 查询对象
     * @return SupplierInfo
     */
    public static SupplierInfo queryToObj(SupplierInfoQuery supplierInfoQuery) {
        if (supplierInfoQuery == null) {
            return null;
        }
        SupplierInfo supplierInfo = new SupplierInfo();
        BeanUtils.copyProperties(supplierInfoQuery, supplierInfo);
        return supplierInfo;
    }
}
