package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.OutboundOrderDetailInfo;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.enums.*;
import com.lz.manage.model.domain.*;
import com.lz.manage.model.dto.inventoryTransactionInfo.InventoryTransactionInfoDto;
import com.lz.manage.service.*;
import com.lz.system.service.ISysUserService;
import com.lz.system.service.ISysDeptService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import com.lz.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.lz.manage.model.domain.OutboundOrderDetailInfo;
import com.lz.manage.mapper.OutboundOrderInfoMapper;
import com.lz.manage.model.domain.OutboundOrderInfo;
import com.lz.manage.service.IOutboundOrderInfoService;
import com.lz.manage.model.dto.outboundOrderInfo.OutboundOrderInfoQuery;
import com.lz.manage.model.vo.outboundOrderInfo.OutboundOrderInfoVo;

/**
 * 出库单Service业务层处理
 *
 * @author YY
 * @date 2026-05-08
 */
@Service
public class OutboundOrderInfoServiceImpl extends ServiceImpl<OutboundOrderInfoMapper, OutboundOrderInfo> implements IOutboundOrderInfoService
{

    @Resource
    private OutboundOrderInfoMapper outboundOrderInfoMapper;

    @Resource
    private IWarehouseInfoService warehouseInfoService;

    @Resource
    private ILocationInfoService locationInfoService;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private IInventoryTransactionInfoService inventoryTransactionInfoService;

    //region mybatis代码
    /**
     * 查询出库单
     *
     * @param id 出库单主键
     * @return 出库单
     */
    @Override
    public OutboundOrderInfo selectOutboundOrderInfoById(Long id)
    {
        return outboundOrderInfoMapper.selectOutboundOrderInfoById(id);
    }

    /**
     * 查询出库单列表
     *
     * @param outboundOrderInfo 出库单
     * @return 出库单
     */
    @Override
    public List<OutboundOrderInfo> selectOutboundOrderInfoList(OutboundOrderInfo outboundOrderInfo)
    {
        List<OutboundOrderInfo> outboundOrderInfoList = outboundOrderInfoMapper.selectOutboundOrderInfoList(outboundOrderInfo);
        for (OutboundOrderInfo info : outboundOrderInfoList) {
            // 获取仓库名称
            if (StringUtils.isNotNull(info.getWarehouseId())) {
                WarehouseInfo warehouseInfo = warehouseInfoService.selectWarehouseInfoById(info.getWarehouseId());
                if (StringUtils.isNotNull(warehouseInfo)) {
                    info.setWarehouseName(warehouseInfo.getWarehouseName());
                }
            }
            // 获取领用人姓名
            if (StringUtils.isNotNull(info.getRecipientId())) {
                com.lz.common.core.domain.entity.SysUser sysUser = userService.selectUserById(info.getRecipientId());
                if (StringUtils.isNotNull(sysUser)) {
                    info.setRecipientName(sysUser.getNickName());
                }
            }
            // 获取领用部门名称
            if (StringUtils.isNotNull(info.getRecipientDeptId())) {
                com.lz.common.core.domain.entity.SysDept sysDept = deptService.selectDeptById(info.getRecipientDeptId());
                if (StringUtils.isNotNull(sysDept)) {
                    info.setRecipientDeptName(sysDept.getDeptName());
                }
            }
            // 获取经办人姓名
            if (StringUtils.isNotNull(info.getOperatorId())) {
                com.lz.common.core.domain.entity.SysUser sysUser = userService.selectUserById(info.getOperatorId());
                if (StringUtils.isNotNull(sysUser)) {
                    info.setOperatorName(sysUser.getNickName());
                }
            }
            // 获取审核人姓名
            if (StringUtils.isNotNull(info.getReviewerId())) {
                com.lz.common.core.domain.entity.SysUser sysUser = userService.selectUserById(info.getReviewerId());
                if (StringUtils.isNotNull(sysUser)) {
                    info.setReviewerName(sysUser.getNickName());
                }
            }
        }
        return outboundOrderInfoList;
    }

