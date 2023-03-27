<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="题目编号" prop="topicId">
        <el-input v-model="queryParams.topicId" placeholder="请输入题目编号" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="错误答案" prop="correctAnswer">
        <el-input v-model="queryParams.correctAnswer" placeholder="请输入错误答案" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="用户id" prop="userId">
        <el-input v-model="queryParams.userId" placeholder="请输入用户id" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="题目类型" prop="topicType">
        <el-select v-model="queryParams.topicType" placeholder="请选择题目类型" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="个人总结" prop="summary">
        <el-input v-model="queryParams.summary" placeholder="请输入个人总结" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="练习次数" prop="practiceCount">
        <el-input v-model="queryParams.practiceCount" placeholder="请输入练习次数" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker v-model="queryParams.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['topic:wrong:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['topic:wrong:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="错题号" align="center" prop="id" />
      <el-table-column label="题目编号" align="center" prop="topicId" />
      <el-table-column label="错误答案" align="center" prop="correctAnswer" />
      <el-table-column label="用户id" align="center" prop="userId" />
      <el-table-column label="题目类型" align="center" prop="topicType" />
      <el-table-column label="个人总结" align="center" prop="summary" />
      <el-table-column label="练习次数" align="center" prop="practiceCount" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['topic:wrong:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['topic:wrong:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" v-dialogDrag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="题目编号" prop="topicId">
          <el-input v-model="form.topicId" placeholder="请输入题目编号" />
        </el-form-item>
        <el-form-item label="错误答案" prop="correctAnswer">
          <el-input v-model="form.correctAnswer" placeholder="请输入错误答案" />
        </el-form-item>
        <el-form-item label="用户id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户id" />
        </el-form-item>
        <el-form-item label="题目类型" prop="topicType">
          <el-select v-model="form.topicType" placeholder="请选择题目类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="个人总结" prop="summary">
          <el-input v-model="form.summary" placeholder="请输入个人总结" />
        </el-form-item>
        <el-form-item label="练习次数" prop="practiceCount">
          <el-input v-model="form.practiceCount" placeholder="请输入练习次数" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { createWrong, updateWrong, deleteWrong, getWrong, getWrongPage, exportWrongExcel } from "@/api/topic/wrong";

export default {
  name: "Wrong",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 错题关联列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        topicId: null,
        correctAnswer: null,
        userId: null,
        topicType: null,
        summary: null,
        practiceCount: null,
        createTime: [],
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        topicId: [{ required: true, message: "题目编号不能为空", trigger: "blur" }],
        correctAnswer: [{ required: true, message: "错误答案不能为空", trigger: "blur" }],
        userId: [{ required: true, message: "用户id不能为空", trigger: "blur" }],
        topicType: [{ required: true, message: "题目类型不能为空", trigger: "change" }],
        summary: [{ required: true, message: "个人总结不能为空", trigger: "blur" }],
        practiceCount: [{ required: true, message: "练习次数不能为空", trigger: "blur" }],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 执行查询
      getWrongPage(this.queryParams).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 表单重置 */
    reset() {
      this.form = {
        id: undefined,
        topicId: undefined,
        correctAnswer: undefined,
        userId: undefined,
        topicType: undefined,
        summary: undefined,
        practiceCount: undefined,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加错题关联";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getWrong(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改错题关联";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        // 修改的提交
        if (this.form.id != null) {
          updateWrong(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createWrong(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除错题关联编号为"' + id + '"的数据项?').then(function() {
          return deleteWrong(id);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      // 处理查询参数
      let params = {...this.queryParams};
      params.pageNo = undefined;
      params.pageSize = undefined;
      this.$modal.confirm('是否确认导出所有错题关联数据项?').then(() => {
          this.exportLoading = true;
          return exportWrongExcel(params);
        }).then(response => {
          this.$download.excel(response, '错题关联.xls');
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
