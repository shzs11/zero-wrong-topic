import request from '@/utils/request'

// 创建错题关联
export function createWrong(data) {
  return request({
    url: '/topic/wrong/create',
    method: 'post',
    data: data
  })
}

// 更新错题关联
export function updateWrong(data) {
  return request({
    url: '/topic/wrong/update',
    method: 'put',
    data: data
  })
}

// 删除错题关联
export function deleteWrong(id) {
  return request({
    url: '/topic/wrong/delete?id=' + id,
    method: 'delete'
  })
}

// 获得错题关联
export function getWrong(id) {
  return request({
    url: '/topic/wrong/get?id=' + id,
    method: 'get'
  })
}

// 获得错题关联分页
export function getWrongPage(query) {
  return request({
    url: '/topic/wrong/page',
    method: 'get',
    params: query
  })
}

// 导出错题关联 Excel
export function exportWrongExcel(query) {
  return request({
    url: '/topic/wrong/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
