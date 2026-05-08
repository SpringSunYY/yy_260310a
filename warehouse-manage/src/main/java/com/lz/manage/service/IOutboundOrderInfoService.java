package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.OutboundOrderInfo;
import com.lz.manage.model.vo.outboundOrderInfo.OutboundOrderInfoVo;
import com.lz.manage.model.dto.outboundOrderInfo.OutboundOrderInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 出库单Service接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface IOutboundOrderInfoService extends IService<OutboundOrderInfo>
{
    //region mybatis代码
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
     * 批量删除出库单
     * 
     * @param ids 需要删除的出库单主键集合
     * @return 结果
     */
    public int deleteOutboundOrderInfoByIds(Long[] ids);

    /**
     * 删除出库单信息
     * 
     * @param id 出库单主键
     * @return 结果
     */
    public int deleteOutboundOrderInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param outboundOrderInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<OutboundOrderInfo> getQueryWrapper(OutboundOrderInfoQuery outboundOrderInfoQuery);

    /**
     * 转换vo
     *
     * @param outboundOrderInfoList OutboundOrderInfo集合
     * @return OutboundOrderInfoVO集合
     */
    List<OutboundOrderInfoVo> convertVoList(List<OutboundOrderInfo> outboundOrderInfoList);
}
