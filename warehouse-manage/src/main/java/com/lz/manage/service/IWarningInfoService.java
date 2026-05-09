package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.WarningInfo;
import com.lz.manage.model.vo.warningInfo.WarningInfoVo;
import com.lz.manage.model.dto.warningInfo.WarningInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 库存预警Service接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface IWarningInfoService extends IService<WarningInfo>
{
    //region mybatis代码
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
     * 批量删除库存预警
     * 
     * @param ids 需要删除的库存预警主键集合
     * @return 结果
     */
    public int deleteWarningInfoByIds(Long[] ids);

    /**
     * 删除库存预警信息
     * 
     * @param id 库存预警主键
     * @return 结果
     */
    public int deleteWarningInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param warningInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<WarningInfo> getQueryWrapper(WarningInfoQuery warningInfoQuery);

    /**
     * 转换vo
     *
     * @param warningInfoList WarningInfo集合
     * @return WarningInfoVO集合
     */
    List<WarningInfoVo> convertVoList(List<WarningInfo> warningInfoList);

    void autoSendWarning();
}
