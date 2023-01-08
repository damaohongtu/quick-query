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
      <el-button type="primary" icon="el-icon-circle-plus-outline" @click="dialogFormVisible = true">新增</el-button>
    </el-row>

    <el-row>
      <div class="content">
        <el-table :data="graphInfo" border>
          <el-table-column prop="graphCode" label="配置编码" />
          <el-table-column prop="graphName" label="业务名" />
          <el-table-column prop="createTime" label="创建时间" />
          <el-table-column prop="updateTime" label="更新时间" />
          <el-table-column prop="createBy" label="创建人" />
          <el-table-column prop="updateBy" label="更新人" />
          <el-table-column label="操作">
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
    </el-row>
    <el-row>
      <el-dialog title="数据源配置" :visible.sync="dialogFormVisible">
        <el-form :model="form">
          <el-form-item label="数据源类型" :label-width="formLabelWidth">
            <el-select v-model="form.region" placeholder="请选数据源类型">
              <el-option label="MySQL" value="mysql"></el-option>
              <el-option label="ClickHouse" value="clickhouse"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Host" :label-width="formLabelWidth">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="Port" :label-width="formLabelWidth">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="Username" :label-width="formLabelWidth">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="Password" :label-width="formLabelWidth">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="success" @click="dialogFormVisible = false">测试</el-button>
          <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
        </div>
      </el-dialog>
    </el-row>
  </div>

</template>

<script>
import { queryAllGraph } from '@/api/orderquery'

export default {
  name: 'GraphManage',

  data() {
    return {
      graphInfo: [
        { graphCode: '', graphName: '', createTime: '', updateTime: '', createBy: '', updateBy: '' }
      ],
      graphCondition: {
        code: ''
      },
      dialogFormVisible: false,
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      formLabelWidth: '120px'
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
.header-query-add {
  margin-top: 10px;
  display: flex;
}
</style>
