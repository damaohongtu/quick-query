<template>
  <div class="app-container">
    <fieldset :disabled="isEditDisabled" style="border: none">
      <el-row>
        <div class="graph">
          <butterfly-vue
            :canvas-data="formattedData"
          />
        </div>
      </el-row>
      <el-row :gutter="10" class="datasource_button">
        <el-button type="primary" @click="addMysql">MySQL数据源</el-button>
        <el-button type="primary" @click="addClickhouse">ClickHouse数据源</el-button>
        <el-button type="primary" @click="addHive">Hive数据源</el-button>
        <el-button type="primary" @click="addHttp">HTTP数据源</el-button>
      </el-row>

      <el-row style="padding-top: 20px">
        <el-col :span="24">

          <el-row>
            <el-col :span="1" align="right" style="padding-right: 10px">
              <label>BizCode:</label>
            </el-col>
            <el-col :span="6" align="left">
              <el-input v-model="formattedData.biz.bizCode" placeholder="" />
            </el-col>
          </el-row>

          <el-row style="padding-top: 10px; justify-content: center">
            <el-col :span="1" align="right" style="padding-right: 10px">
              <label><font color="red">*</font>BizName:</label>
            </el-col>
            <el-col :span="6" align="left">
              <el-input v-model="formattedData.biz.bizName" placeholder="" />
            </el-col>
          </el-row>

          <el-row style="padding-top: 10px">
            <el-col :span="1" align="right" style="padding-right: 10px">
              业务描述:
            </el-col>
            <el-col :span="6">
              <el-input v-model="formattedData.biz.bizDesc" type="textarea" :disabled="isEditDisabled" />
            </el-col>
          </el-row>

          <el-row align="left" style="padding-top: 10px">
            <el-col :span="1" align="right" style="padding-right: 10px">
              <el-button type="primary" :disabled="isEditDisabled" @click="commitGraph">提交</el-button>

            </el-col>
          </el-row>
        </el-col>

      </el-row>
    </fieldset>
  </div>

</template>

<script>
import { ButterflyVue } from 'butterfly-vue'
import { PROCESSOR_DICT } from '../processor-dict.js'
import { queryAllDataBase, queryAllHashInfo, queryGraphByCode } from '@/api/orderquery'

