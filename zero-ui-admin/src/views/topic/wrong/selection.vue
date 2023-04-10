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
          <el-input v-model="form.selectionName" placeholder="请输入题目" />
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
        <el-form-item label="错误答案" prop="answer">
          <el-radio-group v-model="form.correctAnswer">
            <el-radio :label="item.value" :key="item.value"
                      v-for="item in this.getDictDatas(DICT_TYPE.TOPIC_SELECTION)">{{item.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="正确答案" prop="answer">
          <el-radio-group v-model="form.selectionAnswer">
            <el-radio :label="item.value" :key="item.value"
                      v-for="item in this.getDictDatas(DICT_TYPE.TOPIC_SELECTION)">{{item.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="标签" prop="tags">
          <el-select v-model="form.tags" placeholder="请选择标签"   @change="bindSelectChange">
            <el-option v-for="tag in this.tags"
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
        <el-form-item label="个人总结" prop="summary">
          <el-input  style="width: 300px" v-model="form.summary" type="textarea" placeholder="个人总结" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
    </el-tab-pane>
    <el-tab-pane label="判断题" name="second">
      <div class="app-container">

      <!-- 搜索工作栏 -->
      <el-form :model="queryParams2" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="创建时间" prop="createTime">
          <el-date-picker v-model="queryParams2.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleQuery2">搜索</el-button>
          <el-button icon="el-icon-refresh" @click="resetQuery2">重置</el-button>
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
      <el-table v-loading="loading" :data="judgeList">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="题目详情">
                <span>{{ props.row.judgeName }}</span>
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
        <el-table-column width="500px" label="题目" align="center" prop="judgeName" />
        <el-table-column label="正确答案" align="center" prop="selectionAnswer" >
          <template v-slot = "scope">
            <dict-tag :type = "DICT_TYPE.TOPIC_JUGEMENT" :value="scope.row.judgeAnswer"></dict-tag>
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
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleJudgeUpdate(scope.row)"
                       v-hasPermi="['topic:wrong:update']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleJudgeDelete(scope.row)"
                       v-hasPermi="['topic:wrong:delete']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页组件 -->
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams2.pageNo" :limit.sync="queryParams2.pageSize"
                  @pagination="getJudgeList"/>

      <!-- 对话框(添加 / 修改) -->
      <el-dialog :title="title" :visible.sync="open1" width="500px" v-dialogDrag append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="题目" prop="name">
            <el-input v-model="form.judgeName" placeholder="请输入题目" />
          </el-form-item>
          <el-form-item label="答案" prop="answer">
            <el-radio-group v-model="form.judgeAnswer">
              <el-radio :label="item.value" :key="item.value"
                        v-for="item in this.getDictDatas(DICT_TYPE.TOPIC_JUGEMENT)">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="标签" prop="tags">
            <el-select v-model="form.tags" placeholder="请选择标签"   @change="bindSelectChange">
              <el-option v-for="tag in this.tags"
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
          <el-form-item label="个人总结" prop="summary">
            <el-input  style="width: 300px" v-model="form.summary" type="textarea" placeholder="个人总结" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitJudgeForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div></el-tab-pane>
    <el-tab-pane label="问答题" name="third">
      <div class="app-container">

        <!-- 搜索工作栏 -->
        <el-form :model="queryParams3" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="创建时间" prop="createTime">
            <el-date-picker v-model="queryParams3.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                            range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="handleQuery3">搜索</el-button>
            <el-button icon="el-icon-refresh" @click="resetQuery3">重置</el-button>
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
        <el-table v-loading="loading" :data="interList">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="题目详情">
                  <div v-html="props.row.interName" style="height:auto"></div>
                </el-form-item>
                <el-form-item label="错误答案">
                  <div v-html="props.row.correctAnswer" style="height:auto"></div>
                </el-form-item>
                <el-form-item label="正确答案">
                  <div v-html="props.row.interAnswer" style="height:auto"></div>
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
          <el-table-column width="500px" label="题目" align="center" prop="interName" >
            <template slot-scope="scope">
              <div v-html="scope.row.interName"></div>
            </template>
          </el-table-column>
          <el-table-column width="500px" label="正确答案" align="center" prop="interAnswer"  >
            <template slot-scope="scope">
              <div v-html="scope.row.interAnswer"></div>
            </template>
          </el-table-column>
          <el-table-column label="标签" align="center" prop="nameOfTags" />
          <el-table-column label="难度" align="center" prop="difficulty" >
            <template v-slot = "scope">
              <dict-tag :type = "DICT_TYPE.TOPIC_DIFFICULT" :value="scope.row.difficulty"></dict-tag>
            </template>
          </el-table-column>
<!--                <el-table-column label="创建时间" align="center" prop="createTime" width="180">
                  <template v-slot="scope">
                    <span>{{ parseTime(scope.row.createTime) }}</span>
                  </template>
                </el-table-column>-->
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template v-slot="scope">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleJudgeUpdate(scope.row)"
                         v-hasPermi="['topic:wrong:update']">修改</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleJudgeDelete(scope.row)"
                         v-hasPermi="['topic:wrong:delete']">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页组件 -->
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams3.pageNo" :limit.sync="queryParams3.pageSize"
                    @pagination="getJudgeList"/>

        <!-- 对话框(添加 / 修改) -->
        <el-dialog :title="title" :visible.sync="open1" width="500px" v-dialogDrag append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="题目" prop="name">
              <el-input v-model="form.judgeName" placeholder="请输入题目" />
            </el-form-item>
            <el-form-item label="答案" prop="answer">
              <el-radio-group v-model="form.judgeAnswer">
                <el-radio :label="item.value" :key="item.value"
                          v-for="item in this.getDictDatas(DICT_TYPE.TOPIC_JUGEMENT)">{{item.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="标签" prop="tags">
              <el-select v-model="form.tags" placeholder="请选择标签"   @change="bindSelectChange">
                <el-option v-for="tag in this.tags"
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
            <el-form-item label="个人总结" prop="summary">
              <el-input  style="width: 300px" v-model="form.summary" type="textarea" placeholder="个人总结" />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitJudgeForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>
      </div>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import { createWrong, updateWrong, deleteWrong,deleteJudgeWrong,getWrong,getJudgeWrong, getWrongPage,getWrongPage2,getWrongPage3,getWrongPage4, exportWrongExcel } from "@/api/topic/wrong";
import { createTag, updateTag, deleteTag, getTag, getTagPage, exportTagExcel } from "@/api/topic/tag";
import { createSubject, updateSubject, deleteSubject, getSubject, getSubjectPage, exportSubjectExcel,getSubjectAndKnowledge} from "@/api/topic/subject";

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
      // 选择题错题关联列表
      list: [],
      //判断题错题列表
      judgeList:[],
      //问答题错题列表
      interList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //是否显示判断题
      open1:false,
      //tabs标签
      activeName: 'first',
      // 选择题查询参数
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
      // 判断题查询参数
      queryParams2: {
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

      // 问答题查询参数
      queryParams3: {
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
      // 选择表单参数
      form: {},
      //判断表单数据
      form2:{},
      //标签
      tags: {},
      //课程和知识点的级联
      subjectOne:[],
      knowledgeTwo:{},

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
    this.getTag();
    this.getKnowledge();
    this.getJudgeList();
    this.getInterList();
  },
  methods: {
    /** 查询选择题列表 */
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
    /** 查询判断题题列表 */
    getJudgeList() {
      this.loading = true;
      // 执行查询
        // this.queryParams.topicType = 0
      getWrongPage3(this.queryParams2).then(response => {
        this.judgeList = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 查询判断题题列表 */
    getInterList() {
      this.loading = true;
      // 执行查询
      // this.queryParams.topicType = 0
      getWrongPage4(this.queryParams3).then(response => {
        this.interList = response.data.list;
        for(let i =0;i<this.interList.length;i++)
        {
          this.interList[i].interName = this.interList[i].interName.replace(/<img/g, "<img style='max-width:40%;height:auto;'");
          this.interList[i].interAnswer = this.interList[i].interAnswer.replace(/<img/g, "<img style='max-width:40%;height:auto;'");
          this.interList[i].correctAnswer = this.interList[i].interAnswer.replace(/<img/g, "<img style='max-width:40%;height:auto;'");
        }
        this.total = response.data.total;
        this.loading = false;
      });
    },
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
       // this.knowledge = response.data;
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
    bindSelectChange: function (e) {
      this.form.tags = e;
    },

    /** 取消按钮 */
    cancel() {
      this.open1 = false;
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
    /** 搜索按钮操作 */
    handleQuery2() {
      this.queryParams2.pageNo = 1;
      this.getJudgeList();
    },
    /** 重置按钮操作 */
    resetQuery2() {
      this.resetForm("queryForm2");
      this.handleQuery2();
    },
    /** 搜索按钮操作 */
    handleQuery3() {
      this.queryParams3.pageNo = 1;
      this.getInterList();
    },
    /** 重置按钮操作 */
    resetQuery3() {
      this.resetForm("queryForm3");
      this.handleQuery3();
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
        this.form.tags = Number(response.data.tags)
        this.form.difficulty = response.data.difficulty.toString()
        //更新知识点框
        for(let i = 0; i < this.subjectOne.length;i++){
          if(this.subjectOne[i].id === response.data.subjectId){
            this.knowledgeTwo = this.subjectOne[i].knowledgeDOList;
            this.queryParams.knowledgeId = ""
          }
        }
        this.form.knowledgeId = response.data.knowledgeId
        this.open = true;
        this.title = "修改错题关联";
      });
    },

    /** 修改按钮操作 */
    handleJudgeUpdate(row) {
      this.reset();
      const id = row.id;
      getJudgeWrong(id).then(response => {

        this.form = response.data;
        this.form.tags = Number(response.data.tags)
        this.form.difficulty = response.data.difficulty.toString()
        //更新知识点框
        for(let i = 0; i < this.subjectOne.length;i++){
          if(this.subjectOne[i].id === response.data.subjectId){
            this.knowledgeTwo = this.subjectOne[i].knowledgeDOList;
            this.queryParams.knowledgeId = ""
          }
        }
        this.form.knowledgeId = response.data.knowledgeId
        this.open1 = true;
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
          this.form.topicType = 0;
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
    /** 提交判断题按钮 */
    submitJudgeForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        // 修改的提交
        if (this.form.id != null) {
          this.form.topicType = 1;
          updateWrong(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open1 = false;
            this.getJudgeList();
          });
          return;
        }
        // 添加的提交
        createWrong(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open1 = false;
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
        this.getJudgeList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },

    /** 删除按钮操作 */
    handleJudgeDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除错题?').then(function() {
        return deleteJudgeWrong(id);
      }).then(() => {
        this.getJudgeList();
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
      //清空数据

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

/*::v-deep img{
  width:50%;
  height: auto;
}*/

</style>
