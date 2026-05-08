package com.lz.manage.model.vo.sparePartsInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.SparePartsInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 备品备件信息Vo对象 tb_spare_parts_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class SparePartsInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 备件编码
     */
    private String partsCode;

    /**
     * 备件名称
     */
    private String partsName;

    /**
     * 规格型号
     */
    private String specificationModel;

    /**
     * 单位
     */
    private String unit;

    /**
     * 分类
     */
    private String category;

    /**
     * 状态
     */
    private String partsStatus;

    /**
     * 存储条件
     */
    private String storageCondition;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 最小库存量
     */
    private Long minStock;

    /**
     * 最大库存量
     */
    private Long maxStock;

    /**
     * 当前库存量
     */
    private Long currentStock;

    /**
     * 主供应商
     */
    private Long supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


    /**
     * 对象转封装类
     *
     * @param sparePartsInfo SparePartsInfo实体对象
     * @return SparePartsInfoVo
     */
    public static SparePartsInfoVo objToVo(SparePartsInfo sparePartsInfo) {
        if (sparePartsInfo == null) {
            return null;
        }
        SparePartsInfoVo sparePartsInfoVo = new SparePartsInfoVo();
        BeanUtils.copyProperties(sparePartsInfo, sparePartsInfoVo);
        return sparePartsInfoVo;
    }
}