export default {
  components: {
    ButterflyVue
  },

  data() {
    return {
      isEditDisabled: false,
      formattedData: {
        biz: {
          bizCode: '',
          bizName: '',
          bizDesc: ''
        },
        nodes: [],
        edges: [],
        relations: [
          { fromNode: '', fromField: '', toNode: '', toField: '' }
        ]
      },
      database: {
        mysql: [],
        hive: [],
        clickhouse: []
      },
      nodes: [],
      hashStrategy: {
        mysql: [
          {
            code: '',
            name: ''
          }
        ]
      }
    }
  },
  computed: {
    nodeMap: {
      get() {
        const map = new Map()
        this.formattedData.nodes.forEach(
          (item) => {
            map.set(item.id, item.configInfo.outputField)
          }
        )
        return map
      }
    }
  },
  mounted() {
    this.disableEdit()
    if (this.$route.name === 'GraphInfo') {
      queryGraphByCode(this.$route.query.graphCode).then(res => {
        const payload = res
        const tmpNodes = []
        const tmpRelations = []
        payload.nodes.forEach(item => {
          tmpNodes.push(
            {
              id: item.id,
              processor: item.nodeType,
              render: PROCESSOR_DICT[item.nodeType],
              top: JSON.parse(item.coordinate).top,
              left: JSON.parse(item.coordinate).left,
              configInfo: {
                dataSource: JSON.parse(item.configInfo.dataSource),
                inputField: JSON.parse(item.configInfo.inputField),
                outputField: JSON.parse(item.configInfo.outputField),
                routeRule: JSON.parse(item.configInfo.routeRule)
              },
              endpoints: [
                { id: 'right', orientation: [1, 0], pos: [0, 0.5] },
                { id: 'bottom', orientation: [0, 1], pos: [0.5, 0] },
                { id: 'left', orientation: [-1, 0], pos: [0, 0.5] },
                { id: 'top', orientation: [0, -1], pos: [0.5, 0] }
              ]
            }
          )
          const relation = JSON.parse(item.configInfo.relations)
          relation.forEach(item => {
            tmpRelations.push(item)
          })
        })

        this.formattedData = {
          'biz': {
            'bizCode': payload.bizCode,
            'bizName': payload.bizName
          },
          'nodes': tmpNodes,
          'edges': JSON.parse(payload.edges),
          'relations': tmpRelations
        }
      })
    }
    if (this.$route.name === 'AddGraph') {
      this.formattedData = {
        biz: {
          bizCode: '',
          bizName: ''
        },
        nodes: [],
        edges: [],
        relations: []
      }
    }
    queryAllDataBase().then(res => {
      this.database = {
        mysql: res.mysql,
        hive: res.hive,
        clickhouse: res.clickhouse
      }
    })

    queryAllHashInfo().then(res => {
      const tmpStrategy = []
      res.forEach(
        item => {
          tmpStrategy.push({ code: item.code, name: item.name })
        }
      )
      this.hashStrategy.mysql = tmpStrategy
    })
  },
  methods: {
    disableEdit() {
      if (this.$route.name === 'QueryGraph') {
        this.isEditDisabled = true
      }
      if (this.$route.name === 'EditGraph') {
        this.isEditDisabled = false
      }
      if (this.$route.name === 'AddGraph') {
        this.isEditDisabled = false
      }
    },
    addMysql() {
      const length = this.formattedData.nodes.length
      this.nodes.push(length + 1)
      this.formattedData.nodes.push({
        id: `${length + 1}`,
        label: 'recordBusinessNo_1',
        left: length * 50,
        top: length * 50,
        iconType: 'icon-xianshang',
        nodeName: '',
        configInfo: {
          outputField: [
            {
              key: '',
              name: '',
              out: '',
              displayConfig: {
                enumMap: '',
                hyperLink: '',
                type: ''
              }
            }
          ],
          inputField: {
            key: '',
            name: '',
            type: ''
          },
          routeRule: '',
          dataSource: {
            dataSource: '',
            table: '',
            hash: ''
          }
        },
        processor: 'mysql',
        render: PROCESSOR_DICT['mysql'],
        database: this.database.mysql,
        neighbor: this.nodes,
        hashStrategy: this.hashStrategy.mysql,
        endpoints: [
          { id: 'right', orientation: [1, 0], pos: [0, 0.5] },
          { id: 'bottom', orientation: [0, 1], pos: [0.5, 0] },
          { id: 'left', orientation: [-1, 0], pos: [0, 0.5] },
          { id: 'top', orientation: [0, -1], pos: [0.5, 0] }
        ]
      })
    },
    addClickhouse() {
      const length = this.formattedData.nodes.length
      this.formattedData.nodes.push({
        id: `${length + 1}`,
        label: 'recordBusinessNo_1',
        left: length * 50,
        top: length * 50,
        iconType: 'icon-xianshang',
        configInfo: {
          outputField: [
            {
              key: '',
              name: '',
              type: ''
            }
          ],
          inputField: {
            key: '',
            name: '',
            type: ''
          },
          routeRule: '',
          dataSource: {
            dataSource: '',
            table: ''
          }
        },
        processor: 'clickhouse',
        render: PROCESSOR_DICT['clickhouse'],
        nodeName: '',
        database: this.database.clickhouse,
        endpoints: [
          { id: 'right', orientation: [1, 0], pos: [0, 0.5] },
          { id: 'bottom', orientation: [0, 1], pos: [0.5, 0] },
          { id: 'left', orientation: [-1, 0], pos: [0, 0.5] },
          { id: 'top', orientation: [0, -1], pos: [0.5, 0] }
        ]
      })
    },
    addHive() {
      const length = this.formattedData.nodes.length
      this.formattedData.nodes.push({
        id: `${length + 1}`,
        label: 'recordBusinessNo_1',
        left: length * 50,
        top: length * 50,
        iconType: 'icon-xianshang',
        configInfo: {
          outputField: [
            {
              key: '',
              name: '',
              type: ''
            }
          ],
          inputField: {
            key: '',
            name: '',
            type: ''
          },
          routeRule: '',
          dataSource: {
            dataSource: '',
            table: ''
          }
        },
        processor: 'hive',
        render: PROCESSOR_DICT['hive'],
        nodeName: '',
        database: this.database.hive,
        endpoints: [
          { id: 'right', orientation: [1, 0], pos: [0, 0.5] },
          { id: 'bottom', orientation: [0, 1], pos: [0.5, 0] },
          { id: 'left', orientation: [-1, 0], pos: [0, 0.5] },
          { id: 'top', orientation: [0, -1], pos: [0.5, 0] }
        ]
      })
    },
    addHttp() {
      const length = this.formattedData.nodes.length
      this.formattedData.nodes.push({
        id: `${length + 1}`,
        label: 'recordBusinessNo_1',
        left: length * 50,
        top: length * 50,
        iconType: 'icon-xianshang',
        configInfo: {
          outputField: [
            {
              key: '',
              name: '',
              type: ''
            }
          ],
          inputField: {
            key: '',
            name: '',
            type: ''
          },
          routeRule: '',
          dataSource: {
            uri: '',
            method: ''
          }
        },
        processor: 'http',
        render: PROCESSOR_DICT['http'],
        nodeName: '',
        endpoints: [
          { id: 'right', orientation: [1, 0], pos: [0, 0.5] },
          { id: 'bottom', orientation: [0, 1], pos: [0.5, 0] },
          { id: 'left', orientation: [-1, 0], pos: [0, 0.5] },
          { id: 'top', orientation: [0, -1], pos: [0.5, 0] }
        ]
      })
    },

    commitGraph() {
      const nodes = []
      for (let index = 0; index < this.formattedData.nodes.length; index++) {
        const node = this.formattedData.nodes[index]
        const curRelations = []
        for (let idx = 0; idx < this.formattedData.relations.length; idx++) {
          if (this.formattedData.relations[idx].fromNode === node.id) {
            curRelations.push(
              {
                fromNode: this.formattedData.relations[idx].fromNode,
                fromField: this.formattedData.relations[idx].fromField,
                toNode: this.formattedData.relations[idx].toNode,
                toField: this.formattedData.relations[idx].toField
              }
            )
          }
        }

        nodes.push(
          {
            'id': node.id,
            'nodeName': node.nodeName,
            'nodeType': node.processor,
            'coordinate': JSON.stringify({
              'left': node.left,
              'top': node.top
            }),
            'configInfo': {
              'dataSource': JSON.stringify(node.configInfo.dataSource),
              'inputField': JSON.stringify(node.configInfo.inputField),
              'outputField': JSON.stringify(node.configInfo.outputField),
              'routeRule': JSON.stringify(node.configInfo.routeRule),
              'relations': JSON.stringify(curRelations)
            }
          }
        )
      }

      const data = {
        'bizCode': this.formattedData.biz.bizCode,
        'bizName': this.formattedData.biz.bizName,
        'bizDesc': this.formattedData.biz.bizDesc,
        'nodes': nodes,
        'edges': JSON.stringify(this.formattedData.edges)
      }

      this.$confirm('此操作将持久化当前配置, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios.post('/config', data).then(res => {
          if (res.status === 200) {
            this.$message({
              type: 'success',
              message: '配置成功!'
            })
            this.$router.go(-1)
          } else {
            this.$message({
              type: 'error',
              message: '配置失败!'
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消配置!'
        })
      })
    },
    addRelation() {
      this.formattedData.relations.push(
        {
          fromNode: '',
          fromField: '',
          toNode: '',
          toField: ''
        }
      )
    },
    delRelation(index) {
      this.formattedData.relations.splice(index, 1)
    }
  }
}
</script>

<style scoped>
.datasource_button{
  margin-top: 10px;
  display: flex;
}

.graph {
  height: 1000px;
  border: 1px solid #2c3e50;
  padding: 10px;
  background-color: #F5FFFA ;
}

.datasource_list {
  align-items: center;
  padding: 10px;
}
</style>
