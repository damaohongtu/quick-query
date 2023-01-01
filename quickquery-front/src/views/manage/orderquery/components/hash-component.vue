<template>

  <div class="app-container">
    <fieldset :disabled="disableAllInput" style="border: none">
      <div align="left">
        <CommonEditor
          :value="scriptContent.script"
          language="x-groovy"
          style="height: 60vh"
          @input="changeTextarea"
        />
      </div>

      <el-row style="padding-top: 20px">
        <el-col :span="24">

          <el-row>
            <el-col :span="2" style="padding-right: 10px">
              <label>规则编码:</label>
            </el-col>
            <el-col :span="6" align="left">
              <el-input v-model="ruleCode" placeholder="" :disabled="disableEditCode" />
            </el-col>
          </el-row>

          <el-row style="padding-top: 10px">
            <el-col :span="2" style="padding-right: 10px">
              <label><font color="red">*</font>规则名字:</label>
            </el-col>
            <el-col :span="6" align="left">
              <el-input v-model="ruleName" placeholder="" />
            </el-col>
          </el-row>

          <el-row align="left" style="padding-top: 10px">
            <el-col :span="1" align="right" style="padding-right: 10px">
              <el-button type="primary" @click="commitAddHash">提交</el-button>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </fieldset>

  </div>

</template>
<!--"less": "^4.1.3",-->
<!--"less-loader": "5.0.0"-->
<script>
import CommonEditor from '@/components/editor/CommonEditor.vue'

export default {
  name: 'HashComponent',
  components: { CommonEditor },
  data() {
    return {
      ruleCode: '',
      ruleName: '',
      disableAllInput: false,
      disableEditCode: true,
      scriptContent: {
        script: 'class HashStrategy {\n' +
            ' \n' +
            '    int parseDbIndex(String serialNo){\n' +
            '        int index = 0;\n' +
            '        // 你的计算逻辑\n' +
            '        return index;\n' +
            '\n' +
            '    }\n' +
            '\n' +
            '    String parseTablePostfix(String serialNo){\n' +
            '        // 你的计算逻辑\n' +
            '        return "表后缀";\n' +
            '    }\n' +
            ' \n' +
            '}'
      }
    }
  },
  created() {
    if (this.$route.name === 'QueryHash') {
      this.scriptContent.script = this.$route.query.script
      this.ruleCode = this.$route.query.code
      this.ruleName = this.$route.query.name
      this.disableAllInput = true
    }
    if (this.$route.name === 'AddHash') {
      this.disableAllInput = false
      this.disableEditCode = false
    }
    if (this.$route.name === 'EditHash') {
      this.scriptContent.script = this.$route.query.script
      this.ruleCode = this.$route.query.code
      this.ruleName = this.$route.query.name
      this.disableAllInput = false
      this.disableEditCode = true
    }
  },
  methods: {
    changeTextarea(val) {
      this.scriptContent.script = val
    },
    commitAddHash() {
      const data = {
        code: this.ruleCode,
        name: this.ruleName,
        script: this.scriptContent.script
      }
      this.$confirm('此操作将持久化当前配置, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios.post('/hashConfig', data).then(res => {
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
    }
  }
}
</script>

<style scoped>
.content{
  padding: 5px;
}
</style>
