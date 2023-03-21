import request from '@/utils/request'

// 创建科目
export function createSubject(data) {
  return request({
    url: '/topic/subject/create',
    method: 'post',
    data: data
  })
}

// 更新科目
export function updateSubject(data) {
  return request({
    url: '/topic/subject/update',
    method: 'put',
    data: data
  })
}

// 删除科目
export function deleteSubject(id) {
  return request({
    url: '/topic/subject/delete?id=' + id,
    method: 'delete'
  })
}

// 获得科目
export function getSubject(id) {
  return request({
    url: '/topic/subject/get?id=' + id,
    method: 'get'
  })
}

// 获得科目分页
export function getSubjectPage(query) {
  return request({
    url: '/topic/subject/page',
    method: 'get',
    params: query
  })
}

// 获得知识点
export function getSubjectAndKnowledge() {
  return request({
    url: '/topic/subject/list/knowledge',
    method: 'get',
  })
}


// 导出科目 Excel
export function exportSubjectExcel(query) {
  return request({
    url: '/topic/subject/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
