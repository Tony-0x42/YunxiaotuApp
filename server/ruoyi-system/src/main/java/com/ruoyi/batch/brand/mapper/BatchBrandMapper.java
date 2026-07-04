package com.ruoyi.batch.brand.mapper;

import java.util.List;
import com.ruoyi.batch.brand.domain.BatchBrand;

/**
 * 品牌专区Mapper接口
 *
 * @author ruoyi
 */
public interface BatchBrandMapper
{
    /**
     * 查询品牌专区
     *
     * @param brandId 品牌专区主键
     * @return 品牌专区
     */
    public BatchBrand selectBatchBrandById(Long brandId);

    /**
     * 查询品牌专区列表
     *
     * @param batchBrand 品牌专区
     * @return 品牌专区集合
     */
    public List<BatchBrand> selectBatchBrandList(BatchBrand batchBrand);

    /**
     * 新增品牌专区
     *
     * @param batchBrand 品牌专区
     * @return 结果
     */
    public int insertBatchBrand(BatchBrand batchBrand);

    /**
     * 修改品牌专区
     *
     * @param batchBrand 品牌专区
     * @return 结果
     */
    public int updateBatchBrand(BatchBrand batchBrand);

    /**
     * 删除品牌专区
     *
     * @param brandId 品牌专区主键
     * @return 结果
     */
    public int deleteBatchBrandById(Long brandId);

    /**
     * 批量删除品牌专区
     *
     * @param brandIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBatchBrandByIds(Long[] brandIds);
}
