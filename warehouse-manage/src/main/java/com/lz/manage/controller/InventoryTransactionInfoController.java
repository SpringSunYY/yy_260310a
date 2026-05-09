package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.lz.common.utils.ThrowUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.enums.BusinessType;
import com.lz.manage.model.domain.InventoryTransactionInfo;
import com.lz.manage.model.vo.inventoryTransactionInfo.InventoryTransactionInfoVo;
import com.lz.manage.model.dto.inventoryTransactionInfo.InventoryTransactionInfoQuery;
import com.lz.manage.model.dto.inventoryTransactionInfo.InventoryTransactionInfoInsert;
import com.lz.manage.model.dto.inventoryTransactionInfo.InventoryTransactionInfoEdit;
import com.lz.manage.service.IInventoryTransactionInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 库存流水Controller
 *
 * @author YY
 * @date 2026-05-08
 */
@RestController
@RequestMapping("/manage/inventoryTransactionInfo")
public class InventoryTransactionInfoController extends BaseController
{
    @Resource
    private IInventoryTransactionInfoService inventoryTransactionInfoService;

    /**
     * 查询库存流水列表
     */
    @PreAuthorize("@ss.hasPermi('manage:inventoryTransactionInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(InventoryTransactionInfoQuery inventoryTransactionInfoQuery)
    {
        InventoryTransactionInfo inventoryTransactionInfo = InventoryTransactionInfoQuery.queryToObj(inventoryTransactionInfoQuery);
        startPage();
        List<InventoryTransactionInfo> list = inventoryTransactionInfoService.selectInventoryTransactionInfoList(inventoryTransactionInfo);
        List<InventoryTransactionInfoVo> listVo= list.stream().map(InventoryTransactionInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出库存流水列表
     */
    @PreAuthorize("@ss.hasPermi('manage:inventoryTransactionInfo:export')")
    @Log(title = "库存流水", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InventoryTransactionInfoQuery inventoryTransactionInfoQuery)
    {
        InventoryTransactionInfo inventoryTransactionInfo = InventoryTransactionInfoQuery.queryToObj(inventoryTransactionInfoQuery);
        List<InventoryTransactionInfo> list = inventoryTransactionInfoService.selectInventoryTransactionInfoList(inventoryTransactionInfo);
        ExcelUtil<InventoryTransactionInfo> util = new ExcelUtil<InventoryTransactionInfo>(InventoryTransactionInfo.class);
        util.exportExcel(response, list, "库存流水数据");
    }

    /**
     * 获取库存流水详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:inventoryTransactionInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        InventoryTransactionInfo inventoryTransactionInfo = inventoryTransactionInfoService.selectInventoryTransactionInfoById(id);
        return success(InventoryTransactionInfoVo.objToVo(inventoryTransactionInfo));
    }

    /**
     * 新增库存流水
     */
    @PreAuthorize("@ss.hasPermi('manage:inventoryTransactionInfo:add')")
    @Log(title = "库存流水", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InventoryTransactionInfoInsert inventoryTransactionInfoInsert)
    {
        ThrowUtils.throwServiceException("禁止添加");
        InventoryTransactionInfo inventoryTransactionInfo = InventoryTransactionInfoInsert.insertToObj(inventoryTransactionInfoInsert);
        return toAjax(inventoryTransactionInfoService.insertInventoryTransactionInfo(inventoryTransactionInfo));
    }

    /**
     * 修改库存流水
     */
    @PreAuthorize("@ss.hasPermi('manage:inventoryTransactionInfo:edit')")
    @Log(title = "库存流水", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InventoryTransactionInfoEdit inventoryTransactionInfoEdit)
    {
        InventoryTransactionInfo inventoryTransactionInfo = InventoryTransactionInfoEdit.editToObj(inventoryTransactionInfoEdit);
        return toAjax(inventoryTransactionInfoService.updateInventoryTransactionInfo(inventoryTransactionInfo));
    }

    /**
     * 删除库存流水
     */
    @PreAuthorize("@ss.hasPermi('manage:inventoryTransactionInfo:remove')")
    @Log(title = "库存流水", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(inventoryTransactionInfoService.deleteInventoryTransactionInfoByIds(ids));
    }
}
