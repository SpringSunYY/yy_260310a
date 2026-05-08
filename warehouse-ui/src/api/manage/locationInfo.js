import request from '@/utils/request'

// 查询库位列表
export function listLocationInfo(query) {
  return request({
    url: '/manage/locationInfo/list',
    method: 'get',
    params: query
  })
}

// 查询库位详细
export function getLocationInfo(id) {
  return request({
    url: '/manage/locationInfo/' + id,
    method: 'get'
  })
}

// 新增库位
export function addLocationInfo(data) {
  return request({
    url: '/manage/locationInfo',
    method: 'post',
    data: data
  })
}

// 修改库位
export function updateLocationInfo(data) {
  return request({
    url: '/manage/locationInfo',
    method: 'put',
    data: data
  })
}

// 删除库位
export function delLocationInfo(id) {
  return request({
    url: '/manage/locationInfo/' + id,
    method: 'delete'
  })
}
