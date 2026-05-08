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
import com.lz.manage.model.domain.InboundOrderInfo;
import com.lz.manage.model.vo.inboundOrderInfo.InboundOrderInfoVo;
import com.lz.manage.model.dto.inboundOrderInfo.InboundOrderInfoQuery;
import com.lz.manage.model.dto.inboundOrderInfo.InboundOrderInfoInsert;
import com.lz.manage.model.dto.inboundOrderInfo.InboundOrderInfoEdit;
import com.lz.manage.service.IInboundOrderInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 入库单Controller
 *
 * @author YY
 * @date 2026-05-08
 */
@RestController
@RequestMapping("/manage/inboundOrderInfo")
public class InboundOrderInfoController extends BaseController
{
    @Resource
    private IInboundOrderInfoService inboundOrderInfoService;

    /**
     * 查询入库单列表
     */
    @PreAuthorize("@ss.hasPermi('manage:inboundOrderInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(InboundOrderInfoQuery inboundOrderInfoQuery)
    {
        InboundOrderInfo inboundOrderInfo = InboundOrderInfoQuery.queryToObj(inboundOrderInfoQuery);
        startPage();
        List<InboundOrderInfo> list = inboundOrderInfoService.selectInboundOrderInfoList(inboundOrderInfo);
        List<InboundOrderInfoVo> listVo= list.stream().map(InboundOrderInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出入库单列表
     */
    @PreAuthorize("@ss.hasPermi('manage:inboundOrderInfo:export')")
    @Log(title = "入库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InboundOrderInfoQuery inboundOrderInfoQuery)
    {
        InboundOrderInfo inboundOrderInfo = InboundOrderInfoQuery.queryToObj(inboundOrderInfoQuery);
        List<InboundOrderInfo> list = inboundOrderInfoService.selectInboundOrderInfoList(inboundOrderInfo);
        ExcelUtil<InboundOrderInfo> util = new ExcelUtil<InboundOrderInfo>(InboundOrderInfo.class);
        util.exportExcel(response, list, "入库单数据");
    }

    /**
     * 获取入库单详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:inboundOrderInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        InboundOrderInfo inboundOrderInfo = inboundOrderInfoService.selectInboundOrderInfoById(id);
        return success(InboundOrderInfoVo.objToVo(inboundOrderInfo));
    }

    /**
     * 新增入库单
     */
    @PreAuthorize("@ss.hasPermi('manage:inboundOrderInfo:add')")
    @Log(title = "入库单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InboundOrderInfoInsert inboundOrderInfoInsert)
    {
        InboundOrderInfo inboundOrderInfo = InboundOrderInfoInsert.insertToObj(inboundOrderInfoInsert);
        return toAjax(inboundOrderInfoService.insertInboundOrderInfo(inboundOrderInfo));
    }

    /**
     * 修改入库单
     */
    @PreAuthorize("@ss.hasPermi('manage:inboundOrderInfo:edit')")
    @Log(title = "入库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InboundOrderInfoEdit inboundOrderInfoEdit)
    {
        InboundOrderInfo inboundOrderInfo = InboundOrderInfoEdit.editToObj(inboundOrderInfoEdit);
        return toAjax(inboundOrderInfoService.updateInboundOrderInfo(inboundOrderInfo));
    }

    /**
     * 删除入库单
     */
    @PreAuthorize("@ss.hasPermi('manage:inboundOrderInfo:remove')")
    @Log(title = "入库单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(inboundOrderInfoService.deleteInboundOrderInfoByIds(ids));
    }
}
