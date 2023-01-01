import { OrderQueryRequest } from '@/utils/request'

// 单据查询接口
export function searchOrder(bizCode, serialNo) {
  return OrderQueryRequest({
    url: '/api/orderquery/order/query',
    method: 'get',
    params: { 'bizCode': bizCode, 'serialNo': serialNo }
  })
}

// 获取所有的配置图
export function queryAllGraph() {
  return OrderQueryRequest({
    url: '/api/orderquery/graph',
    method: 'get'
  })
}

export function queryGraphByCode(graphCode) {
  return OrderQueryRequest({
    url: '/api/orderquery/graph/query',
    method: 'get',
    params: { 'graphCode': graphCode }
  })
}

// 获取所有的分库分表规则
export function queryAllHash() {
  return OrderQueryRequest({
    url: '/api/orderquery/hash',
    method: 'get'
  })
}

// 通过数据源名称和表格名获取表格字段
export function queryTableInfo(dataSourceType, dataSourceName, table) {
  return OrderQueryRequest({
    url: '/api/orderquery/tableInfo',
    method: 'get',
    params: { 'dataSourceType': dataSourceType, 'dataSourceName': dataSourceName, table: table }
  })
}

// 获取所有的数据库
export function queryAllDataBase() {
  return OrderQueryRequest({
    url: '/api/orderquery/database',
    method: 'get'
  })
}

// 获取所有的分库分表规则
export function queryAllHashInfo() {
  return OrderQueryRequest({
    url: '/api/orderquery/hash',
    method: 'get'
  })
}
