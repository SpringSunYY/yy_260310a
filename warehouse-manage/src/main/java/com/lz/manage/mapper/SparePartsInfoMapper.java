package com.lz.manage.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.manage.model.domain.SparePartsInfo;

import java.util.List;

/**
 * 备品备件信息Mapper接口
 *
 * @author YY
 * @date 2026-05-08
 */
public interface SparePartsInfoMapper extends BaseMapper<SparePartsInfo> {
    /**
     * 查询备品备件信息
     *
     * @param id 备品备件信息主键
     * @return 备品备件信息
     */
    public SparePartsInfo selectSparePartsInfoById(Long id);

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
     * 删除备品备件信息
     *
     * @param id 备品备件信息主键
     * @return 结果
     */
    public int deleteSparePartsInfoById(Long id);

    /**
     * 批量删除备品备件信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSparePartsInfoByIds(Long[] ids);

    default SparePartsInfo selectSparePartsInfoByCode(String partsCode) {
        return selectOne(new LambdaQueryWrapper<SparePartsInfo>().eq(SparePartsInfo::getPartsCode, partsCode));
    }

    ;
}
