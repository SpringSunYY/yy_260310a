package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.InboundOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.manage.model.domain.InboundOrderDetailInfo;

/**
 * 入库单Mapper接口
 * 
 * @author YY
 * @date 2026-05-08
 */
public interface InboundOrderInfoMapper extends BaseMapper<InboundOrderInfo>
{
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
     * 删除入库单
     * 
     * @param id 入库单主键
     * @return 结果
     */
    public int deleteInboundOrderInfoById(Long id);

    /**
     * 批量删除入库单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInboundOrderInfoByIds(Long[] ids);

    /**
     * 批量删除入库明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInboundOrderDetailInfoByInboundIds(Long[] ids);
    
    /**
     * 批量新增入库明细
     * 
     * @param inboundOrderDetailInfoList 入库明细列表
     * @return 结果
     */
    public int batchInboundOrderDetailInfo(List<InboundOrderDetailInfo> inboundOrderDetailInfoList);
    

    /**
     * 通过入库单主键删除入库明细信息
     *
     * @param id 入库单ID
     * @return 结果
     */
    public int deleteInboundOrderDetailInfoByInboundId(Long id);

    /**
     * 通过入库单号查询入库单
     *
     * @param inboundNo 入库单号
     * @return 入库单
     */
    public InboundOrderInfo selectInboundOrderInfoByNo(String inboundNo);
}
