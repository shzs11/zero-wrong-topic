<template>


  <el-tabs  v-model="activeName" @tab-click="handleClick" type="border-card"  stretch>
    <el-tab-pane  label="选择题" name="first">
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker v-model="queryParams.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
      <el-tag>标签一</el-tag>
      <el-tag type="success">标签二</el-tag>
      <el-tag type="info">标签三</el-tag>
      <el-tag type="warning">标签四</el-tag>
      <el-tag type="danger">标签五</el-tag>
    </el-form>

<!--     操作工具栏
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
    </el-row>-->

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="题目详情">
              <span>{{ props.row.selectionName }}</span>
            </el-form-item>
            <el-form-item label="选项A">
              <span>{{ props.row.optionsA }}</span>
            </el-form-item>
            <el-form-item label="选项B">
              <span>{{ props.row.optionsB }}</span>
            </el-form-item>
            <el-form-item label="选项C">
              <span>{{ props.row.optionsC }}</span>
            </el-form-item>
            <el-form-item label="选项D">
              <span>{{ props.row.optionsD }}</span>
            </el-form-item>
            <el-form-item label="科目">
              <span>{{ props.row.nameOfSubject }}</span>
            </el-form-item>
            <el-form-item label="知识点">
              <span>{{ props.row.nameOfKnowledge }}</span>
            </el-form-item>
            <el-form-item label="个人总结">
              <span>{{ props.row.summary }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column width="500px" label="题目" align="center" prop="selectionName" />
      <el-table-column label="错误答案" align="center" prop="selectionAnswer" >
        <template v-slot = "scope">
          <dict-tag :type = "DICT_TYPE.TOPIC_SELECTION" :value="scope.row.correctAnswer"></dict-tag>
        </template>
      </el-table-column>
      <el-table-column label="正确答案" align="center" prop="selectionAnswer" >
        <template v-slot = "scope">
          <dict-tag :type = "DICT_TYPE.TOPIC_SELECTION" :value="scope.row.selectionAnswer"></dict-tag>
        </template>
      </el-table-column>
      <el-table-column label="标签" align="center" prop="nameOfTags" />
      <el-table-column label="难度" align="center" prop="difficulty" >
        <template v-slot = "scope">
          <dict-tag :type = "DICT_TYPE.TOPIC_DIFFICULT" :value="scope.row.difficulty"></dict-tag>
        </template>
      </el-table-column>
      <el-table-column label="练习次数" align="center" prop="practiceCount" />
<!--      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>-->
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
    </el-tab-pane>
    <el-tab-pane label="配置管理" name="second">配置管理</el-tab-pane>
    <el-tab-pane label="角色管理" name="third">角色管理</el-tab-pane>
  </el-tabs>
</template>

<script>
import { createWrong, updateWrong, deleteWrong, getWrong, getWrongPage,getWrongPage2, exportWrongExcel } from "@/api/topic/wrong";

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
      //tabs标签
      activeName: 'first',
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
      this.queryParams.topicType = 0
      getWrongPage2(this.queryParams).then(response => {
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
      this.$modal.confirm('是否确认删除错题?').then(function() {
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
    },
    handleClick(tab, event) {
      //清空表单
     // this.form={}
      console.log(tab, event);
    }
  }
};
</script>

<style>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 180px;
  /*color: #99a9bf;*/
  padding-left: 90px;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 100%;
}
::v-deep .el-tabs__nav-scroll{
  width: 50%;
  margin: 0 auto;
}

</style>
