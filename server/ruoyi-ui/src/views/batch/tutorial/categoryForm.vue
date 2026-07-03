<template>
  <div class="app-container">
    <h4 class="form-title">{{ title }}</h4>
    <el-form ref="categoryForm" :model="categoryForm" :rules="categoryRules" label-width="90px">
      <el-form-item label="分类名称" prop="categoryName">
        <el-input v-model="categoryForm.categoryName" placeholder="请输入分类名称" maxlength="100" show-word-limit />
      </el-form-item>
      <el-form-item label="排序权重" prop="sortWeight">
        <el-input-number v-model="categoryForm.sortWeight" :min="0" :max="9999" controls-position="right" style="width: 100%" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="categoryForm.status">
          <el-radio label="0">启用</el-radio>
          <el-radio label="1">停用</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <div class="form-footer">
      <el-button type="primary" @click="submitCategoryForm">确 定</el-button>
      <el-button @click="cancelCategory">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { addCategory, updateCategory, getCategory } from "@/api/batch/tutorial"

export default {
  name: "BatchTutorialCategoryForm",
  data() {
    return {
      categoryForm: {
        categoryId: undefined,
        categoryName: undefined,
        sortWeight: 0,
        status: "0"
      },
      categoryRules: {
        categoryName: [
          { required: true, message: "分类名称不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ]
      }
    }
  },
  computed: {
    isEdit() {
      return !!this.$route.params.categoryId
    },
    title() {
      return this.isEdit ? "修改分类" : "新增分类"
    }
  },
  created() {
    this.resetCategoryForm()
    if (this.isEdit) {
      getCategory(this.$route.params.categoryId).then(response => {
        this.categoryForm = response.data
      })
    }
  },
  methods: {
    resetCategoryForm() {
      this.categoryForm = {
        categoryId: undefined,
        categoryName: undefined,
        sortWeight: 0,
        status: "0"
      }
      this.resetForm("categoryForm")
    },
    cancelCategory() {
      this.$tab.closeOpenPage({ path: '/batch/tutorial' })
    },
    submitCategoryForm() {
      this.$refs["categoryForm"].validate(valid => {
        if (valid) {
          if (this.categoryForm.categoryId != undefined) {
            updateCategory(this.categoryForm).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.$tab.closeOpenPage({ path: '/batch/tutorial' })
            })
          } else {
            addCategory(this.categoryForm).then(() => {
              this.$modal.msgSuccess("新增成功")
              this.$tab.closeOpenPage({ path: '/batch/tutorial' })
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
  font-size: 16px;
  font-weight: 500;
  color: #303133;
}
.form-footer {
  text-align: center;
  margin-top: 20px;
}
</style>
