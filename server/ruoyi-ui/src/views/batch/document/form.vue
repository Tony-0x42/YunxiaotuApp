<template>
  <div class="app-container">
    <h3 class="form-title">{{ title }}</h3>
    <el-form ref="form" :model="form" :rules="rules" label-width="90px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="文档标题" prop="documentTitle">
            <el-input v-model="form.documentTitle" placeholder="请输入文档标题" maxlength="200" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="文档类型" prop="documentType">
            <el-select v-model="form.documentType" placeholder="请选择文档类型" style="width: 100%">
              <el-option
                v-for="item in documentTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="文档分类" prop="category">
            <el-input v-model="form.category" placeholder="请输入文档分类" maxlength="100" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="排序权重" prop="sortWeight">
            <el-input-number v-model="form.sortWeight" :min="0" :max="9999" controls-position="right" style="width: 100%" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="适用页面" prop="applyPagesArray">
            <el-select v-model="form.applyPagesArray" multiple collapse-tags placeholder="请选择适用页面" style="width: 100%">
              <el-option
                v-for="item in applyPageOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="form.status">
              <el-radio label="0">启用</el-radio>
              <el-radio label="1">禁用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="文档内容" prop="content">
            <editor v-model="form.content" :min-height="320" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="form-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getDocument, addDocument, updateDocument } from "@/api/batch/document"

export default {
  name: "BatchDocumentForm",
  data() {
    return {
      // 文档类型选项
      documentTypeOptions: [
        { value: "1", label: "用户协议" },
        { value: "2", label: "隐私政策" },
        { value: "3", label: "新手文档" },
        { value: "4", label: "帮助文档" }
      ],
      // 适用页面选项
      applyPageOptions: [
        { value: "login", label: "登录页" },
        { value: "register", label: "注册页" },
        { value: "mine", label: "我的页" },
        { value: "guide", label: "新手文档页" },
        { value: "help", label: "帮助中心" },
        { value: "setting", label: "系统设置页" }
      ],
      // 表单参数
      form: {
        documentId: undefined,
        documentTitle: undefined,
        documentType: undefined,
        category: undefined,
        applyPagesArray: [],
        content: undefined,
        sortWeight: 0,
        status: "0"
      },
      // 表单校验
      rules: {
        documentTitle: [
          { required: true, message: "文档标题不能为空", trigger: "blur" }
        ],
        documentType: [
          { required: true, message: "文档类型不能为空", trigger: "change" }
        ],
        applyPagesArray: [
          { type: "array", required: true, message: "适用页面不能为空", trigger: "change" }
        ],
        content: [
          { required: true, message: "文档内容不能为空", trigger: "blur" }
        ]
      }
    }
  },
  computed: {
    isEdit() {
      return !!this.$route.params.documentId
    },
    title() {
      return this.isEdit ? "修改文档" : "新增文档"
    }
  },
  created() {
    this.reset()
    if (this.isEdit) {
      this.loadDetail(this.$route.params.documentId)
    }
  },
  methods: {
    // 表单重置
    reset() {
      this.form = {
        documentId: undefined,
        documentTitle: undefined,
        documentType: undefined,
        category: undefined,
        applyPagesArray: [],
        content: undefined,
        sortWeight: 0,
        status: "0"
      }
      this.resetForm("form")
    },
    // 加载文档详情
    loadDetail(documentId) {
      getDocument(documentId).then(response => {
        this.form = response.data
        this.form.status = String(this.form.status)
        this.form.documentType = String(this.form.documentType)
        this.form.applyPagesArray = this.form.applyPages ? this.form.applyPages.split(",") : []
      })
    },
    // 取消按钮
    cancel() {
      this.$tab.closeOpenPage({ path: "/batch/document" })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.form.applyPagesArray || this.form.applyPagesArray.length === 0) {
            this.$modal.msgError("请选择适用页面")
            return
          }
          this.form.applyPages = this.form.applyPagesArray.join(",")
          if (this.isEdit) {
            updateDocument(this.form).then(() => {
              this.$tab.closeOpenPage({ path: "/batch/document" })
              this.$modal.msgSuccess("修改成功")
            })
          } else {
            addDocument(this.form).then(() => {
              this.$tab.closeOpenPage({ path: "/batch/document" })
              this.$modal.msgSuccess("新增成功")
            })
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.form-title {
  margin: 0 0 20px 0;
  font-size: 18px;
  font-weight: bold;
  color: #303133;
}
.form-footer {
  text-align: center;
  margin-top: 20px;
}
</style>
