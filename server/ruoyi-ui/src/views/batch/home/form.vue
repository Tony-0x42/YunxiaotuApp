<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <!-- 轮播图表单 -->
      <template v-if="type === 'banner'">
        <el-form-item label="图片" prop="imageUrl">
          <image-upload v-model="form.imageUrl" :limit="1" :fileSize="5" :fileType="['png', 'jpg', 'jpeg']" />
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" maxlength="200" show-word-limit />
        </el-form-item>
        <el-form-item label="跳转链接">
          <el-input v-model="form.linkUrl" placeholder="请输入跳转链接" maxlength="500" show-word-limit />
        </el-form-item>
        <el-form-item label="排序权重">
          <el-input-number v-model="form.sortWeight" :min="0" :step="1" step-strictly controls-position="right" />
        </el-form-item>
      </template>

      <!-- 喜报数据表单 -->
      <template v-if="type === 'news'">
        <el-form-item label="业绩标题" prop="newsTitle">
          <el-input v-model="form.newsTitle" placeholder="请输入业绩标题" maxlength="200" show-word-limit />
        </el-form-item>
        <el-form-item label="销售冠军" prop="championName">
          <el-input v-model="form.championName" placeholder="请输入销售冠军姓名" maxlength="50" show-word-limit />
        </el-form-item>
        <el-form-item label="销售金额" prop="salesAmount">
          <el-input-number v-model="form.salesAmount" :min="0" :precision="2" :step="0.01" controls-position="right" style="width: 100%" />
        </el-form-item>
      </template>

      <!-- 功能入口表单 -->
      <template v-if="type === 'entry'">
        <el-form-item label="入口名称" prop="entryName">
          <el-input v-model="form.entryName" placeholder="请输入入口名称" maxlength="100" show-word-limit />
        </el-form-item>
        <el-form-item label="图标" prop="iconUrl">
          <image-upload v-model="form.iconUrl" :limit="1" :fileSize="5" :fileType="['png', 'jpg', 'jpeg']" />
        </el-form-item>
        <el-form-item label="跳转类型" prop="targetType">
          <el-select v-model="form.targetType" placeholder="请选择跳转类型" style="width: 100%">
            <el-option v-for="item in targetTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="跳转目标" prop="targetValue">
          <el-input v-model="form.targetValue" placeholder="请输入跳转目标值" maxlength="500" show-word-limit />
        </el-form-item>
        <el-form-item label="排序权重">
          <el-input-number v-model="form.sortWeight" :min="0" :step="1" step-strictly controls-position="right" />
        </el-form-item>
      </template>

      <!-- 教程入口表单 -->
      <template v-if="type === 'tutorial'">
        <el-form-item label="入口标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入入口标题" maxlength="100" show-word-limit />
        </el-form-item>
        <el-form-item label="封面图" prop="coverUrl">
          <image-upload v-model="form.coverUrl" :limit="1" :fileSize="5" :fileType="['png', 'jpg', 'jpeg']" />
        </el-form-item>
        <el-form-item label="关联文档" prop="documentId">
          <el-select v-model="form.documentId" placeholder="请选择关联文档" filterable clearable style="width: 100%">
            <el-option v-for="item in documentOptions" :key="item.documentId" :label="item.documentTitle" :value="item.documentId" />
          </el-select>
        </el-form-item>
        <el-form-item label="排序权重">
          <el-input-number v-model="form.sortWeight" :min="0" :step="1" step-strictly controls-position="right" />
        </el-form-item>
      </template>

      <el-form-item label="状态">
        <el-radio-group v-model="form.status">
          <el-radio v-for="item in statusOptions" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {
  getBanner, addBanner, updateBanner,
  getNews, addNews, updateNews,
  getEntry, addEntry, updateEntry,
  getTutorialEntry, addTutorialEntry, updateTutorialEntry,
  listDocumentOption
} from "@/api/batch/home"