    /**
     * 新增出库单
     *
     * @param outboundOrderInfo 出库单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertOutboundOrderInfo(OutboundOrderInfo outboundOrderInfo)
    {
        // 设置默认值
        outboundOrderInfo.setOutboundStatus(WarehouseOutboundStatusEnum.WAREHOUSE_OUTBOUND_STATUS_0.getValue());
        outboundOrderInfo.setReviewStatus(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_0.getValue());
        outboundOrderInfo.setCreateBy(SecurityUtils.getUsername());
        outboundOrderInfo.setCreateTime(DateUtils.getNowDate());

        // 校验出库单号是否存在
        OutboundOrderInfo outboundOrderInfoByNo = outboundOrderInfoMapper.selectOutboundOrderInfoByNo(outboundOrderInfo.getOutboundNo());
        ThrowUtils.throwIf(StringUtils.isNotNull(outboundOrderInfoByNo), "出库单号已存在");

        // 判断仓库是否存在
        if (StringUtils.isNotNull(outboundOrderInfo.getWarehouseId())) {
            WarehouseInfo warehouseInfo = warehouseInfoService.selectWarehouseInfoById(outboundOrderInfo.getWarehouseId());
            ThrowUtils.throwIf(StringUtils.isNull(warehouseInfo), "仓库不存在");
        }

        // 如果有领用人，自动获取领用部门
        if (StringUtils.isNotNull(outboundOrderInfo.getRecipientId())) {
            com.lz.common.core.domain.entity.SysUser sysUser = userService.selectUserById(outboundOrderInfo.getRecipientId());
            ThrowUtils.throwIf(StringUtils.isNull(sysUser), "领用人不存在");
            outboundOrderInfo.setRecipientDeptId(sysUser.getDeptId());
            if (StringUtils.isNotNull(sysUser.getDeptId())) {
                com.lz.common.core.domain.entity.SysDept sysDept = deptService.selectDeptById(sysUser.getDeptId());
                if (StringUtils.isNotNull(sysDept)) {
                    outboundOrderInfo.setRecipientDeptName(sysDept.getDeptName());
                }
            }
        }

        // 校验明细
        validateOutboundOrderDetailInfo(outboundOrderInfo);

        int rows = outboundOrderInfoMapper.insertOutboundOrderInfo(outboundOrderInfo);
        insertOutboundOrderDetailInfo(outboundOrderInfo);
        return rows;
    }

    /**
     * 校验出库明细
     */
    private void validateOutboundOrderDetailInfo(OutboundOrderInfo outboundOrderInfo) {
        List<OutboundOrderDetailInfo> detailList = outboundOrderInfo.getOutboundOrderDetailInfoList();
        ThrowUtils.throwIf(StringUtils.isNull(detailList) || detailList.isEmpty(), "出库明细不能为空");

        for (OutboundOrderDetailInfo detail : detailList) {
            ThrowUtils.throwIf(StringUtils.isEmpty(detail.getPartsCode()), "备件编号不能为空");
            ThrowUtils.throwIf(StringUtils.isNull(detail.getOutboundQuantity()), "出库数量不能为空");
            ThrowUtils.throwIf(detail.getOutboundQuantity() <= 0, "出库数量必须大于0");
        }
    }

