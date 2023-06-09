<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="错题题目" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入错题题目" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="用户id" prop="userId">
        <el-input v-model="queryParams.userId" placeholder="请输入用户id" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="错题所有标签" prop="tags">
        <el-select v-model="queryParams.tags" placeholder="请选择标签" clearable size="small">
          <el-option v-for="tag in this.tag"
                     :key="tag.id" :label="tag.name" :value="tag.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="科目" prop="subjectId">
        <el-select v-model="queryParams.subjectId" placeholder="请输入科目" clearable size="small">
          <el-option v-for="subject in this.subject"
                     :key="subject.id" :label="subject.name" :value="subject.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="是否公开" prop="isPublic">
        <el-select v-model="queryParams.isPublic" placeholder="请选择是否公开" clearable size="small">
          <el-option v-for="dict in this.getDictDatas(DICT_TYPE.TOPIC_PUBLIC)"
                       :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
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
                   v-hasPermi="['topic:message:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['topic:message:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="错题号" align="center" prop="id" />
      <el-table-column label="错题题目" align="center" prop="name" />
      <el-table-column label="用户" align="center" prop="nameOfUser" />
      <el-table-column label="科目" align="center" prop="nameOfSubject" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['topic:message:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['topic:message:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="800px" v-dialogDrag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="错题题目" prop="name">
          <el-input v-model="form.name" placeholder="请输入错题题目" />
        </el-form-item>
        <el-form-item label="题目描述">
          <editor v-model="form.description" :min-height="192"/>
        </el-form-item>
        <el-form-item label="错题错误答案">
          <editor v-model="form.originalAnswer" :min-height="192"/>
        </el-form-item>
        <el-form-item label="错题正确答案">
          <editor v-model="form.correctAnswer" :min-height="192"/>
        </el-form-item>
        <el-form-item label="标签" prop="tags">
          <el-select v-model="form.tags" placeholder="请选择标签" clearable size="small">
            <el-option v-for="tag in this.tag"
                       :key="tag.id" :label="tag.name" :value="tag.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="科目" prop="subjectId">
          <el-select v-model="form.subjectId" placeholder="请选择科目" clearable size="small">
            <el-option v-for="subject in this.subject"
                       :key="subject.id" :label="subject.name" :value="subject.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="是否发布" prop="isPublic">
          <el-select v-model="form.isPublic" placeholder="请选择是否公开">
            <el-option v-for="dict in this.getDictDatas(DICT_TYPE.TOPIC_PUBLIC)"
                       :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
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
import { createMessage, updateMessage, deleteMessage, getMessage, getMessagePage, exportMessageExcel } from "@/api/topic/message";
import { createSubject, updateSubject, deleteSubject, getSubject, getSubjectPage, exportSubjectExcel } from "@/api/topic/subject";
import Editor from '@/components/Editor';
import { createTag, updateTag, deleteTag, getTag, getTagPage, exportTagExcel } from "@/api/topic/tag";

export default {
  name: "Message",
  components: {
    Editor,
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
      // 错题列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        name: null,
        userId: null,
        tags: null,
        subjectId: null,
        isPublic: null,
        createTime: [],
      },
      //科目
      subject:{

      },
      tag:{},
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [{ required: true, message: "错题题目不能为空", trigger: "blur" }],
        description: [{ required: true, message: "题目描述不能为空", trigger: "blur" }],
        originalAnswer: [{ required: true, message: "错题错误答案不能为空", trigger: "blur" }],
        correctAnswer: [{ required: true, message: "错题正确答案不能为空", trigger: "blur" }],
        tags: [{ required: true, message: "错题所有标签不能为空", trigger: "blur" }],
        subjectId: [{ required: true, message: "科目编号不能为空", trigger: "blur" }],
        isPublic: [{ required: true, message: "是否公开不能为空", trigger: "change" }],
      }
    };
  },
  created() {
    this.getList();
    this.getSubject();
    this.getTag()
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 执行查询
      getMessagePage(this.queryParams).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /**查询科目*/
    getSubject(){
      var qeryParams ={
        page:"1",
        size:"20",
      }
      getSubjectPage(qeryParams).then(response=>{
        this.subject = response.data.list;
        console.log(this.subject)
      })

    },
    /** 查询标签*/
    getTag(){
      var qeryParams = {
        page:"1",
        size:"20"
      }
      getTagPage(qeryParams).then(response=>{
        this.tag = response.data.list;
        console.log(this.tag)
      })
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 表单重置 */
    reset() {
      this.form = {
        name: undefined,
        description: undefined,
        originalAnswer: undefined,
        correctAnswer: undefined,
        userId: undefined,
        tags: undefined,
        subjectId: undefined,
        isPublic: undefined,
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
      this.title = "添加错题";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getMessage(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改错题";
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
          updateMessage(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createMessage(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除错题编号为"' + id + '"的数据项?').then(function() {
          return deleteMessage(id);
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
      this.$modal.confirm('是否确认导出所有错题数据项?').then(() => {
          this.exportLoading = true;
          return exportMessageExcel(params);
        }).then(response => {
          this.$download.excel(response, '错题.xls');
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
