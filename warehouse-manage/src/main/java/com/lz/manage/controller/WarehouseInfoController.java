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
import com.lz.manage.model.domain.WarehouseInfo;
import com.lz.manage.model.vo.warehouseInfo.WarehouseInfoVo;
import com.lz.manage.model.dto.warehouseInfo.WarehouseInfoQuery;
import com.lz.manage.model.dto.warehouseInfo.WarehouseInfoInsert;
import com.lz.manage.model.dto.warehouseInfo.WarehouseInfoEdit;
import com.lz.manage.service.IWarehouseInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 仓库Controller
 *
 * @author YY
 * @date 2026-05-08
 */
@RestController
@RequestMapping("/manage/warehouseInfo")
public class WarehouseInfoController extends BaseController
{
    @Resource
    private IWarehouseInfoService warehouseInfoService;

    /**
     * 查询仓库列表
     */
    @PreAuthorize("@ss.hasPermi('manage:warehouseInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarehouseInfoQuery warehouseInfoQuery)
    {
        WarehouseInfo warehouseInfo = WarehouseInfoQuery.queryToObj(warehouseInfoQuery);
        startPage();
        List<WarehouseInfo> list = warehouseInfoService.selectWarehouseInfoList(warehouseInfo);
        List<WarehouseInfoVo> listVo= list.stream().map(WarehouseInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出仓库列表
     */
    @PreAuthorize("@ss.hasPermi('manage:warehouseInfo:export')")
    @Log(title = "仓库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WarehouseInfoQuery warehouseInfoQuery)
    {
        WarehouseInfo warehouseInfo = WarehouseInfoQuery.queryToObj(warehouseInfoQuery);
        List<WarehouseInfo> list = warehouseInfoService.selectWarehouseInfoList(warehouseInfo);
        ExcelUtil<WarehouseInfo> util = new ExcelUtil<WarehouseInfo>(WarehouseInfo.class);
        util.exportExcel(response, list, "仓库数据");
    }

    /**
     * 获取仓库详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:warehouseInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        WarehouseInfo warehouseInfo = warehouseInfoService.selectWarehouseInfoById(id);
        return success(WarehouseInfoVo.objToVo(warehouseInfo));
    }

    /**
     * 新增仓库
     */
    @PreAuthorize("@ss.hasPermi('manage:warehouseInfo:add')")
    @Log(title = "仓库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WarehouseInfoInsert warehouseInfoInsert)
    {
        WarehouseInfo warehouseInfo = WarehouseInfoInsert.insertToObj(warehouseInfoInsert);
        return toAjax(warehouseInfoService.insertWarehouseInfo(warehouseInfo));
    }

    /**
     * 修改仓库
     */
    @PreAuthorize("@ss.hasPermi('manage:warehouseInfo:edit')")
    @Log(title = "仓库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WarehouseInfoEdit warehouseInfoEdit)
    {
        WarehouseInfo warehouseInfo = WarehouseInfoEdit.editToObj(warehouseInfoEdit);
        return toAjax(warehouseInfoService.updateWarehouseInfo(warehouseInfo));
    }

    /**
     * 删除仓库
     */
    @PreAuthorize("@ss.hasPermi('manage:warehouseInfo:remove')")
    @Log(title = "仓库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(warehouseInfoService.deleteWarehouseInfoByIds(ids));
    }
}