    /**
     * 修改出库单
     *
     * @param outboundOrderInfo 出库单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateOutboundOrderInfo(OutboundOrderInfo outboundOrderInfo)
    {
        // 判断出库单是否存在
        OutboundOrderInfo existingOutboundOrder = outboundOrderInfoMapper.selectOutboundOrderInfoById(outboundOrderInfo.getId());
        ThrowUtils.throwIf(StringUtils.isNull(existingOutboundOrder), "出库单不存在");

        // 判断出库单是否已审核通过
        ThrowUtils.throwIf(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue().equals(existingOutboundOrder.getReviewStatus()), "已审核通过的出库单不可修改");

        // 校验出库单号是否存在（排除自身）
        OutboundOrderInfo outboundOrderInfoByNo = outboundOrderInfoMapper.selectOutboundOrderInfoByNo(outboundOrderInfo.getOutboundNo());
        ThrowUtils.throwIf(StringUtils.isNotNull(outboundOrderInfoByNo)
                           && !outboundOrderInfoByNo.getId().equals(outboundOrderInfo.getId()),
                "出库单号已存在");

        // 判断仓库是否存在
        if (StringUtils.isNotNull(outboundOrderInfo.getWarehouseId())) {
            WarehouseInfo warehouseInfo = warehouseInfoService.selectWarehouseInfoById(outboundOrderInfo.getWarehouseId());
            ThrowUtils.throwIf(StringUtils.isNull(warehouseInfo), "仓库不存在");
        }

        // 如果有领用人，自动获取领用部门
        if (StringUtils.isNotNull(outboundOrderInfo.getRecipientId())) {
            com.lz.common.core.domain.entity.SysUser sysUser = userService.selectUserById(outboundOrderInfo.getRecipientId());
            ThrowUtils.throwIf(StringUtils.isNull(sysUser), "领用人不存在");
            outboundOrderInfo.setRecipientDeptId(sysUser.getDeptId());
            if (StringUtils.isNotNull(sysUser.getDeptId())) {
                com.lz.common.core.domain.entity.SysDept sysDept = deptService.selectDeptById(sysUser.getDeptId());
                if (StringUtils.isNotNull(sysDept)) {
                    outboundOrderInfo.setRecipientDeptName(sysDept.getDeptName());
                }
            }
        }

        outboundOrderInfo.setCreateBy(existingOutboundOrder.getCreateBy());
        outboundOrderInfo.setCreateTime(existingOutboundOrder.getCreateTime());
        outboundOrderInfo.setUpdateTime(DateUtils.getNowDate());
        outboundOrderInfoMapper.deleteOutboundOrderDetailInfoByOutboundId(outboundOrderInfo.getId());

        // 校验明细
        validateOutboundOrderDetailInfo(outboundOrderInfo);

        insertOutboundOrderDetailInfo(outboundOrderInfo);
        return outboundOrderInfoMapper.updateOutboundOrderInfo(outboundOrderInfo);
    }

    /**
     * 审核出库单
     */
    @Override
    @Transactional
    public int auditOutboundOrderInfo(OutboundOrderInfo outboundOrderInfo) {
        // 判断出库单是否存在
        OutboundOrderInfo existingOutboundOrder = outboundOrderInfoMapper.selectOutboundOrderInfoById(outboundOrderInfo.getId());
        ThrowUtils.throwIf(StringUtils.isNull(existingOutboundOrder), "出库单不存在");

        // 判断出库单是否已审核
        ThrowUtils.throwIf(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue().equals(existingOutboundOrder.getReviewStatus())
                        || WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_2.getValue().equals(existingOutboundOrder.getReviewStatus()),
                "出库单已审核，不可重复审核");

        // 校验明细
        validateOutboundOrderDetailInfo(outboundOrderInfo);

        outboundOrderInfo.setCreateBy(existingOutboundOrder.getCreateBy());
        outboundOrderInfo.setCreateTime(existingOutboundOrder.getCreateTime());
        outboundOrderInfo.setOutboundNo(existingOutboundOrder.getOutboundNo());
        Date nowDate = DateUtils.getNowDate();

        // 如果审核状态发生变化，设置审核人和审核时间
        if (!outboundOrderInfo.getReviewStatus().equals(existingOutboundOrder.getReviewStatus())) {
            outboundOrderInfo.setReviewerId(SecurityUtils.getUserId());
            outboundOrderInfo.setReviewTime(nowDate);
        }

        // 如果审核同意，同时更新出库状态为已出库
        if (WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue().equals(outboundOrderInfo.getReviewStatus())) {
            outboundOrderInfo.setOutboundStatus(WarehouseOutboundStatusEnum.WAREHOUSE_OUTBOUND_STATUS_1.getValue());

            // 创建出库流水
            List<InventoryTransactionInfoDto> transactionInfoDtos = new ArrayList<>();
            // 遍历明细
            for (OutboundOrderDetailInfo detail : outboundOrderInfo.getOutboundOrderDetailInfoList()) {
                InventoryTransactionInfoDto transactionInfoDto = getInventoryTransactionInfoDto(outboundOrderInfo, detail, nowDate);
                transactionInfoDtos.add(transactionInfoDto);
            }
            inventoryTransactionInfoService.insertInventoryTransactionInfos(transactionInfoDtos);
        }

        // 如果审核拒绝，更新出库状态为已取消
        if (WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_2.getValue().equals(outboundOrderInfo.getReviewStatus())) {
            outboundOrderInfo.setOutboundStatus(WarehouseOutboundStatusEnum.WAREHOUSE_OUTBOUND_STATUS_2.getValue());
        }

        // 删除原有明细并插入新明细
        outboundOrderInfoMapper.deleteOutboundOrderDetailInfoByOutboundId(outboundOrderInfo.getId());
        insertOutboundOrderDetailInfo(outboundOrderInfo);

        outboundOrderInfo.setUpdateTime(nowDate);
        return outboundOrderInfoMapper.updateOutboundOrderInfo(outboundOrderInfo);
    }

