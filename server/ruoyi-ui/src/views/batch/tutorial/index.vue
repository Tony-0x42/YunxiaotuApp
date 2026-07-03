<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="教程标题" prop="tutorialTitle">
        <el-input
          v-model="queryParams.tutorialTitle"
          placeholder="请输入教程标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教程类型" prop="tutorialType">
        <el-select v-model="queryParams.tutorialType" placeholder="全部" clearable style="width: 120px">
          <el-option label="视频" value="1" />
          <el-option label="图文" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="所属分类" prop="categoryId">
        <el-select v-model="queryParams.categoryId" placeholder="全部" clearable style="width: 140px">
          <el-option
            v-for="item in categoryOptions"
            :key="item.categoryId"
            :label="item.categoryName"
            :value="item.categoryId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="全部" clearable style="width: 100px">
          <el-option label="上架" value="0" />
          <el-option label="下架" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['batch:tutorial:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['batch:tutorial:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['batch:tutorial:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['batch:tutorial:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-collection-tag"
          size="mini"
          @click="handleCategoryManage"
          v-hasPermi="['batch:tutorial:list']"
        >分类管理</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tutorialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" align="center" width="60" />
      <el-table-column label="教程标题" align="center" prop="tutorialTitle" :show-overflow-tooltip="true" />
      <el-table-column label="教程类型" align="center" prop="tutorialType" width="90">
        <template slot-scope="scope">
          <el-tag :type="scope.row.tutorialType == '1' ? 'danger' : 'success'" size="small">
            {{ scope.row.tutorialType == '1' ? '视频' : '图文' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="所属分类" align="center" prop="categoryName" width="120" />
      <el-table-column label="封面" align="center" prop="coverUrl" width="100">
        <template slot-scope="scope">
          <el-image
            v-if="scope.row.coverUrl"
            style="width: 60px; height: 60px; border-radius: 4px"
            :src="scope.row.coverUrl"
            :preview-src-list="[scope.row.coverUrl]"
            fit="cover"
          />
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="发布时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
            v-hasPermi="['batch:tutorial:edit']"
          />
          <span style="margin-left: 5px">{{ scope.row.status == '0' ? '上架' : '下架' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="浏览次数" align="center" prop="viewCount" width="90" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="220">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handlePreview(scope.row)"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['batch:tutorial:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['batch:tutorial:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 查看弹窗 -->
    <el-dialog title="教程详情" :visible.sync="previewOpen" width="700px" append-to-body>
      <div class="tutorial-preview">
        <h2 class="preview-title">{{ previewData.tutorialTitle }}</h2>
        <div class="preview-meta">
          <span>类型：{{ previewData.tutorialType == '1' ? '视频' : '图文' }}</span>
          <span style="margin-left: 20px">分类：{{ previewData.categoryName || '-' }}</span>
          <span style="margin-left: 20px">发布时间：{{ parseTime(previewData.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </div>
        <div v-if="previewData.coverUrl" class="preview-cover">
          <el-image :src="previewData.coverUrl" style="max-width: 100%; border-radius: 4px" />
        </div>
        <div v-if="previewData.tutorialType == '1' && previewData.videoUrl" class="preview-video">
          <video :src="previewData.videoUrl" controls style="width: 100%; max-height: 400px"></video>
        </div>
        <div v-if="previewData.intro" class="preview-intro">
          <h4>简介</h4>
          <p>{{ previewData.intro }}</p>
        </div>
        <div v-if="previewData.tutorialType == '2' && previewData.documentContent" class="preview-content" v-html="previewData.documentContent"></div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="previewOpen = false">关 闭</el-button>
      </div>
    </el-dialog>

    <!-- 分类管理抽屉 -->
    <el-drawer title="分类管理" :visible.sync="categoryDrawer" direction="rtl" size="600px">
      <div style="padding: 0 20px 20px">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAddCategory"
              v-hasPermi="['batch:tutorial:add']"
            >新增分类</el-button>
          </el-col>
        </el-row>
        <el-table v-loading="categoryLoading" :data="categoryList">
          <el-table-column label="分类名称" align="center" prop="categoryName" />
          <el-table-column label="排序权重" align="center" prop="sortWeight" width="100" />
          <el-table-column label="状态" align="center" prop="status" width="90">
            <template slot-scope="scope">
              <el-tag :type="scope.row.status == '0' ? 'success' : 'danger'" size="small">
                {{ scope.row.status == '0' ? '启用' : '停用' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="160">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdateCategory(scope.row)"
                v-hasPermi="['batch:tutorial:edit']"
              >编辑</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDeleteCategory(scope.row)"
                v-hasPermi="['batch:tutorial:remove']"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-drawer>


  </div>
</template>

<script>
import {
  listTutorial, delTutorial, changeTutorialStatus, listCategory, listCategoryAll,
  delCategory
} from "@/api/batch/tutorial"

export default {
  name: "BatchTutorial",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 教程表格数据
      tutorialList: [],
      // 分类选项
      categoryOptions: [],
      // 是否显示查看弹窗
      previewOpen: false,
      // 预览数据
      previewData: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tutorialTitle: undefined,
        tutorialType: undefined,
        categoryId: undefined,
        status: undefined
      },
      // 分类抽屉
      categoryDrawer: false,
      categoryLoading: false,
      categoryList: []
    }
  },
  created() {
    this.getCategoryOptions()
    this.getList()
  },
  methods: {
    /** 查询教程列表 */
    getList() {
      this.loading = true
      listTutorial(this.queryParams).then(response => {
        this.tutorialList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询分类选项 */
    getCategoryOptions() {
      listCategoryAll().then(response => {
        this.categoryOptions = response.data || []
      })
    },
    /** 查询分类列表 */
    getCategoryList() {
      this.categoryLoading = true
      listCategory({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.categoryList = response.rows
        this.categoryLoading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tutorialId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$tab.openPage('新增教程', '/batch/tutorial/add')
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const tutorialId = row.tutorialId || this.ids
      this.$tab.openPage('修改教程', '/batch/tutorial/edit/' + tutorialId)
    },
    /** 状态切换 */
    handleStatusChange(row) {
      const text = row.status == "0" ? "上架" : "下架"
      this.$modal.confirm('确认要"' + text + '""' + row.tutorialTitle + '"教程吗？').then(function() {
        return changeTutorialStatus({ tutorialId: row.tutorialId, status: row.status })
      }).then(() => {
        this.$modal.msgSuccess(text + "成功")
        this.getList()
      }).catch(() => {
        row.status = row.status == "0" ? "1" : "0"
      })
    },
    /** 查看教程 */
    handlePreview(row) {
      this.previewData = row
      this.previewOpen = true
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const tutorialIds = row.tutorialId || this.ids
      this.$modal.confirm('是否确认删除教程编号为"' + tutorialIds + '"的数据项？').then(function() {
        return delTutorial(tutorialIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.$modal.confirm("是否确认导出所有教程数据？").then(() => {
        this.download('/batch/tutorial/export', this.queryParams, "教程数据.xlsx")
      }).catch(() => {})
    },
    /** 分类管理 */
    handleCategoryManage() {
      this.categoryDrawer = true
      this.getCategoryList()
    },
    /** 新增分类 */
    handleAddCategory() {
      this.$tab.openPage('新增分类', '/batch/tutorial/category/add')
    },
    /** 修改分类 */
    handleUpdateCategory(row) {
      this.$tab.openPage('修改分类', '/batch/tutorial/category/edit/' + row.categoryId)
    },
    /** 删除分类 */
    handleDeleteCategory(row) {
      this.$modal.confirm('是否确认删除分类"' + row.categoryName + '"？').then(function() {
        return delCategory(row.categoryId)
      }).then(() => {
        this.getCategoryList()
        this.getCategoryOptions()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>

<style scoped>
.tutorial-preview {
  max-height: 600px;
  overflow-y: auto;
  padding: 0 10px;
}
.preview-title {
  text-align: center;
  margin-bottom: 10px;
  font-size: 20px;
  font-weight: bold;
}
.preview-meta {
  text-align: center;
  color: #999;
  font-size: 13px;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #eee;
}
.preview-cover {
  text-align: center;
  margin-bottom: 20px;
}
.preview-video {
  margin-bottom: 20px;
}
.preview-intro {
  margin-bottom: 20px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 4px;
}
.preview-intro h4 {
  margin: 0 0 10px;
}
.preview-intro p {
  margin: 0;
  color: #666;
  line-height: 1.6;
}
.preview-content {
  line-height: 1.8;
  font-size: 14px;
  color: #333;
}
.preview-content img {
  max-width: 100%;
}
</style>
