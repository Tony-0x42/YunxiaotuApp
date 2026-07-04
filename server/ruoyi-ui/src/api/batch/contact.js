import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi"

// 查询联系方式列表
export function listContact(query) {
  return request({
    url: '/batch/contact/list',
    method: 'get',
    params: query
  })
}

// 查询联系方式详细
export function getContact(contactId) {
  return request({
    url: '/batch/contact/' + parseStrEmpty(contactId),
    method: 'get'
  })
}

// 新增联系方式
export function addContact(data) {
  return request({
    url: '/batch/contact',
    method: 'post',
    data: data
  })
}

// 修改联系方式
export function updateContact(data) {
  return request({
    url: '/batch/contact',
    method: 'put',
    data: data
  })
}

// 修改联系方式状态
export function changeContactStatus(data) {
  return request({
    url: '/batch/contact/changeStatus',
    method: 'put',
    data: data
  })
}

// 删除联系方式
export function delContact(contactIds) {
  return request({
    url: '/batch/contact/' + contactIds,
    method: 'delete'
  })
}
