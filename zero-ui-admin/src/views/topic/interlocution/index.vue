<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="题目标签" prop="tags">
        <el-select v-model="queryParams.tags" placeholder="请选择题目标签" clearable size="small">
          <el-option v-for="tag in this.tags"
                     :key="tag.id" :label="tag.name" :value="tag.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="科目编号" prop="subjectId">
        <el-select v-model="queryParams.subjectId" placeholder="请选择科目" clearable size="small"
                   @change="subjectLevelOneChanged(queryParams.subjectId)">
          <el-option v-for="subject in this.subjectOne"
                     :key="subject.id" :label="subject.name" :value="subject.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="知识点" prop="knowledgeId">
        <el-select v-model="queryParams.knowledgeId" placeholder="请选择">
          <el-option
            v-for="knowledge in knowledgeTwo"
            :key="knowledge.id"
            :label="knowledge.name"
            :value="knowledge.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="难度" prop="difficulty">
        <el-select v-model="queryParams.difficulty" placeholder="请选择难度" clearable size="small">
          <el-option v-for="dict in this.getDictDatas(DICT_TYPE.TOPIC_DIFFICULT)"
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
                   v-hasPermi="['topic:interlocution:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['topic:interlocution:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="问答题标识" align="center" prop="id" />
      <el-table-column label="题目" align="center" prop="content" />
      <el-table-column label="正确答案" align="center" prop="answer" />
      <el-table-column label="题目标签" align="center" prop="nameOfTag" />
      <el-table-column label="科目编号" align="center" prop="nameOfSubject" />
      <el-table-column label="是否错题" align="center" prop="isWrong" />
      <el-table-column label="知识点" align="center" prop="nameOfKnowledge" />
      <el-table-column label="难度" align="center" prop="difficulty" >
        <template v-slot = "scope">
          <dict-tag :type = "DICT_TYPE.TOPIC_DIFFICULT" :value="scope.row.difficulty"></dict-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['topic:interlocution:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['topic:interlocution:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" v-dialogDrag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="题目">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="正确答案">
          <editor v-model="form.answer" :min-height="192"/>
        </el-form-item>
        <el-form-item label="题目所有标签" prop="tags">
          <el-select v-model="form.tags" placeholder="请选择题目所有标签">
            <el-option v-for="tag in this.tags"
                       :key="tag.id" :label="tag.name" :value="tag.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="科目编号" prop="subjectId">
          <el-select v-model="form.subjectId" placeholder="请选择科目" clearable size="small"
                     @change="subjectLevelOneChanged(form.subjectId)">
            <el-option v-for="subject in this.subjectOne"
                       :key="subject.id" :label="subject.name" :value="subject.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="知识点" prop="knowledgeId">
          <el-select v-model="form.knowledgeId" placeholder="请选择">
            <el-option
              v-for="knowledge in knowledgeTwo"
              :key="knowledge.id"
              :label="knowledge.name"
              :value="knowledge.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="难度" prop="difficulty">
          <el-select v-model="form.difficulty" placeholder="请选择难度">
            <el-option v-for="dict in this.getDictDatas(DICT_TYPE.TOPIC_DIFFICULT)"
                       :key="dict.value" :label="dict.label" :value="dict.value"/>
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
import { createInterlocution, updateInterlocution, deleteInterlocution, getInterlocution, getInterlocutionPage, exportInterlocutionExcel } from "@/api/topic/interlocution";
import Editor from '@/components/Editor';
import { createSubject, updateSubject, deleteSubject, getSubject, getSubjectPage, exportSubjectExcel,getSubjectAndKnowledge} from "@/api/topic/subject";
import { createTag, updateTag, deleteTag, getTag, getTagPage, exportTagExcel } from "@/api/topic/tag";

export default {
  name: "Interlocution",
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
      // 问答题列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        content: null,
        tags: null,
        subjectId: null,
        isWrong: null,
        knowledgeId: null,
        difficulty: null,
        createTime: [],
      },
      // 表单参数
      form: {},
      //标签
      tags:{},
      //课程和知识点的级联
      subjectOne:[],
      knowledgeTwo:{},
      // 表单校验
      rules: {
        content: [{ required: true, message: "题目不能为空", trigger: "blur" }],
        answer: [{ required: true, message: "正确答案不能为空", trigger: "blur" }],
        tags: [{ required: true, message: "题目所有标签不能为空", trigger: "change" }],
        subjectId: [{ required: true, message: "科目编号不能为空", trigger: "change" }],
        knowledgeId: [{ required: true, message: "知识点不能为空", trigger: "change" }],
        difficulty: [{ required: true, message: "难度不能为空", trigger: "change" }],
      }
    };
  },
  created() {
    this.getList();
    this.getTag();
    this.getKnowledge();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 执行查询
      getInterlocutionPage(this.queryParams).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 查询标签*/
    getTag(){
      var qeryParams = {
        page:"1",
        size:"20"
      }
      getTagPage(qeryParams).then(response=>{
        this.tags = response.data.list;
        console.log(this.tags)
      })
    },
    /** 查询知识点**/
    getKnowledge(){
      getSubjectAndKnowledge().then(response=>{
        this.knowledge = response.data;
        this.subjectOne = response.data;
        console.log(this.subjectOne)
      })

    },
    subjectLevelOneChanged(value){
      console.log(value)
      for(let i = 0; i < this.subjectOne.length;i++){
        if(this.subjectOne[i].id === value){
          this.knowledgeTwo = this.subjectOne[i].knowledgeDOList;
          this.queryParams.knowledgeId = ""
          this.form.knowledgeId = ""
        }
      }
      console.log(this.knowledgeTwo)

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
        content: undefined,
        answer: undefined,
        tags: undefined,
        subjectId: undefined,
        knowledgeId: undefined,
        difficulty: undefined,
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
      this.title = "添加问答题";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getInterlocution(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改问答题";
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
          updateInterlocution(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createInterlocution(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除问答题编号为"' + id + '"的数据项?').then(function() {
          return deleteInterlocution(id);
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
      this.$modal.confirm('是否确认导出所有问答题数据项?').then(() => {
          this.exportLoading = true;
          return exportInterlocutionExcel(params);
        }).then(response => {
          this.$download.excel(response, '问答题.xls');
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
