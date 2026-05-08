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
import com.lz.manage.model.domain.SupplierInfo;
import com.lz.manage.model.vo.supplierInfo.SupplierInfoVo;
import com.lz.manage.model.dto.supplierInfo.SupplierInfoQuery;
import com.lz.manage.model.dto.supplierInfo.SupplierInfoInsert;
import com.lz.manage.model.dto.supplierInfo.SupplierInfoEdit;
import com.lz.manage.service.ISupplierInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 供应商信息Controller
 *
 * @author YY
 * @date 2026-05-08
 */
@RestController
@RequestMapping("/manage/supplierInfo")
public class SupplierInfoController extends BaseController
{
    @Resource
    private ISupplierInfoService supplierInfoService;

    /**
     * 查询供应商信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:supplierInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierInfoQuery supplierInfoQuery)
    {
        SupplierInfo supplierInfo = SupplierInfoQuery.queryToObj(supplierInfoQuery);
        startPage();
        List<SupplierInfo> list = supplierInfoService.selectSupplierInfoList(supplierInfo);
        List<SupplierInfoVo> listVo= list.stream().map(SupplierInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出供应商信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:supplierInfo:export')")
    @Log(title = "供应商信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierInfoQuery supplierInfoQuery)
    {
        SupplierInfo supplierInfo = SupplierInfoQuery.queryToObj(supplierInfoQuery);
        List<SupplierInfo> list = supplierInfoService.selectSupplierInfoList(supplierInfo);
        ExcelUtil<SupplierInfo> util = new ExcelUtil<SupplierInfo>(SupplierInfo.class);
        util.exportExcel(response, list, "供应商信息数据");
    }

    /**
     * 获取供应商信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:supplierInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        SupplierInfo supplierInfo = supplierInfoService.selectSupplierInfoById(id);
        return success(SupplierInfoVo.objToVo(supplierInfo));
    }

    /**
     * 新增供应商信息
     */
    @PreAuthorize("@ss.hasPermi('manage:supplierInfo:add')")
    @Log(title = "供应商信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierInfoInsert supplierInfoInsert)
    {
        SupplierInfo supplierInfo = SupplierInfoInsert.insertToObj(supplierInfoInsert);
        return toAjax(supplierInfoService.insertSupplierInfo(supplierInfo));
    }

    /**
     * 修改供应商信息
     */
    @PreAuthorize("@ss.hasPermi('manage:supplierInfo:edit')")
    @Log(title = "供应商信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierInfoEdit supplierInfoEdit)
    {
        SupplierInfo supplierInfo = SupplierInfoEdit.editToObj(supplierInfoEdit);
        return toAjax(supplierInfoService.updateSupplierInfo(supplierInfo));
    }

    /**
     * 删除供应商信息
     */
    @PreAuthorize("@ss.hasPermi('manage:supplierInfo:remove')")
    @Log(title = "供应商信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(supplierInfoService.deleteSupplierInfoByIds(ids));
    }
}
