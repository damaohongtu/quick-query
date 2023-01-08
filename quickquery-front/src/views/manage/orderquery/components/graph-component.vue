<template>
  <div class="app-container">
    <fieldset :disabled="isEditDisabled" style="border: none">
      <el-row>
        <div class="graph" id="graph">
          <butterfly-vue
            :canvas-data="formattedData"
            :base-canvas="baseCanvas"
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
import { Canvas } from 'butterfly-dag'
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
    baseCanvas() {
      const canvas = new Canvas({
        // 如下属性
        root: graph, // canvas的根节点(必传)
        layout: { type: 'ForceLayout', options: {}}, // 布局设置(选填)，可使用集成的，也可自定义布局
        layoutOptions: { rankdir: 'TB' }, // 布局配置(选填)，传入集成/自定义布局的参数
        zoomable: true, // 可缩放(选填)
        moveable: true, // 可平移(选填)
        draggable: true, // 节点可拖动(选填)
        linkable: true, // 节点可连接(选填)
        disLinkable: true, // 节点可取消连接(选填)
        theme: { // 主题定制(选填)
          group: {
            type: 'normal', // 节点组类型(选填): normal(随意拖入拖出),inner(只能拖入不能拖出)
            dragGroupZIndex: 50 // 节点组的默认z-index（选填，默认值：50）
          },
          node: {
            dragNodeZIndex: 250 // 节点的默认z-index/2（选填，默认值250）
          },
          edge: {
            type: 'endpoint', // 线段连接类型
            shapeType: 'Bezier', // 线条默认类型
            hasRadius: false, // 默认曼哈顿曲线不为圆角
            label: 'test', // 线条默认label
            arrow: true, // 线条默认是否带箭头
            arrowPosition: 0.5, // 箭头位置(0 ~ 1)
            arrowOffset: 0.0, // 箭头偏移
            arrowShapeType: '', // 自定义箭头样式
            isExpandWidth: false, // 增加线条交互区域
            defaultAnimate: false, // 默认开启线条动画
            dragEdgeZindex: 499 // 线段的默认z-index(选填，默认：499)
          },
          endpoint: {
            position: [], // 限制锚点位置['Top', 'Bottom', 'Left', 'Right'],
            linkableHighlight: true, // 连线时会触发point.linkable的方法，可做高亮
            limitNum: 10, // 限制锚点的连接数目
            expandArea: { // 锚点过小时，可扩大连线热区
              left: 10,
              right: 10,
              top: 10,
              botton: 10
            }
          },
          zoomGap: 0.001, // 鼠标放大缩小间隙设置
          autoFixCanvas: { // 节点拖动或连线拖动到画布边缘时，画布自动延展
            enable: false,
            autoMovePadding: [20, 20, 20, 20] // 触发自动延展的画布内边距
          },
          autoResizeRootSize: true, // 自动适配root大小，默认为true
          isMouseMoveStopPropagation: true // 拖动事件是否阻止冒泡
        },
        global: { // 自定义配置，会贯穿所有canvas，group，node，edge，endpoint对象
          isScopeStrict: false // scope是否为严格模式(默认为false)
        }
      })
      canvas.setMinimap(true, {/* options */})
      return canvas
    },
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
