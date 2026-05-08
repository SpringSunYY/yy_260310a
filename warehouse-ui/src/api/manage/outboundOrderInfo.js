import request from '@/utils/request'

// 查询出库单列表
export function listOutboundOrderInfo(query) {
  return request({
    url: '/manage/outboundOrderInfo/list',
    method: 'get',
    params: query
  })
}

// 查询出库单详细
export function getOutboundOrderInfo(id) {
  return request({
    url: '/manage/outboundOrderInfo/' + id,
    method: 'get'
  })
}

// 新增出库单
export function addOutboundOrderInfo(data) {
  return request({
    url: '/manage/outboundOrderInfo',
    method: 'post',
    data: data
  })
}

// 修改出库单
export function updateOutboundOrderInfo(data) {
  return request({
    url: '/manage/outboundOrderInfo',
    method: 'put',
    data: data
  })
}

// 删除出库单
export function delOutboundOrderInfo(id) {
  return request({
    url: '/manage/outboundOrderInfo/' + id,
    method: 'delete'
  })
}
