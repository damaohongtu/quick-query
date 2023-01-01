<!-- 单次查询结果展示组件 -->
<template>
  <div class="app-container">
    <div class="search-title">
      欢迎使用 OrderQuery <span class="search-title-text">{{bizName}}</span>查询功能
    </div>
    <el-row style="margin-bottom: 15px;">
      <el-input v-model="serial_no" v-loading="loading" placeholder="请输入单号" class="input-with-select" style="border-color: #409EFF;">
        <el-select v-model="config_code" slot="prepend" placeholder="请选择" class="el-select">
          <el-option
            v-for="item in configList"
            :key="item.code"
            :title="item.name"
            :label="item.name"
            :value="item.code"
          />
        </el-select>
        <el-button
          slot="append"
          type="primary"
          icon="el-icon-search"
          style="background-color: #409EFF; color: white; border-top-left-radius: 0; border-bottom-left-radius: 0"
          @click="query(bizCode, serial_no)"
        >查询</el-button>
      </el-input>
    </el-row>
    <el-row type="flex" style="flex-wrap: wrap;flex-flow: row wrap;">
      <el-col v-for="(item) in result.data.filter(obj => {return obj.rows.length > 0})" :key="item.id" :lg="{ span:5 }" :offset="0">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>{{ item.nodeName }}</span>
            <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
          </div>
          <div v-if="is_show">
            <div v-for="(row) in item.rows[item.curPage-1]" :key="row.key" class="text item">
              <font color="red"><b>{{ row.name }}:</b></font>{{ row.value }}
            </div>
          </div>
          <div style="position: absolute; bottom: 0; width: 300px ">
            <el-pagination
              small
              layout="prev, pager, next"
              :page-size="1"
              :current-page.sync="item.curPage"
              :total="item.rows.length"
              @current-change="(val) => handleCurrentChange(item, val)"
            />
          </div>

        </el-card>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import { searchOrder } from '@/api/orderquery'
export default {
  name: 'SearchComponent',
  data() {
    return {
      bizCode: '',
      bizName: '',
      config_code: '',
      serial_no: '',
      configList: [
        { code: '1', name: '业务1' },
        { code: '2', name: '业务2' }
      ],
      result: {
        data: [{ nodeCode: '', curPage: 1, rows: [] }]
      },
      is_show: true,
      loading: false
    }
  },
  mounted() {
    this.bizCode = this.$route.query.bizCode
    this.bizName = this.$route.query.bizName
  },
  methods: {
    query(config_code, serial_no) {
      this.loading = true
      console.log('开始查询', config_code, serial_no)
      searchOrder(config_code, serial_no).then(res => {
        if (res.status === 200) {
          this.result = res.data
          res.data.data.map(x => {
            x.curPage = 1
          })
          this.result = res.data
          console.log(this.result)
        } else {
          this.result = {
            data: [{ rows: [] }]
          }
        }
        this.loading = false
      })
    },
    handleCurrentChange(parm, val) {
      console.log(parm.curPage, val)
      this.is_show = false
      this.$nextTick(() => {
        this.is_show = true
      })
    }
  }

}
</script>

<style scoped>
  .search-title {
    padding-bottom: 10px;
  }
  .search-title-text {
    color: red;
  }
  .text {
    font-size: 14px;
    text-align: left;
  }

  .el-select {
    width: 150px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 360px;
    margin-top: 18px;
    height: 100%;
  }

</style>

