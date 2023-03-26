import request from '@/utils/request'

// 创建问答题
export function createInterlocution(data) {
  return request({
    url: '/topic/interlocution/create',
    method: 'post',
    data: data
  })
}

// 更新问答题
export function updateInterlocution(data) {
  return request({
    url: '/topic/interlocution/update',
    method: 'put',
    data: data
  })
}

// 删除问答题
export function deleteInterlocution(id) {
  return request({
    url: '/topic/interlocution/delete?id=' + id,
    method: 'delete'
  })
}

// 获得问答题
export function getInterlocution(id) {
  return request({
    url: '/topic/interlocution/get?id=' + id,
    method: 'get'
  })
}

// 获得问答题分页
export function getInterlocutionPage(query) {
  return request({
    url: '/topic/interlocution/page',
    method: 'get',
    params: query
  })
}

// 导出问答题 Excel
export function exportInterlocutionExcel(query) {
  return request({
    url: '/topic/interlocution/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
