import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi"

// 查询品牌专区列表
export function listBrand(query) {
  return request({
    url: '/batch/brand/list',
    method: 'get',
    params: query
  })
}

// 查询品牌专区详细
export function getBrand(brandId) {
  return request({
    url: '/batch/brand/' + parseStrEmpty(brandId),
    method: 'get'
  })
}

// 新增品牌专区
export function addBrand(data) {
  return request({
    url: '/batch/brand',
    method: 'post',
    data: data
  })
}

// 修改品牌专区
export function updateBrand(data) {
  return request({
    url: '/batch/brand',
    method: 'put',
    data: data
  })
}

// 修改品牌专区状态
export function changeBrandStatus(data) {
  return request({
    url: '/batch/brand/changeStatus',
    method: 'put',
    data: data
  })
}

// 删除品牌专区
export function delBrand(brandIds) {
  return request({
    url: '/batch/brand/' + brandIds,
    method: 'delete'
  })
}
