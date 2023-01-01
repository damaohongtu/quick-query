<template>
  <div :id="itemData.id" class="flow-base-node" :style="{ top: itemData.top + 'px', left: itemData.left + 'px', width:'300px' }">
    <i class="iconfont ${opts.options.iconType}" />
    <el-row class="bg1">
      <el-col :span="24">
        <!--头部信息-->
        <el-row>
          <el-col :span="10" class="inline">
            <label>节点名:</label>
            <input v-model="itemData.nodeName" type="input" class="node_title_input">
          </el-col>
          <el-col :span="10" class="inline">
            <label>配置码:</label>
            <input v-model="itemData.id" type="text" disabled="disabled" class="node_title_input">
          </el-col>
          <el-col :span="4">
            <div class="del_node">
              <button @click="delNode(itemData.id)">x</button>
            </div>
          </el-col>
        </el-row>
        <div class="line" />

        <el-collapse>
          <el-collapse-item title="输出字段配置">
            <!--输出字段-->
            <el-row class="input_data">
              <el-row>
                <div v-for="(item, index) in itemData.configInfo.outputField" :key="index">
                  <el-row>
                    <el-col :span="7">
                      <el-input v-model="itemData.configInfo.outputField[index].key" placeholder="key" size="mini" />
                    </el-col>
                    <el-col :span="7">
                      <el-input v-model="itemData.configInfo.outputField[index].name" placeholder="key" size="mini" />
                    </el-col>
                    <el-col :span="7">
                      <el-select v-model="itemData.configInfo.outputField[index].type" size="mini" placeholder="请选择">
                        <el-option label="string" value="string" />
                        <el-option label="int" value="int" />
                      </el-select>
                    </el-col>
                    <el-col :span="3">
                      <el-button type="danger" icon="el-icon-delete" circle plain size="mini" @click="delOutputField(item.key)" />
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

          <el-collapse-item title="输入字段配置">
            <!--输入字段-->
            <el-row>
              <el-row>
                <el-col :span="8">
                  <el-input v-model="itemData.configInfo.inputField.key" placeholder="key" size="mini" />
                </el-col>
                <el-col :span="8">
                  <el-input v-model="itemData.configInfo.inputField.name" placeholder="name" size="mini" />
                </el-col>
                <el-col :span="8">
                  <el-select v-model="itemData.configInfo.inputField.type" size="mini" placeholder="请选择">
                    <el-option label="string" value="string">string</el-option>
                    <el-option label="string" value="string">int</el-option>
                  </el-select>
                </el-col>
              </el-row>
              <el-row>
                <el-form label-width="80px">
                  <el-form-item label="路由规则:">
                    <el-input v-model="itemData.configInfo.routeRule" size="mini" />
                  </el-form-item>
                </el-form>
              </el-row>
            </el-row>
          </el-collapse-item>
          <!--数据源-->
          <el-collapse-item title="数据源配置">

            <el-row>

              <el-row>
                <el-col>
                  <el-form label-width="80px">
                    <el-form-item label="URI:">
                      <el-input v-model="itemData.configInfo.dataSource.uri" placeholder="请输入" size="mini" />
                    </el-form-item>
                  </el-form>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="24">
                  <el-form label-width="80px">
                    <el-form-item label="method:">
                      <el-select v-model="itemData.configInfo.dataSource.method" size="mini" placeholder="请选择">
                        <el-option label="POST" value="POST" />
                        <el-option label="GET" value="GET" />
                      </el-select>
                    </el-form-item>
                  </el-form>
                </el-col>
              </el-row>
            </el-row>

          </el-collapse-item>

        </el-collapse>

        <div class="line" />
        <!--处理器名字-->
        <el-row class="http">
          <el-col :span="24">
            <div>HTTP数据源</div>
          </el-col>
        </el-row>
      </el-col>

    </el-row>
  </div>
</template>

<script>

export default {
  props: ['itemData'],
  data() {
    return {
      input_fields: [
        { id: '1', key: '1', name: '', type: '' }
      ]
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
      this.itemData.configInfo.outputField.push({ key: '', name: '', type: '' })
    },
    delOutputField(key) {
      let index = null
      index = this.itemData.configInfo.outputField.findIndex(item => {
        if (item.key == key) {
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
    }
  }
}
</script>

<style>
.bg1{
  border-radius: 10px;
  background-color: #FFFFF0;
  border: 1px solid #2c3e50;
}
.input_data{

}
.inline{
  align-content: flex-start;
  display: inline;
  flex-wrap: wrap;
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
.http{
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  background-color: #FFC20E;
}
.field_title{
  text-align: left;
  color: red;
  font-size: 10px;
}
._input{
  border: 0px;	/*去除所有边框*/
  outline: none;	/*去除选中时出现的边框*/
  border-bottom-color: black;	/*下边框颜色*/
  border-bottom-left-radius: 0px;	/*下左边框的圆角为0px*/
  border-bottom-right-radius: 0px;	/*下右边框的圆角为0px*/
  border-bottom-width: 0.2px;	/*下边框的宽度*/
  border-bottom-style: solid;	/*下边框的样式 solid-->实线*/
  width: 80px;
}
.width_input{
  border: 0px;	/*去除所有边框*/
  outline: none;	/*去除选中时出现的边框*/
  border-bottom-color: black;	/*下边框颜色*/
  border-bottom-left-radius: 0px;	/*下左边框的圆角为0px*/
  border-bottom-right-radius: 0px;	/*下右边框的圆角为0px*/
  border-bottom-width: 0.2px;	/*下边框的宽度*/
  border-bottom-style: solid;	/*下边框的样式 solid-->实线*/
}
.node_title_input{
  width: 50px;
}
.data_source_select{
  width: 140px;
}
.add_button{
  padding-right: 2px;
}
</style>
