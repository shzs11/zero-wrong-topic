import request from '@/utils/request'

// 创建选择题
export function createSelection(data) {
  return request({
    url: '/topic/selection/create',
    method: 'post',
    data: data
  })
}

// 更新选择题
export function updateSelection(data) {
  return request({
    url: '/topic/selection/update',
    method: 'put',
    data: data
  })
}

// 删除选择题
export function deleteSelection(id) {
  return request({
    url: '/topic/selection/delete?id=' + id,
    method: 'delete'
  })
}

// 获得选择题
export function getSelection(id) {
  return request({
    url: '/topic/selection/get?id=' + id,
    method: 'get'
  })
}

// 获得选择题分页
export function getSelectionPage(query) {
  return request({
    url: '/topic/selection/page',
    method: 'get',
    params: query
  })
}

// 导出选择题 Excel
export function exportSelectionExcel(query) {
  return request({
    url: '/topic/selection/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
