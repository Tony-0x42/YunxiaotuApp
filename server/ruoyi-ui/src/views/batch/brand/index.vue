<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="品牌名称" prop="brandName">
        <el-input
          v-model="queryParams.brandName"
          placeholder="请输入品牌名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="全部" clearable style="width: 100px">
          <el-option label="启用" value="0" />
          <el-option label="禁用" value="1" />
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
          v-hasPermi="['batch:brand:add']"
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
          v-hasPermi="['batch:brand:edit']"
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
          v-hasPermi="['batch:brand:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="brandList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" align="center" width="60" />
      <el-table-column label="品牌名称" align="center" prop="brandName" :show-overflow-tooltip="true" />
      <el-table-column label="品牌LOGO" align="center" prop="logoUrl" width="120">
        <template slot-scope="scope">
          <el-image
            v-if="scope.row.logoUrl"
            :src="scope.row.logoUrl"
            :preview-src-list="[scope.row.logoUrl]"
            style="width: 50px; height: 50px; border-radius: 4px"
            fit="cover"
          />
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="排序权重" align="center" prop="sortWeight" width="90" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
            v-hasPermi="['batch:brand:edit']"
          />
          <span style="margin-left: 5px">{{ scope.row.status == '0' ? '启用' : '禁用' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['batch:brand:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['batch:brand:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="handlePageChange"
    />
  </div>
</template>

<script>
import { listBrand, delBrand, changeBrandStatus } from "@/api/batch/brand"

export default {
  name: "BatchBrand",
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
      // 品牌专区表格数据
      brandList: [],
      // 完整数据（前端分页）
      allBrandList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        brandName: undefined,
        status: undefined
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询品牌专区列表 */
    getList() {
      this.loading = true
      listBrand(this.queryParams).then(response => {
        this.allBrandList = (response.data || []).map(row => {
          return {
            ...row,
            status: String(row.status)
          }
        })
        this.total = this.allBrandList.length
        this.sliceList()
        this.loading = false
      })
    },
    /** 前端分页切片 */
    sliceList() {
      const start = (this.queryParams.pageNum - 1) * this.queryParams.pageSize
      const end = start + this.queryParams.pageSize
      this.brandList = this.allBrandList.slice(start, end)
    },
    /** 分页变化 */
    handlePageChange() {
      this.sliceList()
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
      this.ids = selection.map(item => item.brandId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$tab.openPage("新增品牌专区", "/batch/brand/add")
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const brandId = row.brandId || this.ids[0]
      this.$tab.openPage("修改品牌专区", "/batch/brand/edit/" + brandId)
    },
    /** 状态切换 */
    handleStatusChange(row) {
      const text = row.status == "0" ? "启用" : "禁用"
      this.$modal.confirm('确认要"' + text + '""' + row.brandName + '"品牌专区吗？').then(function() {
        return changeBrandStatus({ brandId: row.brandId, status: row.status })
      }).then(() => {
        this.$modal.msgSuccess(text + "成功")
        this.getList()
      }).catch(() => {
        row.status = row.status == "0" ? "1" : "0"
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const brandIds = row.brandId || this.ids
      this.$modal.confirm('是否确认删除品牌专区编号为"' + brandIds + '"的数据项？').then(function() {
        return delBrand(brandIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>
