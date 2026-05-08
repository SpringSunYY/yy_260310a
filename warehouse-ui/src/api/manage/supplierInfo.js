import request from '@/utils/request'

// 查询供应商信息列表
export function listSupplierInfo(query) {
  return request({
    url: '/manage/supplierInfo/list',
    method: 'get',
    params: query
  })
}

// 查询供应商信息详细
export function getSupplierInfo(id) {
  return request({
    url: '/manage/supplierInfo/' + id,
    method: 'get'
  })
}

// 新增供应商信息
export function addSupplierInfo(data) {
  return request({
    url: '/manage/supplierInfo',
    method: 'post',
    data: data
  })
}

// 修改供应商信息
export function updateSupplierInfo(data) {
  return request({
    url: '/manage/supplierInfo',
    method: 'put',
    data: data
  })
}

// 删除供应商信息
export function delSupplierInfo(id) {
  return request({
    url: '/manage/supplierInfo/' + id,
    method: 'delete'
  })
}
