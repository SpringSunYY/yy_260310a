import request from '@/utils/request'

// 查询仓库列表
export function listWarehouseInfo(query) {
  return request({
    url: '/manage/warehouseInfo/list',
    method: 'get',
    params: query
  })
}

// 查询仓库详细
export function getWarehouseInfo(id) {
  return request({
    url: '/manage/warehouseInfo/' + id,
    method: 'get'
  })
}

// 新增仓库
export function addWarehouseInfo(data) {
  return request({
    url: '/manage/warehouseInfo',
    method: 'post',
    data: data
  })
}

// 修改仓库
export function updateWarehouseInfo(data) {
  return request({
    url: '/manage/warehouseInfo',
    method: 'put',
    data: data
  })
}

// 删除仓库
export function delWarehouseInfo(id) {
  return request({
    url: '/manage/warehouseInfo/' + id,
    method: 'delete'
  })
}
