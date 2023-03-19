import request from '@/utils/request'

// 创建知识点
export function createKnowledge(data) {
  return request({
    url: '/topic/knowledge/create',
    method: 'post',
    data: data
  })
}

// 更新知识点
export function updateKnowledge(data) {
  return request({
    url: '/topic/knowledge/update',
    method: 'put',
    data: data
  })
}

// 删除知识点
export function deleteKnowledge(id) {
  return request({
    url: '/topic/knowledge/delete?id=' + id,
    method: 'delete'
  })
}

// 获得知识点
export function getKnowledge(id) {
  return request({
    url: '/topic/knowledge/get?id=' + id,
    method: 'get'
  })
}

// 获得知识点分页
export function getKnowledgePage(query) {
  return request({
    url: '/topic/knowledge/page',
    method: 'get',
    params: query
  })
}

// 导出知识点 Excel
export function exportKnowledgeExcel(query) {
  return request({
    url: '/topic/knowledge/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
