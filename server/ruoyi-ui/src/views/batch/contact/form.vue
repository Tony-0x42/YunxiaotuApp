<template>
  <div class="app-container">
    <h3 class="form-title">{{ title }}</h3>
    <el-form ref="form" :model="form" :rules="rules" label-width="90px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="联系人" prop="contactName">
            <el-input v-model="form.contactName" placeholder="请输入联系人/名称" maxlength="100" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="电话" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入电话" maxlength="20" show-word-limit />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="类型" prop="contactType">
            <el-select v-model="form.contactType" placeholder="请选择类型" style="width: 100%">
              <el-option
                v-for="item in contactTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
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
        <el-col :span="12">
          <el-form-item label="区域/说明" prop="region">
            <el-input v-model="form.region" placeholder="请输入区域或说明" maxlength="100" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="form.status">
              <el-radio label="0">启用</el-radio>
              <el-radio label="1">禁用</el-radio>
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
import { getContact, addContact, updateContact } from "@/api/batch/contact"

export default {
  name: "BatchContactForm",
  data() {
    return {
      // 联系类型选项
      contactTypeOptions: [
        { value: "1", label: "在线客服" },
        { value: "2", label: "总部热线" },
        { value: "3", label: "区域联系" }
      ],
      // 表单参数
      form: {
        contactId: undefined,
        contactName: undefined,
        region: undefined,
        phone: undefined,
        contactType: "1",
        sortWeight: 0,
        status: "0"
      },
      // 表单校验
      rules: {
        contactName: [
          { required: true, message: "联系人/名称不能为空", trigger: "blur" }
        ],
        phone: [
          { required: true, message: "电话不能为空", trigger: "blur" }
        ],
        contactType: [
          { required: true, message: "类型不能为空", trigger: "change" }
        ]
      }
    }
  },
  computed: {
    isEdit() {
      return !!this.$route.params.contactId
    },
    title() {
      return this.isEdit ? "修改联系方式" : "新增联系方式"
    }
  },
  created() {
    this.reset()
    if (this.isEdit) {
      this.loadDetail(this.$route.params.contactId)
    }
  },
  methods: {
    // 表单重置
    reset() {
      this.form = {
        contactId: undefined,
        contactName: undefined,
        region: undefined,
        phone: undefined,
        contactType: "1",
        sortWeight: 0,
        status: "0"
      }
      this.resetForm("form")
    },
    // 加载联系详情
    loadDetail(contactId) {
      getContact(contactId).then(response => {
        this.form = response.data
        this.form.status = String(this.form.status)
        this.form.contactType = String(this.form.contactType)
      })
    },
    // 取消按钮
    cancel() {
      this.$tab.closeOpenPage({ path: "/batch/contact" })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.isEdit) {
            updateContact(this.form).then(() => {
              this.$tab.closeOpenPage({ path: "/batch/contact" })
              this.$modal.msgSuccess("修改成功")
            })
          } else {
            addContact(this.form).then(() => {
              this.$tab.closeOpenPage({ path: "/batch/contact" })
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
