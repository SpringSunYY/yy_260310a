package com.lz.manage.model.dto.sparePartsInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.SparePartsInfo;
/**
 * 备品备件信息Vo对象 tb_spare_parts_info
 *
 * @author YY
 * @date 2026-05-08
 */
@Data
public class SparePartsInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 备件编码 */
    private String partsCode;

    /** 备件名称 */
    private String partsName;

    /** 规格型号 */
    private String specificationModel;

    /** 单位 */
    private String unit;

    /** 分类 */
    private String category;

    /** 状态 */
    private String partsStatus;

    /** 存储条件 */
    private String storageCondition;

    /** 单价 */
    private BigDecimal unitPrice;

    /** 最小库存量 */
    private Long minStock;

    /** 最大库存量 */
    private Long maxStock;

    /** 当前库存量 */
    private Long currentStock;

    /** 主供应商 */
    private Long supplierId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param sparePartsInfoInsert 插入对象
     * @return SparePartsInfoInsert
     */
    public static SparePartsInfo insertToObj(SparePartsInfoInsert sparePartsInfoInsert) {
        if (sparePartsInfoInsert == null) {
            return null;
        }
        SparePartsInfo sparePartsInfo = new SparePartsInfo();
        BeanUtils.copyProperties(sparePartsInfoInsert, sparePartsInfo);
        return sparePartsInfo;
    }
}
