import MysqlProcessor from '@/views/manage/orderquery/components/processor/mysql-processor'
import ClickhouseProcessor from '@/views/manage/orderquery/components/processor/clickhouse-processor'
import HiveProcessor from '@/views/manage/orderquery/components/processor/hive-processor'
import HttpProcessor from '@/views/manage/orderquery/components/processor/http-processor'
export const PROCESSOR_DICT = {
  mysql: MysqlProcessor,
  clickhouse: ClickhouseProcessor,
  hive: HiveProcessor,
  http: HttpProcessor
}
