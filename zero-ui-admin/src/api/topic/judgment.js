import request from '@/utils/request'

// 创建判断题
export function createJudgment(data) {
  return request({
    url: '/topic/judgment/create',
    method: 'post',
    data: data
  })
}

// 更新判断题
export function updateJudgment(data) {
  return request({
    url: '/topic/judgment/update',
    method: 'put',
    data: data
  })
}

// 删除判断题
export function deleteJudgment(id) {
  return request({
    url: '/topic/judgment/delete?id=' + id,
    method: 'delete'
  })
}

// 获得判断题
export function getJudgment(id) {
  return request({
    url: '/topic/judgment/get?id=' + id,
    method: 'get'
  })
}

// 获得判断题分页
export function getJudgmentPage(query) {
  return request({
    url: '/topic/judgment/page',
    method: 'get',
    params: query
  })
}

// 导出判断题 Excel
export function exportJudgmentExcel(query) {
  return request({
    url: '/topic/judgment/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