    private static InventoryTransactionInfoDto getInventoryTransactionInfoDto(OutboundOrderInfo outboundOrderInfo, OutboundOrderDetailInfo detail, Date nowDate) {
        InventoryTransactionInfoDto transactionInfoDto = new InventoryTransactionInfoDto();
        transactionInfoDto.setPartsCode(detail.getPartsCode());
        transactionInfoDto.setWarehouseId(outboundOrderInfo.getWarehouseId());
        transactionInfoDto.setLocationId(detail.getLocationId());
        transactionInfoDto.setBatchNo(detail.getBatchNo());
        // 出库流水类型
        transactionInfoDto.setTransactionType(WarehouseTransactionTypeEnum.WAREHOUSE_TRANSACTION_TYPE_1.getValue());
        transactionInfoDto.setRelatedOrderNo(outboundOrderInfo.getOutboundNo());
        transactionInfoDto.setOperatorId(outboundOrderInfo.getOperatorId());
        transactionInfoDto.setTransactionTime(outboundOrderInfo.getOutboundDate());
        transactionInfoDto.setCreateBy(outboundOrderInfo.getCreateBy());
        transactionInfoDto.setCreateTime(nowDate);
        // 变动数量为负数（出库）
        transactionInfoDto.setChangeQuantity(-detail.getOutboundQuantity());
        return transactionInfoDto;
    }

