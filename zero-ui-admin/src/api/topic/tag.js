import request from '@/utils/request'

// 创建标签
export function createTag(data) {
  return request({
    url: '/topic/tag/create',
    method: 'post',
    data: data
  })
}

// 更新标签
export function updateTag(data) {
  return request({
    url: '/topic/tag/update',
    method: 'put',
    data: data
  })
}

// 删除标签
export function deleteTag(id) {
  return request({
    url: '/topic/tag/delete?id=' + id,
    method: 'delete'
  })
}

// 获得标签
export function getTag(id) {
  return request({
    url: '/topic/tag/get?id=' + id,
    method: 'get'
  })
}

// 获得标签分页
export function getTagPage(query) {
  return request({
    url: '/topic/tag/page',
    method: 'get',
    params: query
  })
}

// 导出标签 Excel
export function exportTagExcel(query) {
  return request({
    url: '/topic/tag/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
