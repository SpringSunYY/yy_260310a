package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.StocktakingOrderInfo;
import com.lz.manage.model.vo.stocktakingOrderInfo.StocktakingOrderInfoVo;
import com.lz.manage.model.dto.stocktakingOrderInfo.StocktakingOrderInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 盘点单Service接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface IStocktakingOrderInfoService extends IService<StocktakingOrderInfo>
{
    //region mybatis代码
    /**
     * 查询盘点单
     * 
     * @param id 盘点单主键
     * @return 盘点单
     */
    public StocktakingOrderInfo selectStocktakingOrderInfoById(Long id);

    /**
     * 查询盘点单列表
     * 
     * @param stocktakingOrderInfo 盘点单
     * @return 盘点单集合
     */
    public List<StocktakingOrderInfo> selectStocktakingOrderInfoList(StocktakingOrderInfo stocktakingOrderInfo);

    /**
     * 新增盘点单
     * 
     * @param stocktakingOrderInfo 盘点单
     * @return 结果
     */
    public int insertStocktakingOrderInfo(StocktakingOrderInfo stocktakingOrderInfo);

    /**
     * 修改盘点单
     * 
     * @param stocktakingOrderInfo 盘点单
     * @return 结果
     */
    public int updateStocktakingOrderInfo(StocktakingOrderInfo stocktakingOrderInfo);

    /**
     * 批量删除盘点单
     * 
     * @param ids 需要删除的盘点单主键集合
     * @return 结果
     */
    public int deleteStocktakingOrderInfoByIds(Long[] ids);

    /**
     * 删除盘点单信息
     * 
     * @param id 盘点单主键
     * @return 结果
     */
    public int deleteStocktakingOrderInfoById(Long id);
    //endregion

    /**
     * 审核盘点单
     *
     * @param stocktakingOrderInfo 盘点单
     * @return 结果
     */
    public int auditStocktakingOrderInfo(StocktakingOrderInfo stocktakingOrderInfo);
    //endregion
    /**
     * 获取查询条件
     *
     * @param stocktakingOrderInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<StocktakingOrderInfo> getQueryWrapper(StocktakingOrderInfoQuery stocktakingOrderInfoQuery);

    /**
     * 转换vo
     *
     * @param stocktakingOrderInfoList StocktakingOrderInfo集合
     * @return StocktakingOrderInfoVO集合
     */
    List<StocktakingOrderInfoVo> convertVoList(List<StocktakingOrderInfo> stocktakingOrderInfoList);
}
