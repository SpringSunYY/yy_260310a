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
import com.lz.manage.model.domain.OutboundOrderInfo;
import com.lz.manage.model.vo.outboundOrderInfo.OutboundOrderInfoVo;
import com.lz.manage.model.dto.outboundOrderInfo.OutboundOrderInfoQuery;
import com.lz.manage.model.dto.outboundOrderInfo.OutboundOrderInfoInsert;
import com.lz.manage.model.dto.outboundOrderInfo.OutboundOrderInfoEdit;
import com.lz.manage.service.IOutboundOrderInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 出库单Controller
 *
 * @author YY
 * @date 2026-05-08
 */
@RestController
@RequestMapping("/manage/outboundOrderInfo")
public class OutboundOrderInfoController extends BaseController
{
    @Resource
    private IOutboundOrderInfoService outboundOrderInfoService;

    /**
     * 查询出库单列表
     */
    @PreAuthorize("@ss.hasPermi('manage:outboundOrderInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(OutboundOrderInfoQuery outboundOrderInfoQuery)
    {
        OutboundOrderInfo outboundOrderInfo = OutboundOrderInfoQuery.queryToObj(outboundOrderInfoQuery);
        startPage();
        List<OutboundOrderInfo> list = outboundOrderInfoService.selectOutboundOrderInfoList(outboundOrderInfo);
        List<OutboundOrderInfoVo> listVo= list.stream().map(OutboundOrderInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出出库单列表
     */
    @PreAuthorize("@ss.hasPermi('manage:outboundOrderInfo:export')")
    @Log(title = "出库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OutboundOrderInfoQuery outboundOrderInfoQuery)
    {
        OutboundOrderInfo outboundOrderInfo = OutboundOrderInfoQuery.queryToObj(outboundOrderInfoQuery);
        List<OutboundOrderInfo> list = outboundOrderInfoService.selectOutboundOrderInfoList(outboundOrderInfo);
        ExcelUtil<OutboundOrderInfo> util = new ExcelUtil<OutboundOrderInfo>(OutboundOrderInfo.class);
        util.exportExcel(response, list, "出库单数据");
    }

    /**
     * 获取出库单详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:outboundOrderInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        OutboundOrderInfo outboundOrderInfo = outboundOrderInfoService.selectOutboundOrderInfoById(id);
        return success(OutboundOrderInfoVo.objToVo(outboundOrderInfo));
    }

    /**
     * 新增出库单
     */
    @PreAuthorize("@ss.hasPermi('manage:outboundOrderInfo:add')")
    @Log(title = "出库单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OutboundOrderInfoInsert outboundOrderInfoInsert)
    {
        OutboundOrderInfo outboundOrderInfo = OutboundOrderInfoInsert.insertToObj(outboundOrderInfoInsert);
        return toAjax(outboundOrderInfoService.insertOutboundOrderInfo(outboundOrderInfo));
    }

    /**
     * 修改出库单
     */
    @PreAuthorize("@ss.hasPermi('manage:outboundOrderInfo:edit')")
    @Log(title = "出库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OutboundOrderInfoEdit outboundOrderInfoEdit)
    {
        OutboundOrderInfo outboundOrderInfo = OutboundOrderInfoEdit.editToObj(outboundOrderInfoEdit);
        return toAjax(outboundOrderInfoService.updateOutboundOrderInfo(outboundOrderInfo));
    }

    /**
     * 审核出库单
     */
    @PreAuthorize("@ss.hasPermi('manage:outboundOrderInfo:audit')")
    @Log(title = "出库单", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody OutboundOrderInfo outboundOrderInfo)
    {
        return toAjax(outboundOrderInfoService.auditOutboundOrderInfo(outboundOrderInfo));
    }

    /**
     * 删除出库单
     */
    @PreAuthorize("@ss.hasPermi('manage:outboundOrderInfo:remove')")
    @Log(title = "出库单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(outboundOrderInfoService.deleteOutboundOrderInfoByIds(ids));
    }
}
