package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.WarningInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 库存预警Mapper接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface WarningInfoMapper extends BaseMapper<WarningInfo>
{
    /**
     * 查询库存预警
     * 
     * @param id 库存预警主键
     * @return 库存预警
     */
    public WarningInfo selectWarningInfoById(Long id);

    /**
     * 查询库存预警列表
     * 
     * @param warningInfo 库存预警
     * @return 库存预警集合
     */
    public List<WarningInfo> selectWarningInfoList(WarningInfo warningInfo);

    /**
     * 新增库存预警
     * 
     * @param warningInfo 库存预警
     * @return 结果
     */
    public int insertWarningInfo(WarningInfo warningInfo);

    /**
     * 修改库存预警
     * 
     * @param warningInfo 库存预警
     * @return 结果
     */
    public int updateWarningInfo(WarningInfo warningInfo);

    /**
     * 删除库存预警
     * 
     * @param id 库存预警主键
     * @return 结果
     */
    public int deleteWarningInfoById(Long id);

    /**
     * 批量删除库存预警
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarningInfoByIds(Long[] ids);
}
