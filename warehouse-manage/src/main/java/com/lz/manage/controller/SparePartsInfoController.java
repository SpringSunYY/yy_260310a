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
import com.lz.manage.model.domain.SparePartsInfo;
import com.lz.manage.model.vo.sparePartsInfo.SparePartsInfoVo;
import com.lz.manage.model.dto.sparePartsInfo.SparePartsInfoQuery;
import com.lz.manage.model.dto.sparePartsInfo.SparePartsInfoInsert;
import com.lz.manage.model.dto.sparePartsInfo.SparePartsInfoEdit;
import com.lz.manage.service.ISparePartsInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 备品备件信息Controller
 *
 * @author YY
 * @date 2026-05-08
 */
@RestController
@RequestMapping("/manage/sparePartsInfo")
public class SparePartsInfoController extends BaseController
{
    @Resource
    private ISparePartsInfoService sparePartsInfoService;

    /**
     * 查询备品备件信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:sparePartsInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SparePartsInfoQuery sparePartsInfoQuery)
    {
        SparePartsInfo sparePartsInfo = SparePartsInfoQuery.queryToObj(sparePartsInfoQuery);
        startPage();
        List<SparePartsInfo> list = sparePartsInfoService.selectSparePartsInfoList(sparePartsInfo);
        List<SparePartsInfoVo> listVo= list.stream().map(SparePartsInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出备品备件信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:sparePartsInfo:export')")
    @Log(title = "备品备件信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SparePartsInfoQuery sparePartsInfoQuery)
    {
        SparePartsInfo sparePartsInfo = SparePartsInfoQuery.queryToObj(sparePartsInfoQuery);
        List<SparePartsInfo> list = sparePartsInfoService.selectSparePartsInfoList(sparePartsInfo);
        ExcelUtil<SparePartsInfo> util = new ExcelUtil<SparePartsInfo>(SparePartsInfo.class);
        util.exportExcel(response, list, "备品备件信息数据");
    }

    /**
     * 获取备品备件信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:sparePartsInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        SparePartsInfo sparePartsInfo = sparePartsInfoService.selectSparePartsInfoById(id);
        return success(SparePartsInfoVo.objToVo(sparePartsInfo));
    }

    /**
     * 新增备品备件信息
     */
    @PreAuthorize("@ss.hasPermi('manage:sparePartsInfo:add')")
    @Log(title = "备品备件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SparePartsInfoInsert sparePartsInfoInsert)
    {
        SparePartsInfo sparePartsInfo = SparePartsInfoInsert.insertToObj(sparePartsInfoInsert);
        return toAjax(sparePartsInfoService.insertSparePartsInfo(sparePartsInfo));
    }

    /**
     * 修改备品备件信息
     */
    @PreAuthorize("@ss.hasPermi('manage:sparePartsInfo:edit')")
    @Log(title = "备品备件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SparePartsInfoEdit sparePartsInfoEdit)
    {
        SparePartsInfo sparePartsInfo = SparePartsInfoEdit.editToObj(sparePartsInfoEdit);
        return toAjax(sparePartsInfoService.updateSparePartsInfo(sparePartsInfo));
    }

    /**
     * 删除备品备件信息
     */
    @PreAuthorize("@ss.hasPermi('manage:sparePartsInfo:remove')")
    @Log(title = "备品备件信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sparePartsInfoService.deleteSparePartsInfoByIds(ids));
    }
}
