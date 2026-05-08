import request from '@/utils/request'

// 查询采购订单列表
export function listPurchaseOrderInfo(query) {
    return request({
        url: '/manage/purchaseOrderInfo/list',
        method: 'get',
        params: query
    })
}

// 查询采购订单详细
export function getPurchaseOrderInfo(orderId) {
    return request({
        url: '/manage/purchaseOrderInfo/' + orderId,
        method: 'get'
    })
}

// 新增采购订单
export function addPurchaseOrderInfo(data) {
    return request({
        url: '/manage/purchaseOrderInfo',
        method: 'post',
        data: data
    })
}

// 修改采购订单
export function updatePurchaseOrderInfo(data) {
    return request({
        url: '/manage/purchaseOrderInfo',
        method: 'put',
        data: data
    })
}

export function auditPurchaseOrderInfo(data) {
    return request({
        url: '/manage/purchaseOrderInfo/audit',
        method: 'put',
        data: data
    })
}

// 删除采购订单
export function delPurchaseOrderInfo(orderId) {
    return request({
        url: '/manage/purchaseOrderInfo/' + orderId,
        method: 'delete'
    })
}
