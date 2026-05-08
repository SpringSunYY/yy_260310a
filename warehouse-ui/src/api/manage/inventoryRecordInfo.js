import request from '@/utils/request'

// 查询库存记录列表
export function listInventoryRecordInfo(query) {
  return request({
    url: '/manage/inventoryRecordInfo/list',
    method: 'get',
    params: query
  })
}

// 查询库存记录详细
export function getInventoryRecordInfo(id) {
  return request({
    url: '/manage/inventoryRecordInfo/' + id,
    method: 'get'
  })
}

// 新增库存记录
export function addInventoryRecordInfo(data) {
  return request({
    url: '/manage/inventoryRecordInfo',
    method: 'post',
    data: data
  })
}

// 修改库存记录
export function updateInventoryRecordInfo(data) {
  return request({
    url: '/manage/inventoryRecordInfo',
    method: 'put',
    data: data
  })
}

// 删除库存记录
export function delInventoryRecordInfo(id) {
  return request({
    url: '/manage/inventoryRecordInfo/' + id,
    method: 'delete'
  })
}
