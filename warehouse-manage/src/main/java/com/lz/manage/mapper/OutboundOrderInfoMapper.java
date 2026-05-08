package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.OutboundOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.manage.model.domain.OutboundOrderDetailInfo;

/**
 * 出库单Mapper接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface OutboundOrderInfoMapper extends BaseMapper<OutboundOrderInfo>
{
    /**
     * 查询出库单
     * 
     * @param id 出库单主键
     * @return 出库单
     */
    public OutboundOrderInfo selectOutboundOrderInfoById(Long id);

    /**
     * 查询出库单列表
     * 
     * @param outboundOrderInfo 出库单
     * @return 出库单集合
     */
    public List<OutboundOrderInfo> selectOutboundOrderInfoList(OutboundOrderInfo outboundOrderInfo);

    /**
     * 新增出库单
     * 
     * @param outboundOrderInfo 出库单
     * @return 结果
     */
    public int insertOutboundOrderInfo(OutboundOrderInfo outboundOrderInfo);

    /**
     * 修改出库单
     * 
     * @param outboundOrderInfo 出库单
     * @return 结果
     */
    public int updateOutboundOrderInfo(OutboundOrderInfo outboundOrderInfo);

    /**
     * 删除出库单
     * 
     * @param id 出库单主键
     * @return 结果
     */
    public int deleteOutboundOrderInfoById(Long id);

    /**
     * 批量删除出库单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOutboundOrderInfoByIds(Long[] ids);

    /**
     * 批量删除出库明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOutboundOrderDetailInfoByOutboundIds(Long[] ids);
    
    /**
     * 批量新增出库明细
     * 
     * @param outboundOrderDetailInfoList 出库明细列表
     * @return 结果
     */
    public int batchOutboundOrderDetailInfo(List<OutboundOrderDetailInfo> outboundOrderDetailInfoList);
    

    /**
     * 通过出库单主键删除出库明细信息
     * 
     * @param id 出库单ID
     * @return 结果
     */
    public int deleteOutboundOrderDetailInfoByOutboundId(Long id);
}
