import request from '@/utils/request'

// 查询库存流水列表
export function listInventoryTransactionInfo(query) {
  return request({
    url: '/manage/inventoryTransactionInfo/list',
    method: 'get',
    params: query
  })
}

// 查询库存流水详细
export function getInventoryTransactionInfo(id) {
  return request({
    url: '/manage/inventoryTransactionInfo/' + id,
    method: 'get'
  })
}

// 新增库存流水
export function addInventoryTransactionInfo(data) {
  return request({
    url: '/manage/inventoryTransactionInfo',
    method: 'post',
    data: data
  })
}

// 修改库存流水
export function updateInventoryTransactionInfo(data) {
  return request({
    url: '/manage/inventoryTransactionInfo',
    method: 'put',
    data: data
  })
}

// 删除库存流水
export function delInventoryTransactionInfo(id) {
  return request({
    url: '/manage/inventoryTransactionInfo/' + id,
    method: 'delete'
  })
}
