<template>
  <div
    :id="itemData.id"
    class="flow-base-node"
    :style="{ top: itemData.top + 'px', left: itemData.left + 'px', width:'500px' }"
  >
    <i class="iconfont ${opts.options.iconType}" />
    <el-row class="bg1">
      <el-col :span="24">
        <!--头部信息-->
        <el-row :gutter="20" type="flex" justify="space-around" style="padding-top: 10px">
          <el-col :span="11">
            <el-form label-width="80px">
              <el-form-item label="节点名:">
                <el-input v-model="itemData.nodeName" />
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="10">
            <el-form label-width="80px">
              <el-form-item label="配置码:">
                <el-input v-model="itemData.id" />
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="3">
            <el-button type="danger" icon="el-icon-close" circle plain @click="delNode(itemData.id)" />
          </el-col>
        </el-row>
        <div class="line" />

        <el-collapse accordion>
          <el-collapse-item title="数据源配置">
            <el-row>
              <el-row>
                <el-col :span="24">
                  <el-form label-width="80px">
                    <el-form-item label="Database:">
                      <el-select v-model="itemData.configInfo.dataSource.dataSource">
                        <el-option
                          v-for="item in itemData.database"
                          :key="item"
                          :title="item"
                          :label="item"
                          :value="item"
                        />
                      </el-select>
                    </el-form-item>
                  </el-form>
                </el-col>
              </el-row>
              <el-row>
                <el-col>
                  <el-form label-width="80px">
                    <el-form-item label="Table:">
                      <el-input v-model="itemData.configInfo.dataSource.table" placeholder="请输入" />
                    </el-form-item>
                  </el-form>
                </el-col>
              </el-row>
            </el-row>

          </el-collapse-item>
          <el-collapse-item title="分库分表规则配置">
            <el-col :span="24">

              <el-form label-width="80px">
                <el-form-item label="分库分表:">
                  <el-select v-model="itemData.configInfo.dataSource.hash">
                    <el-option
                      v-for="item in itemData.hashStrategy"
                      :key="item.code"
                      :title="item.name"
                      :label="item.name"
                      :value="item.code"
                    />
                  </el-select>
                </el-form-item>
              </el-form>
            </el-col>
          </el-collapse-item>
          <el-collapse-item title="输入字段配置">
            <!--输入字段-->
            <el-row>
              <el-form label-width="80px">
                <el-form-item label="字段:">
                  <el-input v-model="itemData.configInfo.inputField.key" placeholder="key" />
                </el-form-item>
                <el-form-item label="字段名:">
                  <el-input v-model="itemData.configInfo.inputField.name" placeholder="name" />
                </el-form-item>
                <el-form-item label="路由规则:">
                  <el-input v-model="itemData.configInfo.routeRule" placeholder="rule" />
                </el-form-item>
              </el-form>
            </el-row>
          </el-collapse-item>
          <el-collapse-item title="输出字段配置">
            <el-row style="padding-bottom: 10px;">
              <el-input v-model="fullTableName" placeholder="请输入表名" class="input-with-select" style="border-color: #409EFF;">
                <el-button
                  slot="append"
                  type="primary"
                  icon="el-icon-download"
                  style="background-color: #409EFF; color: white; border-top-left-radius: 0; border-bottom-left-radius: 0"
                  @click="loadTableInfo(itemData.configInfo.dataSource.dataSource, fullTableName)"
                >加载字段</el-button>
              </el-input>
            </el-row>
            <!--输出字段-->
            <el-row>
              <el-row>
                <div v-for="(item, index) in itemData.configInfo.outputField" :key="index">
                  <el-row type="flex" justify="space-around">
                    <el-col :span="5">
                      <el-input v-model="itemData.configInfo.outputField[index].key" placeholder="key" />
                    </el-col>
                    <el-col :span="5">
                      <el-input v-model="itemData.configInfo.outputField[index].name" placeholder="name" />
                    </el-col>
                    <el-col :span="6">
                      <!-- 设置关联字段 -->
                      <el-select v-model="itemData.configInfo.outputField[index].out" placeholder="关联节点">
                        <el-option
                          v-for="item in itemData.neighbor"
                          :key="item"
                          :title="item"
                          :label="item"
                          :value="item"
                        />
                      </el-select>
                    </el-col>
                    <el-col :span="4">
                      <el-popover
                        placement="right"
                        width="400"
                        trigger="click"
                      >
                        <div>
                          <el-row type="flex" justify="center">
                            <div style="padding-bottom: 10px">配置字段<font color="red">{{ itemData.configInfo.outputField[index].key }}</font>格式化</div>
                          </el-row>
                          <el-row>
                            <el-form label-width="80px">
                              <el-form-item label="枚举映射:">
                                <el-input v-model="itemData.configInfo.outputField[index].displayConfig.enumMap" />
                              </el-form-item>
                              <el-form-item label="超链接:">
                                <el-input v-model="itemData.configInfo.outputField[index].displayConfig.hyperLink" />
                              </el-form-item>
                              <el-form-item label="类型:">
                                <el-radio v-model="itemData.configInfo.outputField[index].displayConfig.type" label="1">金额</el-radio>
                                <el-radio v-model="itemData.configInfo.outputField[index].displayConfig.type" label="2">日期</el-radio>
                                <el-radio v-model="itemData.configInfo.outputField[index].displayConfig.type" label="3">百分数</el-radio>
                              </el-form-item>
                            </el-form>
                          </el-row>
                        </div>
                        <el-button slot="reference" plain>格式化</el-button>
                      </el-popover>
                    </el-col>
                    <el-col :span="2">
                      <el-button type="danger" icon="el-icon-delete" circle plain @click="delOutputField(item.key)" />
                    </el-col>
                  </el-row>
                </div>
                <el-row>
                  <el-col :span="7">&nbsp;</el-col>
                  <el-col :span="7">&nbsp;</el-col>
                  <el-col :span="7">&nbsp;</el-col>
                  <el-col :span="3">
                    <div class="add_button">
                      <el-button type="success" icon="el-icon-plus" circle plain size="mini" @click="addOutputField" />
                    </div>
                  </el-col>
                </el-row>
              </el-row>
            </el-row>
          </el-collapse-item>
        </el-collapse>

        <div class="line" />
        <!--处理器名字-->
        <el-row>
          <el-col :span="24" class="mysql">
            <div>Hive数据源</div>
          </el-col>

        </el-row>
      </el-col>

    </el-row>
  </div>
