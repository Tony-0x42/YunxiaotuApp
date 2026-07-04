<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="联系人" prop="contactName">
        <el-input
          v-model="queryParams.contactName"
          placeholder="请输入联系人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="contactType">
        <el-select v-model="queryParams.contactType" placeholder="全部" clearable style="width: 120px">
          <el-option
            v-for="item in contactTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
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
          v-hasPermi="['batch:contact:add']"
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
          v-hasPermi="['batch:contact:edit']"
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
          v-hasPermi="['batch:contact:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contactList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" align="center" width="60" />
      <el-table-column label="联系人" align="center" prop="contactName" :show-overflow-tooltip="true" />
      <el-table-column label="区域/说明" align="center" prop="region" :show-overflow-tooltip="true" />
      <el-table-column label="电话" align="center" prop="phone" width="130" />
      <el-table-column label="类型" align="center" prop="contactType" width="110">
        <template slot-scope="scope">
          <el-tag :type="typeStyle(scope.row.contactType)" size="small">{{ typeText(scope.row.contactType) }}</el-tag>
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
            v-hasPermi="['batch:contact:edit']"
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
            v-hasPermi="['batch:contact:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['batch:contact:remove']"
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
import { listContact, delContact, changeContactStatus } from "@/api/batch/contact"

export default {
  name: "BatchContact",
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
      // 联系方式表格数据
      contactList: [],
      // 完整数据（前端分页）
      allContactList: [],
      // 联系类型选项
      contactTypeOptions: [
        { value: "1", label: "在线客服" },
        { value: "2", label: "总部热线" },
        { value: "3", label: "区域联系" }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contactName: undefined,
        phone: undefined,
        contactType: undefined,
        status: undefined
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询联系方式列表 */
    getList() {
      this.loading = true
      listContact(this.queryParams).then(response => {
        this.allContactList = (response.data || []).map(row => {
          return {
            ...row,
            status: String(row.status),
            contactType: String(row.contactType)
          }
        })
        this.total = this.allContactList.length
        this.sliceList()
        this.loading = false
      })
    },
    /** 前端分页切片 */
    sliceList() {
      const start = (this.queryParams.pageNum - 1) * this.queryParams.pageSize
      const end = start + this.queryParams.pageSize
      this.contactList = this.allContactList.slice(start, end)
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
      this.ids = selection.map(item => item.contactId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$tab.openPage("新增联系方式", "/batch/contact/add")
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const contactId = row.contactId || this.ids[0]
      this.$tab.openPage("修改联系方式", "/batch/contact/edit/" + contactId)
    },
    /** 状态切换 */
    handleStatusChange(row) {
      const text = row.status == "0" ? "启用" : "禁用"
      this.$modal.confirm('确认要"' + text + '""' + row.contactName + '"联系方式吗？').then(function() {
        return changeContactStatus({ contactId: row.contactId, status: row.status })
      }).then(() => {
        this.$modal.msgSuccess(text + "成功")
        this.getList()
      }).catch(() => {
        row.status = row.status == "0" ? "1" : "0"
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const contactIds = row.contactId || this.ids
      this.$modal.confirm('是否确认删除联系方式编号为"' + contactIds + '"的数据项？').then(function() {
        return delContact(contactIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 类型文本 */
    typeText(type) {
      const item = this.contactTypeOptions.find(item => item.value === String(type))
      return item ? item.label : type
    },
    /** 类型样式 */
    typeStyle(type) {
      switch (String(type)) {
        case "1": return "success"
        case "2": return "primary"
        case "3": return "warning"
        default: return ""
      }
    }
  }
}
</script>
