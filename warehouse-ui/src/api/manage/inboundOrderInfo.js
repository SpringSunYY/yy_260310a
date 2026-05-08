import request from '@/utils/request'

// 查询入库单列表
export function listInboundOrderInfo(query) {
  return request({
    url: '/manage/inboundOrderInfo/list',
    method: 'get',
    params: query
  })
}

// 查询入库单详细
export function getInboundOrderInfo(id) {
  return request({
    url: '/manage/inboundOrderInfo/' + id,
    method: 'get'
  })
}

// 新增入库单
export function addInboundOrderInfo(data) {
  return request({
    url: '/manage/inboundOrderInfo',
    method: 'post',
    data: data
  })
}

// 修改入库单
export function updateInboundOrderInfo(data) {
  return request({
    url: '/manage/inboundOrderInfo',
    method: 'put',
    data: data
  })
}

// 删除入库单
export function delInboundOrderInfo(id) {
  return request({
    url: '/manage/inboundOrderInfo/' + id,
    method: 'delete'
  })
}
