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
import com.lz.manage.model.domain.LocationInfo;
import com.lz.manage.model.vo.locationInfo.LocationInfoVo;
import com.lz.manage.model.dto.locationInfo.LocationInfoQuery;
import com.lz.manage.model.dto.locationInfo.LocationInfoInsert;
import com.lz.manage.model.dto.locationInfo.LocationInfoEdit;
import com.lz.manage.service.ILocationInfoService;
import com.lz.common.utils.poi.ExcelUtil;

/**
 * 库位Controller
 *
 * @author YY
 * @date 2026-05-08
 */
@RestController
@RequestMapping("/manage/locationInfo")
public class LocationInfoController extends BaseController
{
    @Resource
    private ILocationInfoService locationInfoService;

    /**
     * 查询库位列表
     */
    @PreAuthorize("@ss.hasPermi('manage:locationInfo:list')")
    @GetMapping("/list")
    public AjaxResult list(LocationInfoQuery locationInfoQuery)
    {
        LocationInfo locationInfo = LocationInfoQuery.queryToObj(locationInfoQuery);
        List<LocationInfo> list = locationInfoService.selectLocationInfoList(locationInfo);
        List<LocationInfoVo> listVo= list.stream().map(LocationInfoVo::objToVo).collect(Collectors.toList());
        return success(listVo);
    }

    /**
     * 导出库位列表
     */
    @PreAuthorize("@ss.hasPermi('manage:locationInfo:export')")
    @Log(title = "库位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LocationInfoQuery locationInfoQuery)
    {
        LocationInfo locationInfo = LocationInfoQuery.queryToObj(locationInfoQuery);
        List<LocationInfo> list = locationInfoService.selectLocationInfoList(locationInfo);
        ExcelUtil<LocationInfo> util = new ExcelUtil<LocationInfo>(LocationInfo.class);
        util.exportExcel(response, list, "库位数据");
    }

    /**
     * 获取库位详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:locationInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        LocationInfo locationInfo = locationInfoService.selectLocationInfoById(id);
        return success(LocationInfoVo.objToVo(locationInfo));
    }

    /**
     * 新增库位
     */
    @PreAuthorize("@ss.hasPermi('manage:locationInfo:add')")
    @Log(title = "库位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LocationInfoInsert locationInfoInsert)
    {
        LocationInfo locationInfo = LocationInfoInsert.insertToObj(locationInfoInsert);
        return toAjax(locationInfoService.insertLocationInfo(locationInfo));
    }

    /**
     * 修改库位
     */
    @PreAuthorize("@ss.hasPermi('manage:locationInfo:edit')")
    @Log(title = "库位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LocationInfoEdit locationInfoEdit)
    {
        LocationInfo locationInfo = LocationInfoEdit.editToObj(locationInfoEdit);
        return toAjax(locationInfoService.updateLocationInfo(locationInfo));
    }

    /**
     * 删除库位
     */
    @PreAuthorize("@ss.hasPermi('manage:locationInfo:remove')")
    @Log(title = "库位", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(locationInfoService.deleteLocationInfoByIds(ids));
    }
}
