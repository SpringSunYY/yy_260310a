package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.StocktakingOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.manage.model.domain.StocktakingOrderDetailInfo;

/**
 * 盘点单Mapper接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface StocktakingOrderInfoMapper extends BaseMapper<StocktakingOrderInfo>
{
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
     * 删除盘点单
     * 
     * @param id 盘点单主键
     * @return 结果
     */
    public int deleteStocktakingOrderInfoById(Long id);

    /**
     * 批量删除盘点单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStocktakingOrderInfoByIds(Long[] ids);

    /**
     * 批量删除盘点明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStocktakingOrderDetailInfoByStocktakingIds(Long[] ids);
    
    /**
     * 批量新增盘点明细
     * 
     * @param stocktakingOrderDetailInfoList 盘点明细列表
     * @return 结果
     */
    public int batchStocktakingOrderDetailInfo(List<StocktakingOrderDetailInfo> stocktakingOrderDetailInfoList);
    

    /**
     * 通过盘点单主键删除盘点明细信息
     *
     * @param id 盘点单ID
     * @return 结果
     */
    public int deleteStocktakingOrderDetailInfoByStocktakingId(Long id);

    /**
     * 通过盘点单号查询盘点单
     *
     * @param stocktakingNo 盘点单号
     * @return 盘点单
     */
    public StocktakingOrderInfo selectStocktakingOrderInfoByNo(String stocktakingNo);
}
