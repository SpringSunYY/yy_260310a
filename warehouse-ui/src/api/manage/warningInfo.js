import request from '@/utils/request'

// 查询库存预警列表
export function listWarningInfo(query) {
  return request({
    url: '/manage/warningInfo/list',
    method: 'get',
    params: query
  })
}

// 查询库存预警详细
export function getWarningInfo(id) {
  return request({
    url: '/manage/warningInfo/' + id,
    method: 'get'
  })
}

// 新增库存预警
export function addWarningInfo(data) {
  return request({
    url: '/manage/warningInfo',
    method: 'post',
    data: data
  })
}

// 修改库存预警
export function updateWarningInfo(data) {
  return request({
    url: '/manage/warningInfo',
    method: 'put',
    data: data
  })
}

// 删除库存预警
export function delWarningInfo(id) {
  return request({
    url: '/manage/warningInfo/' + id,
    method: 'delete'
  })
}
