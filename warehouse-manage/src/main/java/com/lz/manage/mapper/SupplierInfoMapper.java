package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.SupplierInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 供应商信息Mapper接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface SupplierInfoMapper extends BaseMapper<SupplierInfo>
{
    /**
     * 查询供应商信息
     * 
     * @param id 供应商信息主键
     * @return 供应商信息
     */
    public SupplierInfo selectSupplierInfoById(Long id);

    /**
     * 查询供应商信息列表
     * 
     * @param supplierInfo 供应商信息
     * @return 供应商信息集合
     */
    public List<SupplierInfo> selectSupplierInfoList(SupplierInfo supplierInfo);

    /**
     * 新增供应商信息
     * 
     * @param supplierInfo 供应商信息
     * @return 结果
     */
    public int insertSupplierInfo(SupplierInfo supplierInfo);

    /**
     * 修改供应商信息
     * 
     * @param supplierInfo 供应商信息
     * @return 结果
     */
    public int updateSupplierInfo(SupplierInfo supplierInfo);

    /**
     * 删除供应商信息
     * 
     * @param id 供应商信息主键
     * @return 结果
     */
    public int deleteSupplierInfoById(Long id);

    /**
     * 批量删除供应商信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierInfoByIds(Long[] ids);
}
