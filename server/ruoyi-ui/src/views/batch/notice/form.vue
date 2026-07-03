<template>
  <div class="app-container">
    <h3 class="form-title">{{ title }}</h3>
    <el-form ref="form" :model="form" :rules="rules" label-width="90px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="公告标题" prop="noticeTitle">
            <el-input v-model="form.noticeTitle" placeholder="请输入公告标题" maxlength="200" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="公告类型" prop="noticeType">
            <el-select v-model="form.noticeType" placeholder="请选择公告类型" style="width: 100%">
              <el-option
                v-for="item in noticeTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="发布状态" prop="publishStatus">
            <el-select v-model="form.publishStatus" placeholder="请选择发布状态" style="width: 100%">
              <el-option
                v-for="item in publishStatusFormOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="封面图">
            <image-upload v-model="form.coverUrl" :limit="1" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="公告内容" prop="content">
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
import { getNotice, addNotice, updateNotice } from "@/api/batch/notice"

export default {
  name: "BatchNoticeForm",
  data() {
    return {
      // 表单参数
      form: {
        noticeId: undefined,
        noticeTitle: undefined,
        noticeType: 1,
        coverUrl: undefined,
        content: undefined,
        publishStatus: 0,
        readCount: 0
      },
      // 表单校验
      rules: {
        noticeTitle: [
          { required: true, message: "公告标题不能为空", trigger: "blur" }
        ],
        noticeType: [
          { required: true, message: "公告类型不能为空", trigger: "change" }
        ],
        publishStatus: [
          { required: true, message: "发布状态不能为空", trigger: "change" }
        ],
        content: [
          { required: true, message: "公告内容不能为空", trigger: "blur" }
        ]
      },
      noticeTypeOptions: [
        { value: 1, label: "通知" },
        { value: 2, label: "活动" },
        { value: 3, label: "重要更新" }
      ],
      publishStatusFormOptions: [
        { value: 0, label: "立即发布" },
        { value: 2, label: "暂存" }
      ]
    }
  },
  computed: {
    isEdit() {
      return !!this.$route.params.noticeId
    },
    title() {
      return this.isEdit ? "编辑公告" : "新增公告"
    }
  },
  created() {
    this.reset()
    if (this.isEdit) {
      this.loadDetail(this.$route.params.noticeId)
    }
  },
  methods: {
    // 表单重置
    reset() {
      this.form = {
        noticeId: undefined,
        noticeTitle: undefined,
        noticeType: 1,
        coverUrl: undefined,
        content: undefined,
        publishStatus: 0,
        readCount: 0
      }
      this.resetForm("form")
    },
    // 加载公告详情
    loadDetail(noticeId) {
      getNotice(noticeId).then(response => {
        this.form = response.data
        if (this.form.publishStatus === undefined || this.form.publishStatus === null) {
          this.form.publishStatus = 0
        }
      })
    },
    // 取消按钮
    cancel() {
      this.$tab.closeOpenPage({ path: '/batch/notice' })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          const data = { ...this.form }
          // 下架状态编辑时不允许直接改回已发布，需通过发布按钮操作
          if (this.isEdit && data.publishStatus === 0) {
            const oldStatus = this.$route.query.oldStatus
            if (oldStatus !== undefined && String(oldStatus) === "1") {
              data.publishStatus = 1
            }
          }
          if (this.isEdit) {
            updateNotice(data).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.$tab.closeOpenPage({ path: '/batch/notice' })
            })
          } else {
            addNotice(data).then(() => {
              this.$modal.msgSuccess("新增成功")
              this.$tab.closeOpenPage({ path: '/batch/notice' })
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
