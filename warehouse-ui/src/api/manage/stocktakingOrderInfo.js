import request from '@/utils/request'

// 查询盘点单列表
export function listStocktakingOrderInfo(query) {
  return request({
    url: '/manage/stocktakingOrderInfo/list',
    method: 'get',
    params: query
  })
}

// 查询盘点单详细
export function getStocktakingOrderInfo(id) {
  return request({
    url: '/manage/stocktakingOrderInfo/' + id,
    method: 'get'
  })
}

// 新增盘点单
export function addStocktakingOrderInfo(data) {
  return request({
    url: '/manage/stocktakingOrderInfo',
    method: 'post',
    data: data
  })
}

// 修改盘点单
export function updateStocktakingOrderInfo(data) {
  return request({
    url: '/manage/stocktakingOrderInfo',
    method: 'put',
    data: data
  })
}

// 删除盘点单
export function delStocktakingOrderInfo(id) {
  return request({
    url: '/manage/stocktakingOrderInfo/' + id,
    method: 'delete'
  })
}
