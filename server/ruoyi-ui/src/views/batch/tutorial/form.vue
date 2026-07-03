<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="90px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="教程标题" prop="tutorialTitle">
            <el-input v-model="form.tutorialTitle" placeholder="请输入教程标题" maxlength="200" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="教程类型" prop="tutorialType">
            <el-select v-model="form.tutorialType" placeholder="请选择教程类型" style="width: 100%">
              <el-option label="视频" value="1" />
              <el-option label="图文" value="2" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="所属分类" prop="categoryId">
            <el-select v-model="form.categoryId" placeholder="请选择分类" clearable style="width: 100%">
              <el-option
                v-for="item in categoryOptions"
                :key="item.categoryId"
                :label="item.categoryName"
                :value="item.categoryId"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="排序权重" prop="sortWeight">
            <el-input-number v-model="form.sortWeight" :min="0" :max="9999" controls-position="right" style="width: 100%" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="封面图" prop="coverUrl">
            <image-upload v-model="form.coverUrl" :limit="1" :file-size="5" :file-type="['png', 'jpg', 'jpeg']" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="form.tutorialType == '1'">
        <el-col :span="24">
          <el-form-item label="视频文件" prop="videoUrl">
            <file-upload
              v-model="form.videoUrl"
              :limit="1"
              :file-size="200"
              :file-type="['mp4', 'mov', 'avi', 'wmv', 'flv', 'mkv']"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="form.tutorialType == '2'">
        <el-col :span="24">
          <el-form-item label="图文内容" prop="documentContent">
            <editor v-model="form.documentContent" :min-height="320" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="简介" prop="intro">
            <el-input v-model="form.intro" type="textarea" :rows="3" placeholder="请输入教程简介" maxlength="500" show-word-limit />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="form.status">
              <el-radio label="0">上架</el-radio>
              <el-radio label="1">下架</el-radio>
            </el-radio-group>
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
import { getTutorial, addTutorial, updateTutorial, listCategoryAll } from "@/api/batch/tutorial"

export default {
  name: "BatchTutorialForm",
  data() {
    return {
      // 是否编辑模式
      isEdit: false,
      // 分类选项
      categoryOptions: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        tutorialTitle: [
          { required: true, message: "教程标题不能为空", trigger: "blur" }
        ],
        tutorialType: [
          { required: true, message: "教程类型不能为空", trigger: "change" }
        ],
        categoryId: [
          { required: true, message: "请选择所属分类", trigger: "change" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ]
      }
    }
  },
  watch: {
    "form.tutorialType"(val) {
      if (val == '1') {
        this.form.documentContent = undefined
      } else if (val == '2') {
        this.form.videoUrl = undefined
      }
    }
  },
  created() {
    this.getCategoryOptions()
    const tutorialId = this.$route.params.tutorialId
    if (tutorialId) {
      this.isEdit = true
      getTutorial(tutorialId).then(response => {
        this.form = response.data
        this.form.tutorialType = String(this.form.tutorialType)
        this.form.status = String(this.form.status)
      })
    } else {
      this.isEdit = false
      this.reset()
    }
  },
  methods: {
    /** 查询分类选项 */
    getCategoryOptions() {
      listCategoryAll().then(response => {
        this.categoryOptions = response.data || []
      })
    },
    // 表单重置
    reset() {
      this.form = {
        tutorialId: undefined,
        tutorialTitle: undefined,
        tutorialType: "1",
        categoryId: undefined,
        coverUrl: undefined,
        videoUrl: undefined,
        documentContent: undefined,
        intro: undefined,
        sortWeight: 0,
        viewCount: 0,
        status: "0"
      }
      this.resetForm("form")
    },
    // 取消按钮
    cancel() {
      this.$tab.closeOpenPage({ path: '/batch/tutorial' })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.tutorialType == '1' && !this.form.videoUrl) {
            this.$modal.msgError("请上传视频文件")
            return
          }
          if (this.form.tutorialType == '2' && !this.form.documentContent) {
            this.$modal.msgError("请输入图文内容")
            return
          }
          if (this.isEdit) {
            updateTutorial(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.$tab.closeOpenPage({ path: '/batch/tutorial' })
            })
          } else {
            addTutorial(this.form).then(() => {
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
.form-footer {
  text-align: center;
  padding-top: 20px;
}
</style>
