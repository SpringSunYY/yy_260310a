import request from '@/utils/request'

// 查询备品备件信息列表
export function listSparePartsInfo(query) {
  return request({
    url: '/manage/sparePartsInfo/list',
    method: 'get',
    params: query
  })
}

// 查询备品备件信息详细
export function getSparePartsInfo(id) {
  return request({
    url: '/manage/sparePartsInfo/' + id,
    method: 'get'
  })
}

// 新增备品备件信息
export function addSparePartsInfo(data) {
  return request({
    url: '/manage/sparePartsInfo',
    method: 'post',
    data: data
  })
}

// 修改备品备件信息
export function updateSparePartsInfo(data) {
  return request({
    url: '/manage/sparePartsInfo',
    method: 'put',
    data: data
  })
}

// 删除备品备件信息
export function delSparePartsInfo(id) {
  return request({
    url: '/manage/sparePartsInfo/' + id,
    method: 'delete'
  })
}