export default {
  name: "BatchHomeForm",
  data() {
    return {
      type: this.$route.params.type || "banner",
      isEdit: false,
      documentOptions: [],
      statusOptions: [
        { label: "启用", value: "0" },
        { label: "禁用", value: "1" }
      ],
      targetTypeOptions: [
        { label: "页面", value: "1" },
        { label: "URL", value: "2" },
        { label: "功能码", value: "3" }
      ],
      form: {},
      rules: {
        title: [{ required: true, message: "标题不能为空", trigger: "blur" }],
        imageUrl: [{ required: true, message: "请上传图片", trigger: "change" }],
        newsTitle: [{ required: true, message: "业绩标题不能为空", trigger: "blur" }],
        championName: [{ required: true, message: "销售冠军姓名不能为空", trigger: "blur" }],
        salesAmount: [{ required: true, message: "销售金额不能为空", trigger: "blur" }],
        entryName: [{ required: true, message: "入口名称不能为空", trigger: "blur" }],
        iconUrl: [{ required: true, message: "请上传图标", trigger: "change" }],
        targetType: [{ required: true, message: "请选择跳转类型", trigger: "change" }],
        targetValue: [{ required: true, message: "请输入跳转目标", trigger: "blur" }],
        coverUrl: [{ required: true, message: "请上传封面图", trigger: "change" }],
        documentId: [{ required: true, message: "请选择关联文档", trigger: "change" }]
      }
    }
  },
  created() {
    this.init()
  },
  methods: {
    /** 初始化 */
    init() {
      this.reset()
      if (this.type === "tutorial") {
        this.loadDocumentOptions()
      }
      const id = this.$route.params.id
      if (id) {
        this.isEdit = true
        this.getDetail(id)
      }
    },
    /** 加载关联文档下拉 */
    loadDocumentOptions() {
      listDocumentOption().then(response => {
        this.documentOptions = response.data || []
      })
    },
    /** 获取详情 */
    getDetail(id) {
      const getApi = {
        banner: getBanner,
        news: getNews,
        entry: getEntry,
        tutorial: getTutorialEntry
      }
      getApi[this.type](id).then(response => {
        this.form = response.data
      })
    },
    /** 表单重置 */
    reset() {
      let defaults = { status: "0", sortWeight: 0 }
      if (this.type === "banner") {
        defaults = { bannerId: undefined, title: undefined, imageUrl: undefined, linkUrl: undefined, sortWeight: 0, status: "0" }
      } else if (this.type === "news") {
        defaults = { newsId: undefined, newsTitle: undefined, championName: undefined, salesAmount: 0, status: "0" }
      } else if (this.type === "entry") {
        defaults = { entryId: undefined, entryName: undefined, iconUrl: undefined, targetType: undefined, targetValue: undefined, sortWeight: 0, status: "0" }
      } else if (this.type === "tutorial") {
        defaults = { entryId: undefined, title: undefined, coverUrl: undefined, documentId: undefined, sortWeight: 0, status: "0" }
      }
      this.form = defaults
      this.resetForm("form")
    },
    /** 取消 */
    cancel() {
      this.$tab.closeOpenPage({ path: "/batch/home", query: { tab: this.type } })
    },
    /** 提交 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) return
        const idField = this.getIdField()
        const addApi = {
          banner: addBanner,
          news: addNews,
          entry: addEntry,
          tutorial: addTutorialEntry
        }
        const updateApi = {
          banner: updateBanner,
          news: updateNews,
          entry: updateEntry,
          tutorial: updateTutorialEntry
        }
        const api = this.form[idField] !== undefined ? updateApi[this.type] : addApi[this.type]
        api(this.form).then(() => {
          this.$modal.msgSuccess(this.form[idField] !== undefined ? "修改成功" : "新增成功")
          this.$tab.closeOpenPage({ path: "/batch/home", query: { tab: this.type } })
        })
      })
    },
    /** 工具：ID 字段名 */
    getIdField() {
      const map = { banner: "bannerId", news: "newsId", entry: "entryId", tutorial: "entryId" }
      return map[this.type]
    }
  }
}
</script>
