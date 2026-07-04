<template>
  <div class="app-container">
    <h3 class="form-title">{{ title }}</h3>
    <el-form ref="form" :model="form" :rules="rules" label-width="90px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="品牌名称" prop="brandName">
            <el-input v-model="form.brandName" placeholder="请输入品牌名称" maxlength="100" show-word-limit />
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
          <el-form-item label="品牌LOGO" prop="logoUrl">
            <image-upload v-model="form.logoUrl" :limit="1" :fileSize="5" :fileType="['png', 'jpg', 'jpeg']" />
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
      <el-row>
        <el-col :span="24">
          <el-form-item label="品牌简介" prop="intro">
            <el-input v-model="form.intro" type="textarea" :rows="2" placeholder="请输入品牌简介" maxlength="500" show-word-limit />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="品牌详情" prop="detail">
            <editor v-model="form.detail" :min-height="320" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="宣传素材" prop="mediaUrls">
            <image-upload v-model="form.mediaUrls" :limit="5" :fileSize="20" :fileType="['png', 'jpg', 'jpeg', 'mp4']" />
            <div class="el-upload__tip">请上传品牌宣传图或视频，最多 5 个，视频支持 mp4 格式</div>
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
import { getBrand, addBrand, updateBrand } from "@/api/batch/brand"

export default {
  name: "BatchBrandForm",
  data() {
    return {
      // 表单参数
      form: {
        brandId: undefined,
        brandName: undefined,
        logoUrl: undefined,
        intro: undefined,
        detail: undefined,
        mediaUrls: undefined,
        sortWeight: 0,
        status: "0"
      },
      // 表单校验
      rules: {
        brandName: [
          { required: true, message: "品牌名称不能为空", trigger: "blur" }
        ]
      }
    }
  },
  computed: {
    isEdit() {
      return !!this.$route.params.brandId
    },
    title() {
      return this.isEdit ? "修改品牌专区" : "新增品牌专区"
    }
  },
  created() {
    this.reset()
    if (this.isEdit) {
      this.loadDetail(this.$route.params.brandId)
    }
  },
  methods: {
    // 表单重置
    reset() {
      this.form = {
        brandId: undefined,
        brandName: undefined,
        logoUrl: undefined,
        intro: undefined,
        detail: undefined,
        mediaUrls: undefined,
        sortWeight: 0,
        status: "0"
      }
      this.resetForm("form")
    },
    // 加载品牌详情
    loadDetail(brandId) {
      getBrand(brandId).then(response => {
        this.form = response.data
        this.form.status = String(this.form.status)
      })
    },
    // 取消按钮
    cancel() {
      this.$tab.closeOpenPage({ path: "/batch/brand" })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.isEdit) {
            updateBrand(this.form).then(() => {
              this.$tab.closeOpenPage({ path: "/batch/brand" })
              this.$modal.msgSuccess("修改成功")
            })
          } else {
            addBrand(this.form).then(() => {
              this.$tab.closeOpenPage({ path: "/batch/brand" })
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
