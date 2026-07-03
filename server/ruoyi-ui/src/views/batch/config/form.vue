<template>
  <div class="app-container">
    <h3 class="form-title">{{ isEdit ? '修改版本' : '新增版本' }}</h3>
    <el-form ref="versionForm" :model="versionForm" :rules="versionRules" label-width="100px" style="max-width: 700px;">
      <el-form-item label="版本号" prop="versionNo">
        <el-input v-model="versionForm.versionNo" placeholder="请输入版本号，如 1.2.0" maxlength="50" />
      </el-form-item>
      <el-form-item label="平台" prop="platform">
        <el-radio-group v-model="versionForm.platform">
          <el-radio :label="1">Android</el-radio>
          <el-radio :label="2">iOS</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="更新类型" prop="updateType">
        <el-radio-group v-model="versionForm.updateType">
          <el-radio :label="1">强制更新</el-radio>
          <el-radio :label="2">提示更新</el-radio>
          <el-radio :label="3">静默更新</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="下载链接" prop="downloadUrl">
        <el-input v-model="versionForm.downloadUrl" placeholder="请输入下载链接" maxlength="500" />
      </el-form-item>
      <el-form-item label="发布时间" prop="publishTime">
        <el-date-picker v-model="versionForm.publishTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择发布时间" style="width: 100%;" />
      </el-form-item>
      <el-form-item label="更新内容" prop="updateContent">
        <el-input v-model="versionForm.updateContent" type="textarea" :rows="4" placeholder="请输入更新内容" maxlength="2000" show-word-limit />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="versionForm.status">
          <el-radio :label="0">启用</el-radio>
          <el-radio :label="1">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitVersionForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { getVersion, addVersion, updateVersion } from "@/api/batch/config"

export default {
  name: "BatchConfigVersionForm",
  data() {
    return {
      isEdit: false,
      versionForm: {
        versionId: undefined,
        versionNo: "",
        platform: 1,
        updateType: 2,
        updateContent: "",
        downloadUrl: "",
        publishTime: undefined,
        status: 0
      },
      versionRules: {
        versionNo: [{ required: true, message: "版本号不能为空", trigger: "blur" }],
        platform: [{ required: true, message: "平台不能为空", trigger: "change" }],
        updateType: [{ required: true, message: "更新类型不能为空", trigger: "change" }],
        downloadUrl: [{ required: true, message: "下载链接不能为空", trigger: "blur" }],
        publishTime: [{ required: true, message: "发布时间不能为空", trigger: "change" }],
        status: [{ required: true, message: "状态不能为空", trigger: "change" }]
      }
    }
  },
  created() {
    const versionId = this.$route.params.versionId
    if (versionId) {
      this.isEdit = true
      getVersion(versionId).then(response => {
        this.versionForm = response.data
      })
    } else {
      this.isEdit = false
      this.resetVersionForm()
    }
  },
  methods: {
    /** 提交版本表单 */
    submitVersionForm() {
      this.$refs["versionForm"].validate(valid => {
        if (valid) {
          const api = this.isEdit ? updateVersion : addVersion
          api(this.versionForm).then(() => {
            this.$modal.msgSuccess(this.isEdit ? "修改成功" : "新增成功")
            this.$tab.closeOpenPage({ path: "/batch/config", query: { activeTab: "version" } })
          })
        }
      })
    },
    /** 取消 */
    cancel() {
      this.$tab.closeOpenPage({ path: "/batch/config", query: { activeTab: "version" } })
    },
    /** 重置版本表单 */
    resetVersionForm() {
      this.versionForm = {
        versionId: undefined,
        versionNo: "",
        platform: 1,
        updateType: 2,
        updateContent: "",
        downloadUrl: "",
        publishTime: undefined,
        status: 0
      }
      this.resetForm("versionForm")
    }
  }
}
</script>

<style scoped>
.form-title {
  margin: 0 0 20px 0;
  padding-left: 10px;
  border-left: 4px solid #409EFF;
  font-size: 18px;
  color: #303133;
}
</style>
