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
import com.lz.manage.model.domain.WarningInfo;
import com.lz.manage.model.vo.warningInfo.WarningInfoVo;
import com.lz.manage.model.dto.warningInfo.WarningInfoQuery;
import com.lz.manage.model.dto.warningInfo.WarningInfoInsert;
import com.lz.manage.model.dto.warningInfo.WarningInfoEdit;
import com.lz.manage.service.IWarningInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 库存预警Controller
 *
 * @author YY
 * @date 2026-05-08
 */
@RestController
@RequestMapping("/manage/warningInfo")
public class WarningInfoController extends BaseController
{
    @Resource
    private IWarningInfoService warningInfoService;

    /**
     * 查询库存预警列表
     */
    @PreAuthorize("@ss.hasPermi('manage:warningInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarningInfoQuery warningInfoQuery)
    {
        WarningInfo warningInfo = WarningInfoQuery.queryToObj(warningInfoQuery);
        startPage();
        List<WarningInfo> list = warningInfoService.selectWarningInfoList(warningInfo);
        List<WarningInfoVo> listVo= list.stream().map(WarningInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出库存预警列表
     */
    @PreAuthorize("@ss.hasPermi('manage:warningInfo:export')")
    @Log(title = "库存预警", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WarningInfoQuery warningInfoQuery)
    {
        WarningInfo warningInfo = WarningInfoQuery.queryToObj(warningInfoQuery);
        List<WarningInfo> list = warningInfoService.selectWarningInfoList(warningInfo);
        ExcelUtil<WarningInfo> util = new ExcelUtil<WarningInfo>(WarningInfo.class);
        util.exportExcel(response, list, "库存预警数据");
    }

    /**
     * 获取库存预警详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:warningInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        WarningInfo warningInfo = warningInfoService.selectWarningInfoById(id);
        return success(WarningInfoVo.objToVo(warningInfo));
    }

    /**
     * 新增库存预警
     */
    @PreAuthorize("@ss.hasPermi('manage:warningInfo:add')")
    @Log(title = "库存预警", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WarningInfoInsert warningInfoInsert)
    {
        WarningInfo warningInfo = WarningInfoInsert.insertToObj(warningInfoInsert);
        return toAjax(warningInfoService.insertWarningInfo(warningInfo));
    }

    /**
     * 修改库存预警
     */
    @PreAuthorize("@ss.hasPermi('manage:warningInfo:edit')")
    @Log(title = "库存预警", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WarningInfoEdit warningInfoEdit)
    {
        WarningInfo warningInfo = WarningInfoEdit.editToObj(warningInfoEdit);
        return toAjax(warningInfoService.updateWarningInfo(warningInfo));
    }

    /**
     * 删除库存预警
     */
    @PreAuthorize("@ss.hasPermi('manage:warningInfo:remove')")
    @Log(title = "库存预警", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(warningInfoService.deleteWarningInfoByIds(ids));
    }
}
