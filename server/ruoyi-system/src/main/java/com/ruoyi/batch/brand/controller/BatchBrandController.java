package com.ruoyi.batch.brand.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.batch.brand.domain.BatchBrand;
import com.ruoyi.batch.brand.service.IBatchBrandService;

/**
 * 品牌专区Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/batch/brand")
public class BatchBrandController extends BaseController
{
    @Autowired
    private IBatchBrandService batchBrandService;

    /**
     * 查询品牌专区列表（APP 端匿名可访问）
     */
    @PreAuthorize("isAnonymous() or @ss.hasPermi('batch:brand:list') or @ss.hasPermi('app:user')")
    @GetMapping("/list")
    public AjaxResult list(BatchBrand batchBrand)
    {
        if (batchBrand.getStatus() == null)
        {
            batchBrand.setStatus(0);
        }
        if (batchBrand.getDelFlag() == null)
        {
            batchBrand.setDelFlag(0);
        }
        List<BatchBrand> list = batchBrandService.selectBatchBrandList(batchBrand);
        return success(list);
    }

    /**
     * 根据品牌编号获取详细信息
     */
    @PreAuthorize("isAnonymous() or @ss.hasPermi('batch:brand:query') or @ss.hasPermi('app:user')")
    @GetMapping(value = "/{brandId}")
    public AjaxResult getInfo(@PathVariable Long brandId)
    {
        return success(batchBrandService.selectBatchBrandById(brandId));
    }

    /**
     * 新增品牌专区
     */
    @PreAuthorize("@ss.hasPermi('batch:brand:add')")
    @Log(title = "品牌专区", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BatchBrand batchBrand)
    {
        batchBrand.setCreateBy(getUsername());
        return toAjax(batchBrandService.insertBatchBrand(batchBrand));
    }

    /**
     * 修改品牌专区
     */
    @PreAuthorize("@ss.hasPermi('batch:brand:edit')")
    @Log(title = "品牌专区", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BatchBrand batchBrand)
    {
        batchBrand.setUpdateBy(getUsername());
        return toAjax(batchBrandService.updateBatchBrand(batchBrand));
    }

    /**
     * 修改品牌专区状态
     */
    @PreAuthorize("@ss.hasPermi('batch:brand:edit')")
    @Log(title = "品牌专区", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody BatchBrand batchBrand)
    {
        batchBrand.setUpdateBy(getUsername());
        return toAjax(batchBrandService.updateBatchBrand(batchBrand));
    }

    /**
     * 删除品牌专区
     */
    @PreAuthorize("@ss.hasPermi('batch:brand:remove')")
    @Log(title = "品牌专区", businessType = BusinessType.DELETE)
    @DeleteMapping("/{brandIds}")
    public AjaxResult remove(@PathVariable Long[] brandIds)
    {
        return toAjax(batchBrandService.deleteBatchBrandByIds(brandIds));
    }
}
