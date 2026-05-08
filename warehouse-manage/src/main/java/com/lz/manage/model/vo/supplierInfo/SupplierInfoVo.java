package com.lz.manage.model.vo.supplierInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.SupplierInfo;
/**
 * 供应商信息Vo对象 tb_supplier_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class SupplierInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

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
     * @param supplierInfo SupplierInfo实体对象
     * @return SupplierInfoVo
     */
    public static SupplierInfoVo objToVo(SupplierInfo supplierInfo) {
        if (supplierInfo == null) {
            return null;
        }
        SupplierInfoVo supplierInfoVo = new SupplierInfoVo();
        BeanUtils.copyProperties(supplierInfo, supplierInfoVo);
        return supplierInfoVo;
    }
}
