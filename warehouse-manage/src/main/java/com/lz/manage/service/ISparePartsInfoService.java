package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.SparePartsInfo;
import com.lz.manage.model.vo.sparePartsInfo.SparePartsInfoVo;
import com.lz.manage.model.dto.sparePartsInfo.SparePartsInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 备品备件信息Service接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface ISparePartsInfoService extends IService<SparePartsInfo>
{
    //region mybatis代码
    /**
     * 查询备品备件信息
     * 
     * @param id 备品备件信息主键
     * @return 备品备件信息
     */
    public SparePartsInfo selectSparePartsInfoById(Long id);

    SparePartsInfo selectSparePartsInfoByCode(String partsCode);


    /**
     * 查询备品备件信息列表
     * 
     * @param sparePartsInfo 备品备件信息
     * @return 备品备件信息集合
     */
    public List<SparePartsInfo> selectSparePartsInfoList(SparePartsInfo sparePartsInfo);

    /**
     * 新增备品备件信息
     * 
     * @param sparePartsInfo 备品备件信息
     * @return 结果
     */
    public int insertSparePartsInfo(SparePartsInfo sparePartsInfo);

    /**
     * 修改备品备件信息
     * 
     * @param sparePartsInfo 备品备件信息
     * @return 结果
     */
    public int updateSparePartsInfo(SparePartsInfo sparePartsInfo);

    /**
     * 批量删除备品备件信息
     * 
     * @param ids 需要删除的备品备件信息主键集合
     * @return 结果
     */
    public int deleteSparePartsInfoByIds(Long[] ids);

    /**
     * 删除备品备件信息信息
     * 
     * @param id 备品备件信息主键
     * @return 结果
     */
    public int deleteSparePartsInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param sparePartsInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<SparePartsInfo> getQueryWrapper(SparePartsInfoQuery sparePartsInfoQuery);

    /**
     * 转换vo
     *
     * @param sparePartsInfoList SparePartsInfo集合
     * @return SparePartsInfoVO集合
     */
    List<SparePartsInfoVo> convertVoList(List<SparePartsInfo> sparePartsInfoList);
}
