<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="题目" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入题目" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="错题标签" prop="tags">
        <el-select v-model="queryParams.tags" placeholder="请选择标签" clearable size="small">
          <el-option v-for="tag in this.tag"
                     :key="tag.id" :label="tag.name" :value="tag.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="科目" prop="subjectId">
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
        <el-select v-model="queryParams.difficulty" placeholder="题目难度" clearable size="small">
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
                   v-hasPermi="['topic:selection:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['topic:selection:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="选择题标识" align="center" prop="id" />
      <el-table-column label="题目" align="center" prop="name" />
      <el-table-column label="错题标签" align="center" prop="nameOfTag" />
      <el-table-column label="科目" align="center" prop="nameOfSubject" />
      <el-table-column label="知识点" align="center" prop="nameOfKnowledge" />
      <el-table-column label="难度" align="center" prop="difficulty" >
        <template v-slot = "scope">
          <dict-tag :type = "DICT_TYPE.TOPIC_DIFFICULT" :value="scope.row.difficulty"></dict-tag>
        </template>
      </el-table-column>
      <el-table-column label="是否错题" align="center" prop="isWrong" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['topic:selection:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['topic:selection:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" v-dialogDrag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="题目" prop="name">
          <el-input v-model="form.name" placeholder="请输入题目" />
        </el-form-item>
        <el-form-item label="选项A" prop="optionsA">
          <el-input v-model="form.optionsA" placeholder="请输入选项A" />
        </el-form-item>
        <el-form-item label="选项B" prop="optionsB">
          <el-input v-model="form.optionsB" placeholder="请输入选项B" />
        </el-form-item>
        <el-form-item label="选项C" prop="optionsC">
          <el-input v-model="form.optionsC" placeholder="请输入选项C" />
        </el-form-item>
        <el-form-item label="选项D" prop="optionsD">
          <el-input v-model="form.optionsD" placeholder="请输入选项D" />
        </el-form-item>
        <el-form-item label="参考答案" prop="answer">
          <el-radio-group v-model="form.answer">
            <el-radio :label="item.value" :key="item.value"
                      v-for="item in this.getDictDatas(DICT_TYPE.TOPIC_SELECTION)">{{item.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="标签" prop="tags">
          <el-select v-model="form.tags" placeholder="请选择标签">
            <el-option v-for="tag in this.tag"
                       :key="tag.id" :label="tag.name" :value="tag.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="科目" prop="subjectId">
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
          <el-select v-model="form.difficulty" placeholder="题目难度" clearable size="small">
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
import { createSelection, updateSelection, deleteSelection, getSelection, getSelectionPage, exportSelectionExcel } from "@/api/topic/selection";
import { createSubject, updateSubject, deleteSubject, getSubject, getSubjectPage, exportSubjectExcel,getSubjectAndKnowledge} from "@/api/topic/subject";
import { createTag, updateTag, deleteTag, getTag, getTagPage, exportTagExcel } from "@/api/topic/tag";

export default {
  name: "Selection",
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
      // 选择题列表
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
        tags: null,
        subjectId: null,
        knowledgeId: undefined,
        difficulty: null,
        createTime: [],
      },
      // 表单参数
      form: {},
      //科目
      subject:{},
      //标签
      tag:{},
      //课程和知识点的级联
      subjectOne:[],
      knowledgeTwo:{},
      // 表单校验
      rules: {
        name: [{ required: true, message: "题目不能为空", trigger: "blur" }],
        optionsA: [{ required: true, message: "选项A不能为空", trigger: "blur" }],
        optionsB: [{ required: true, message: "选项B不能为空", trigger: "blur" }],
        optionsC: [{ required: true, message: "选项C不能为空", trigger: "blur" }],
        optionsD: [{ required: true, message: "选项D不能为空", trigger: "blur" }],
        answer: [{ required: true, message: "参考答案不能为空", trigger: "blur" }],
        tags: [{ required: true, message: "错题标签不能为空", trigger: "change" }],
        subjectId: [{ required: true, message: "科目编号不能为空", trigger: "change" }],
        knowledgeId: [{ required: true, message: "知识点不能为空", trigger: "change" }],
        difficulty: [{ required: true, message: "难度不能为空", trigger: "blur" }],
      }
    };
  },
  created() {
    this.getList();
    this.getKnowledge();
    this.getSubject();
    this.getTag();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 执行查询
      getSelectionPage(this.queryParams).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 查询知识点**/
    getKnowledge(){
      getSubjectAndKnowledge().then(response=>{
        this.knowledge = response.data;
        this.subjectOne = response.data;
        console.log(this.subjectOne)
      })

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
        console.log(this.tags)
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
        name: undefined,
        optionsA: undefined,
        optionsB: undefined,
        optionsC: undefined,
        optionsD: undefined,
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
      this.title = "添加选择题";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getSelection(id).then(response => {
        this.form = response.data;
        console.log("修改"+this.form)
        this.open = true;
        this.title = "修改选择题";
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
          updateSelection(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createSelection(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除选择题编号为"' + id + '"的数据项?').then(function() {
          return deleteSelection(id);
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
      this.$modal.confirm('是否确认导出所有选择题数据项?').then(() => {
          this.exportLoading = true;
          return exportSelectionExcel(params);
        }).then(response => {
          this.$download.excel(response, '选择题.xls');
          this.exportLoading = false;
        }).catch(() => {});
    },
    handleChange(value) {
      console.log(value);
      this.queryParams.knowledgeId = this.knowledgevalue[1]
      this.form.knowledgeId = this.knowledgevalue[1]
    }
  }
};
</script>
