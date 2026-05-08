package com.lz.manage.model.dto.supplierInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.SupplierInfo;
/**
 * 供应商信息Vo对象 tb_supplier_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class SupplierInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 供应商编码 */
    private String supplierCode;

    /** 供应商名称 */
    private String supplierName;

    /** 联系人 */
    private String contactPerson;

    /** 联系电话 */
    private String contactPhone;

    /** 联系邮箱 */
    private String contactEmail;

    /** 地址 */
    private String address;

    /** 信用等级 */
    private String creditLevel;

    /** 状态 */
    private String supplierStatus;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param supplierInfoInsert 插入对象
     * @return SupplierInfoInsert
     */
    public static SupplierInfo insertToObj(SupplierInfoInsert supplierInfoInsert) {
        if (supplierInfoInsert == null) {
            return null;
        }
        SupplierInfo supplierInfo = new SupplierInfo();
        BeanUtils.copyProperties(supplierInfoInsert, supplierInfo);
        return supplierInfo;
    }
}
