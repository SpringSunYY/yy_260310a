package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.InboundOrderInfo;
import com.lz.manage.model.vo.inboundOrderInfo.InboundOrderInfoVo;
import com.lz.manage.model.dto.inboundOrderInfo.InboundOrderInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 入库单Service接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface IInboundOrderInfoService extends IService<InboundOrderInfo>
{
    //region mybatis代码
    /**
     * 查询入库单
     * 
     * @param id 入库单主键
     * @return 入库单
     */
    public InboundOrderInfo selectInboundOrderInfoById(Long id);

    /**
     * 查询入库单列表
     * 
     * @param inboundOrderInfo 入库单
     * @return 入库单集合
     */
    public List<InboundOrderInfo> selectInboundOrderInfoList(InboundOrderInfo inboundOrderInfo);

    /**
     * 新增入库单
     * 
     * @param inboundOrderInfo 入库单
     * @return 结果
     */
    public int insertInboundOrderInfo(InboundOrderInfo inboundOrderInfo);

    /**
     * 修改入库单
     * 
     * @param inboundOrderInfo 入库单
     * @return 结果
     */
    public int updateInboundOrderInfo(InboundOrderInfo inboundOrderInfo);

    /**
     * 批量删除入库单
     * 
     * @param ids 需要删除的入库单主键集合
     * @return 结果
     */
    public int deleteInboundOrderInfoByIds(Long[] ids);

    /**
     * 删除入库单信息
     * 
     * @param id 入库单主键
     * @return 结果
     */
    public int deleteInboundOrderInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param inboundOrderInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<InboundOrderInfo> getQueryWrapper(InboundOrderInfoQuery inboundOrderInfoQuery);

    /**
     * 转换vo
     *
     * @param inboundOrderInfoList InboundOrderInfo集合
     * @return InboundOrderInfoVO集合
     */
    List<InboundOrderInfoVo> convertVoList(List<InboundOrderInfo> inboundOrderInfoList);
}