    /**
     * 批量删除出库单
     *
     * @param ids 需要删除的出库单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOutboundOrderInfoByIds(Long[] ids)
    {
        for (Long id : ids) {
            OutboundOrderInfo existingOutboundOrder = outboundOrderInfoMapper.selectOutboundOrderInfoById(id);
            ThrowUtils.throwIf(StringUtils.isNull(existingOutboundOrder), "出库单不存在");
            // 判断出库单是否已审核通过
            ThrowUtils.throwIf(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue().equals(existingOutboundOrder.getReviewStatus()), "已审核通过的出库单不可删除");
        }
        outboundOrderInfoMapper.deleteOutboundOrderDetailInfoByOutboundIds(ids);
        return outboundOrderInfoMapper.deleteOutboundOrderInfoByIds(ids);
    }

    /**
     * 删除出库单信息
     *
     * @param id 出库单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOutboundOrderInfoById(Long id)
    {
        OutboundOrderInfo existingOutboundOrder = outboundOrderInfoMapper.selectOutboundOrderInfoById(id);
        ThrowUtils.throwIf(StringUtils.isNull(existingOutboundOrder), "出库单不存在");
        // 判断出库单是否已审核通过
        ThrowUtils.throwIf(WarehouseOrderApplicantStatusEnum.WAREHOUSE_ORDER_APPLICANT_STATUS_1.getValue().equals(existingOutboundOrder.getReviewStatus()), "已审核通过的出库单不可删除");
        outboundOrderInfoMapper.deleteOutboundOrderDetailInfoByOutboundId(id);
        return outboundOrderInfoMapper.deleteOutboundOrderInfoById(id);
    }

    /**
     * 新增出库明细信息
     *
     * @param outboundOrderInfo 出库单对象
     */
    public void insertOutboundOrderDetailInfo(OutboundOrderInfo outboundOrderInfo)
    {
        List<OutboundOrderDetailInfo> outboundOrderDetailInfoList = outboundOrderInfo.getOutboundOrderDetailInfoList();
        Long id = outboundOrderInfo.getId();
        if (StringUtils.isNotNull(outboundOrderDetailInfoList))
        {
            List<OutboundOrderDetailInfo> list = new ArrayList<OutboundOrderDetailInfo>();
            for (OutboundOrderDetailInfo outboundOrderDetailInfo : outboundOrderDetailInfoList)
            {
                outboundOrderDetailInfo.setOutboundId(id);
                if (StringUtils.isNotEmpty(outboundOrderInfo.getCreateBy())) {
                    outboundOrderDetailInfo.setCreateBy(outboundOrderInfo.getCreateBy());
                }
                if (StringUtils.isNotEmpty(outboundOrderInfo.getUpdateBy())) {
                    outboundOrderDetailInfo.setUpdateBy(outboundOrderInfo.getUpdateBy());
                }
                if (StringUtils.isNotNull(outboundOrderInfo.getCreateTime())) {
                    outboundOrderDetailInfo.setCreateTime(outboundOrderInfo.getCreateTime());
                }
                if (StringUtils.isNotNull(outboundOrderInfo.getUpdateTime())) {
                    outboundOrderDetailInfo.setUpdateTime(outboundOrderInfo.getUpdateTime());
                }
                list.add(outboundOrderDetailInfo);
            }
            if (!list.isEmpty())
            {
                outboundOrderInfoMapper.batchOutboundOrderDetailInfo(list);
            }
        }
    }
    //endregion
    @Override
    public QueryWrapper<OutboundOrderInfo> getQueryWrapper(OutboundOrderInfoQuery outboundOrderInfoQuery){
        QueryWrapper<OutboundOrderInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = outboundOrderInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = outboundOrderInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String outboundNo = outboundOrderInfoQuery.getOutboundNo();
        queryWrapper.eq(StringUtils.isNotEmpty(outboundNo) ,"outbound_no",outboundNo);

        String outboundType = outboundOrderInfoQuery.getOutboundType();
        queryWrapper.eq(StringUtils.isNotEmpty(outboundType) ,"outbound_type",outboundType);

        Long warehouseId = outboundOrderInfoQuery.getWarehouseId();
        queryWrapper.eq(StringUtils.isNotNull(warehouseId) ,"warehouse_id",warehouseId);

        Date outboundDate = outboundOrderInfoQuery.getOutboundDate();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginOutboundDate"))&&StringUtils.isNotNull(params.get("endOutboundDate")),"outbound_date",params.get("beginOutboundDate"),params.get("endOutboundDate"));

        String outboundStatus = outboundOrderInfoQuery.getOutboundStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(outboundStatus) ,"outbound_status",outboundStatus);

        String reviewStatus = outboundOrderInfoQuery.getReviewStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(reviewStatus) ,"review_status",reviewStatus);

        String createBy = outboundOrderInfoQuery.getCreateBy();
        queryWrapper.like(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Date createTime = outboundOrderInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<OutboundOrderInfoVo> convertVoList(List<OutboundOrderInfo> outboundOrderInfoList) {
        if (StringUtils.isEmpty(outboundOrderInfoList)) {
            return Collections.emptyList();
        }
        return outboundOrderInfoList.stream().map(OutboundOrderInfoVo::objToVo).collect(Collectors.toList());
    }
}
