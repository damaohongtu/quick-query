<template>
  <div class="app-container">
    <el-row :gutter='10' class="header-query-add">
      <el-col :span="3">
        <el-input placeholder="请输入配置编码" />
      </el-col>
      <el-col :span="3">
        <el-input placeholder="请输入配置名称"></el-input>
      </el-col>
      <el-button type="primary" icon="el-icon-search" @click="query">查询</el-button>
      <el-button type="primary" icon="el-icon-circle-plus-outline" @click="addHash">新增</el-button>
    </el-row>
    <el-row>
      <div class="content">
        <div class="content">
          <el-table :data="hashInfo" border>
            <el-table-column prop="code" label="分库分表规则编码" />
            <el-table-column prop="name" label="分库分表名" />
            <el-table-column prop="createTime" label="创建时间" />
            <el-table-column prop="updateTime" label="更新时间" />
            <el-table-column prop="createBy" label="创建人" />
            <el-table-column prop="updateBy" label="更新人" />
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button type="success" icon="el-icon-view" plain @click="queryHash(scope.row.code, scope.row.name, scope.row.script)">查看</el-button>
                <el-button type="danger" icon="el-icon-edit" plain @click="editHash(scope.row.code, scope.row.name, scope.row.script)">编辑</el-button>
              </template>
            </el-table-column>

          </el-table>
          <div align="right" style="padding-right: 10px; padding-top: 5px">
            <el-pagination
              background
              :page-sizes="[5, 10, 20, 40]"
              :page-size="5"
              layout="total, sizes, prev, pager, next, jumper"
              :total="100"
            />
          </div>
        </div>
      </div>
    </el-row>
  </div>

</template>

<script>

import { queryAllHash } from '@/api/orderquery'

export default {
  name: 'GraphManage',

  data() {
    return {
      hashInfo: [
        { code: '', name: '', createTime: '', updateTime: '', createBy: '', updateBy: '' }
      ]
    }
  },
  created() {
    queryAllHash().then(res => {
      console.log(res)
      this.hashInfo = res
    })
  },
  methods: {
    addHash() {
      this.$router.push({ path: '/hash-manage/new' })
    },
    queryHash(code, name, script) {
      this.$router.push({ path: '/hash-manage/info', query: { code: code, name: name, script: script }})
    },
    editHash(code, name, script) {
      this.$router.push({ path: '/hash-manage/edit', query: { code: code, name: name, script: script }})
    }
  }
}
</script>

<style scoped>
.content{
  padding: 5px;
}
.header-query-add {
  margin-top: 10px;
  display: flex;
}
</style>
