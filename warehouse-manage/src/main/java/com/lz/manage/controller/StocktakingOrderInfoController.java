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
import com.lz.manage.model.domain.StocktakingOrderInfo;
import com.lz.manage.model.vo.stocktakingOrderInfo.StocktakingOrderInfoVo;
import com.lz.manage.model.dto.stocktakingOrderInfo.StocktakingOrderInfoQuery;
import com.lz.manage.model.dto.stocktakingOrderInfo.StocktakingOrderInfoInsert;
import com.lz.manage.model.dto.stocktakingOrderInfo.StocktakingOrderInfoEdit;
import com.lz.manage.service.IStocktakingOrderInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 盘点单Controller
 *
 * @author YY
 * @date 2026-05-08
 */
@RestController
@RequestMapping("/manage/stocktakingOrderInfo")
public class StocktakingOrderInfoController extends BaseController
{
    @Resource
    private IStocktakingOrderInfoService stocktakingOrderInfoService;

    /**
     * 查询盘点单列表
     */
    @PreAuthorize("@ss.hasPermi('manage:stocktakingOrderInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(StocktakingOrderInfoQuery stocktakingOrderInfoQuery)
    {
        StocktakingOrderInfo stocktakingOrderInfo = StocktakingOrderInfoQuery.queryToObj(stocktakingOrderInfoQuery);
        startPage();
        List<StocktakingOrderInfo> list = stocktakingOrderInfoService.selectStocktakingOrderInfoList(stocktakingOrderInfo);
        List<StocktakingOrderInfoVo> listVo= list.stream().map(StocktakingOrderInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出盘点单列表
     */
    @PreAuthorize("@ss.hasPermi('manage:stocktakingOrderInfo:export')")
    @Log(title = "盘点单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StocktakingOrderInfoQuery stocktakingOrderInfoQuery)
    {
        StocktakingOrderInfo stocktakingOrderInfo = StocktakingOrderInfoQuery.queryToObj(stocktakingOrderInfoQuery);
        List<StocktakingOrderInfo> list = stocktakingOrderInfoService.selectStocktakingOrderInfoList(stocktakingOrderInfo);
        ExcelUtil<StocktakingOrderInfo> util = new ExcelUtil<StocktakingOrderInfo>(StocktakingOrderInfo.class);
        util.exportExcel(response, list, "盘点单数据");
    }

    /**
     * 获取盘点单详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:stocktakingOrderInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        StocktakingOrderInfo stocktakingOrderInfo = stocktakingOrderInfoService.selectStocktakingOrderInfoById(id);
        return success(StocktakingOrderInfoVo.objToVo(stocktakingOrderInfo));
    }

    /**
     * 新增盘点单
     */
    @PreAuthorize("@ss.hasPermi('manage:stocktakingOrderInfo:add')")
    @Log(title = "盘点单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StocktakingOrderInfoInsert stocktakingOrderInfoInsert)
    {
        StocktakingOrderInfo stocktakingOrderInfo = StocktakingOrderInfoInsert.insertToObj(stocktakingOrderInfoInsert);
        return toAjax(stocktakingOrderInfoService.insertStocktakingOrderInfo(stocktakingOrderInfo));
    }

    /**
     * 修改盘点单
     */
    @PreAuthorize("@ss.hasPermi('manage:stocktakingOrderInfo:edit')")
    @Log(title = "盘点单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StocktakingOrderInfoEdit stocktakingOrderInfoEdit)
    {
        StocktakingOrderInfo stocktakingOrderInfo = StocktakingOrderInfoEdit.editToObj(stocktakingOrderInfoEdit);
        return toAjax(stocktakingOrderInfoService.updateStocktakingOrderInfo(stocktakingOrderInfo));
    }

    /**
     * 删除盘点单
     */
    @PreAuthorize("@ss.hasPermi('manage:stocktakingOrderInfo:remove')")
    @Log(title = "盘点单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(stocktakingOrderInfoService.deleteStocktakingOrderInfoByIds(ids));
    }

    /**
     * 审核盘点单
     */
    @PreAuthorize("@ss.hasPermi('manage:stocktakingOrderInfo:audit')")
    @Log(title = "盘点单", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody StocktakingOrderInfo stocktakingOrderInfo)
    {
        return toAjax(stocktakingOrderInfoService.auditStocktakingOrderInfo(stocktakingOrderInfo));
    }
}
