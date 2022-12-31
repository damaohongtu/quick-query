<template>
  <div class="app-container">
    <el-row>
      <el-col :span="24">
        <el-row style="display: flex; justify-content: flex-start;">
          <el-col :span="4">
            <el-form>
              <el-form-item label="配置编码:" prop="name">
                <el-input placeholder="请输入" style="width: 200px;" />
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="4">
            <el-form>
              <el-form-item label="配置名称:">
                <el-input placeholder="请输入" style="width: 200px;">></el-input>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" icon="el-icon-search" @click="query">查询</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" icon="el-icon-circle-plus-outline" @click="add">新增</el-button>
          </el-col>
        </el-row>
        <el-row>
          <div class="content">
            <div class="content">
              <el-table :data="graphInfo" border>
                <el-table-column prop="graphCode" label="配置编码" />
                <el-table-column prop="graphName" label="业务名" />
                <el-table-column prop="createTime" label="创建时间" />
                <el-table-column prop="updateTime" label="更新时间" />
                <el-table-column prop="updateBy" label="操作">
                  <template slot-scope="scope">
                    <el-button type="success" icon="el-icon-view" plain @click="query(scope.row.graphCode)">查看</el-button>
                    <el-button type="danger" icon="el-icon-edit" plain @click="edit(scope.row.graphCode)">编辑</el-button>
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
      </el-col>
    </el-row>
  </div>

</template>

<script>
import { queryAllGraph } from '@/api/orderquery'

export default {
  name: 'GraphManage',

  data() {
    return {
      activeName: 'first',
      graphInfo: [
        { graphCode: '', graphName: '', createTime: '', updateTime: '', createBy: '', updateBy: '' }
      ],
      graphCondition: {
        code: ''
      },
      hashInfo: [
        { code: '', name: '', createTime: '', updateTime: '', createBy: '', updateBy: '' }
      ]
    }
  },
  created() {
    queryAllGraph().then(res => {
      this.graphInfo = res
    })
  },
  methods: {
    add() {
      this.$router.push({ path: '/graph-manage/new' })
    },
    query(graphCode) {
      this.$router.push({ path: '/graph-manage/info', query: { graphCode: graphCode }})
    },
    edit(graphCode) {
      this.$router.push({ path: '/graph-manage/edit', query: { graphCode: graphCode }})
    }
  }
}
</script>

<style scoped>
.content{
  padding: 5px;

}
</style>
