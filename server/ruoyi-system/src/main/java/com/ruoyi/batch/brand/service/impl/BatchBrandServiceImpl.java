package com.ruoyi.batch.brand.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.batch.brand.domain.BatchBrand;
import com.ruoyi.batch.brand.mapper.BatchBrandMapper;
import com.ruoyi.batch.brand.service.IBatchBrandService;

/**
 * 品牌专区Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class BatchBrandServiceImpl implements IBatchBrandService
{
    @Autowired
    private BatchBrandMapper batchBrandMapper;

    /**
     * 查询品牌专区
     */
    @Override
    public BatchBrand selectBatchBrandById(Long brandId)
    {
        return batchBrandMapper.selectBatchBrandById(brandId);
    }

    /**
     * 查询品牌专区列表
     */
    @Override
    public List<BatchBrand> selectBatchBrandList(BatchBrand batchBrand)
    {
        return batchBrandMapper.selectBatchBrandList(batchBrand);
    }

    /**
     * 新增品牌专区
     */
    @Override
    public int insertBatchBrand(BatchBrand batchBrand)
    {
        return batchBrandMapper.insertBatchBrand(batchBrand);
    }

    /**
     * 修改品牌专区
     */
    @Override
    public int updateBatchBrand(BatchBrand batchBrand)
    {
        return batchBrandMapper.updateBatchBrand(batchBrand);
    }

    /**
     * 批量删除品牌专区
     */
    @Override
    public int deleteBatchBrandByIds(Long[] brandIds)
    {
        return batchBrandMapper.deleteBatchBrandByIds(brandIds);
    }

    /**
     * 删除品牌专区信息
     */
    @Override
    public int deleteBatchBrandById(Long brandId)
    {
        return batchBrandMapper.deleteBatchBrandById(brandId);
    }
}
