package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
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
import com.lz.manage.model.domain.InventoryRecordInfo;
import com.lz.manage.model.vo.inventoryRecordInfo.InventoryRecordInfoVo;
import com.lz.manage.model.dto.inventoryRecordInfo.InventoryRecordInfoQuery;
import com.lz.manage.model.dto.inventoryRecordInfo.InventoryRecordInfoInsert;
import com.lz.manage.model.dto.inventoryRecordInfo.InventoryRecordInfoEdit;
import com.lz.manage.service.IInventoryRecordInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 库存记录Controller
 *
 * @author YY
 * @date 2026-05-08
 */
@RestController
@RequestMapping("/manage/inventoryRecordInfo")
public class InventoryRecordInfoController extends BaseController
{
    @Resource
    private IInventoryRecordInfoService inventoryRecordInfoService;

    /**
     * 查询库存记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:inventoryRecordInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(InventoryRecordInfoQuery inventoryRecordInfoQuery)
    {
        InventoryRecordInfo inventoryRecordInfo = InventoryRecordInfoQuery.queryToObj(inventoryRecordInfoQuery);
        startPage();
        List<InventoryRecordInfo> list = inventoryRecordInfoService.selectInventoryRecordInfoList(inventoryRecordInfo);
        List<InventoryRecordInfoVo> listVo= list.stream().map(InventoryRecordInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出库存记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:inventoryRecordInfo:export')")
    @Log(title = "库存记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InventoryRecordInfoQuery inventoryRecordInfoQuery)
    {
        InventoryRecordInfo inventoryRecordInfo = InventoryRecordInfoQuery.queryToObj(inventoryRecordInfoQuery);
        List<InventoryRecordInfo> list = inventoryRecordInfoService.selectInventoryRecordInfoList(inventoryRecordInfo);
        ExcelUtil<InventoryRecordInfo> util = new ExcelUtil<InventoryRecordInfo>(InventoryRecordInfo.class);
        util.exportExcel(response, list, "库存记录数据");
    }

    /**
     * 获取库存记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:inventoryRecordInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        InventoryRecordInfo inventoryRecordInfo = inventoryRecordInfoService.selectInventoryRecordInfoById(id);
        return success(InventoryRecordInfoVo.objToVo(inventoryRecordInfo));
    }

    /**
     * 新增库存记录
     */
    @PreAuthorize("@ss.hasPermi('manage:inventoryRecordInfo:add')")
    @Log(title = "库存记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InventoryRecordInfoInsert inventoryRecordInfoInsert)
    {
        InventoryRecordInfo inventoryRecordInfo = InventoryRecordInfoInsert.insertToObj(inventoryRecordInfoInsert);
        return toAjax(inventoryRecordInfoService.insertInventoryRecordInfo(inventoryRecordInfo));
    }

    /**
     * 修改库存记录
     */
    @PreAuthorize("@ss.hasPermi('manage:inventoryRecordInfo:edit')")
    @Log(title = "库存记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InventoryRecordInfoEdit inventoryRecordInfoEdit)
    {
        InventoryRecordInfo inventoryRecordInfo = InventoryRecordInfoEdit.editToObj(inventoryRecordInfoEdit);
        return toAjax(inventoryRecordInfoService.updateInventoryRecordInfo(inventoryRecordInfo));
    }

    /**
     * 删除库存记录
     */
    @PreAuthorize("@ss.hasPermi('manage:inventoryRecordInfo:remove')")
    @Log(title = "库存记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(inventoryRecordInfoService.deleteInventoryRecordInfoByIds(ids));
    }
}