</template>

<script>

import { queryTableInfo } from '@/api/orderquery'

export default {
  props: ['itemData'],
  data() {
    return {
      input_fields: [
        { id: '', key: '', name: '', type: '' }
      ],
      fullTableName: ''
    }
  },
  methods: {
    onInputConfigInfo(e) {
      this.$store.commit('updateConfigInfo', {
        id: this.itemData.id,
        configInfo: e.target.value
      })
    },
    // 动态添加
    addOutputField() {
      this.itemData.configInfo.outputField.push({
        key: '',
        name: '',
        out: '',
        displayConfig: {
          enumMap: '',
          hyperLink: '',
          type: ''
        }
      })
    },
    delOutputField(key) {
      let index = null
      index = this.itemData.configInfo.outputField.findIndex(item => {
        if (item.key === key) {
          return true
        }
      })
      this.itemData.configInfo.outputField.splice(index, 1)
    },
    // 查看
    search() {
      console.log(this.table)
    },
    delNode(id) {
      console.log(id)
      this.$store.commit('delNode', id)
    },

    // 加载表格字段
    loadTableInfo(dataSourceName, table) {
      const dataSourceType = 'external.mysql'
      queryTableInfo(dataSourceType, dataSourceName, table).then(
        res => {
          res.forEach((item) => {
            this.itemData.configInfo.outputField.push({ key: item.code, name: item.name, type: item.dbType, displayConfig: { enumMap: '',
              hyperLink: '',
              type: ''
            }})
          })
        }
      )
    }
  }
}
</script>

<style>
.bg1 {
  border-radius: 10px;
  border: 1px solid #2c3e50;
}

.line {
  position: relative;
  margin-top: 2px;
  height: 1px;
  background-color: black;
  text-align: center;
  font-size: 16px;
  color: black;
}

.mysql {
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  background-color: #B0E3E6;
}

.field_title {
  text-align: left;
  color: red;
  font-size: 10px;
}

._input {
  border: 0px; /*去除所有边框*/
  outline: none; /*去除选中时出现的边框*/
  border-bottom-color: black; /*下边框颜色*/
  border-bottom-left-radius: 0px; /*下左边框的圆角为0px*/
  border-bottom-right-radius: 0px; /*下右边框的圆角为0px*/
  border-bottom-width: 0.2px; /*下边框的宽度*/
  border-bottom-style: solid; /*下边框的样式 solid-->实线*/
  width: 80px;
}

.width_input {
  border: 0px; /*去除所有边框*/
  outline: none; /*去除选中时出现的边框*/
  border-bottom-color: black; /*下边框颜色*/
  border-bottom-left-radius: 0px; /*下左边框的圆角为0px*/
  border-bottom-right-radius: 0px; /*下右边框的圆角为0px*/
  border-bottom-width: 0.2px; /*下边框的宽度*/
  border-bottom-style: solid; /*下边框的样式 solid-->实线*/
}

.node_title_input {
  width: 50px;
}

.data_source_select {
  width: 140px;
}

.add_button {
  padding-right: 2px;
}

.del_node {
  padding: 2px;
}
</style>
