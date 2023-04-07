<template>
<!--  <el-menu :default-active="this.$route.path"  class="el-menu-demo" mode="horizontal" @select="handleSelect">
&lt;!&ndash;    <el-menu-item @click="toSelection()" >选择题</el-menu-item>&ndash;&gt;
    <el-menu-item index="2">判断题</el-menu-item>
    <el-menu-item index="3">简答题</el-menu-item>
  </el-menu>-->

  <el-tabs v-model="activeName" @tab-click="handleClick" type="border-card"  stretch>
    <el-tab-pane label="选择题" name="first">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="题目" prop="name">
          <el-input style="width: 300px"  type="textarea" v-model="form.selectionName" placeholder="请输入题目" />
        </el-form-item>
        <el-form-item label="选项A" prop="optionsA">
          <el-input style="width: 300px" type="textarea" v-model="form.optionsA" placeholder="请输入选项A" />
        </el-form-item>
        <el-form-item label="选项B" prop="optionsB">
          <el-input style="width: 300px" v-model="form.optionsB" type="textarea" placeholder="请输入选项B" />
        </el-form-item>
        <el-form-item label="选项C" prop="optionsC">
          <el-input style="width: 300px" v-model="form.optionsC" type="textarea" placeholder="请输入选项C" />
        </el-form-item>
        <el-form-item label="选项D" prop="optionsD">
          <el-input style="width: 300px" v-model="form.optionsD" type="textarea" placeholder="请输入选项D" />
        </el-form-item>
        <el-form-item label="你的答案" prop="answer">
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
          <el-select v-model="form.tags" placeholder="请选择标签">
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
          <el-input  style="width: 300px" v-model="form.summary" type="textarea" placeholder="请输入个人总结" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="submitForm">保存</el-button>
            <el-button @click="cancel">取 消</el-button>
        </el-form-item>
      </el-form>
    </el-tab-pane>
    <el-tab-pane label="判断题" name="second">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="题目" prop="name">
          <el-input style="width: 300px" type="textarea" v-model="form.judgementName" placeholder="请输入题目" />
        </el-form-item>
        <el-form-item label="你的答案" prop="answer">
          <el-radio-group v-model="form.judgeCorrectAnswer">
            <el-radio :label="item.value" :key="item.value"
                      v-for="item in this.getDictDatas(DICT_TYPE.TOPIC_JUGEMENT)">{{item.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="正确答案" prop="answer">
          <el-radio-group v-model="form.judgeAnswer">
            <el-radio :label="item.value" :key="item.value"
                      v-for="item in this.getDictDatas(DICT_TYPE.TOPIC_JUGEMENT)">{{item.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="错题标签" prop="tags">
          <el-select v-model="form.tags" placeholder="请选择错题标签">
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
        <el-form-item label="个人总结" prop="summary">
          <el-input  style="width: 300px" v-model="form.summary" type="textarea" placeholder="请输入个人总结" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitJudgeForm">保存</el-button>
          <el-button @click="cancel">取 消</el-button>
        </el-form-item>
      </el-form>
    </el-tab-pane>

    <el-tab-pane label="问答题" name="third">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="题目">
          <editor style="width: 400px" v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="错误答案">
          <editor style="width: 400px"  v-model="form.interCorrectAnswer" :min-height="192"/>
        </el-form-item>
        <el-form-item label="正确答案">
          <editor style="width: 400px"  v-model="form.interAnswer" :min-height="192"/>
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
        <el-form-item label="个人总结" prop="summary">
          <el-input  style="width: 300px" v-model="form.summary" type="textarea" placeholder="请输入个人总结" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitContentForm">保存</el-button>
          <el-button @click="cancel">取 消</el-button>
        </el-form-item>
      </el-form>
    </el-tab-pane>
  </el-tabs>

</template>

<script>
import ElementForm from "@/components/bpmnProcessDesigner/package/penal/form/ElementForm";
import { createSelection, updateSelection, deleteSelection, getSelection, getSelectionPage, exportSelectionExcel } from "@/api/topic/selection";
import { createSubject, updateSubject, deleteSubject, getSubject, getSubjectPage, exportSubjectExcel,getSubjectAndKnowledge} from "@/api/topic/subject";
import { createTag, updateTag, deleteTag, getTag, getTagPage, exportTagExcel } from "@/api/topic/tag";
import { createWrong, updateWrong, deleteWrong, getWrong, getWrongPage, exportWrongExcel } from "@/api/topic/wrong";
import Editor from '@/components/Editor';

export default {
  name: "tap",
  components: {
    ElementForm,
    Editor,
  },
  data() {
    return {
      activeIndex: '1',
      activeIndex2: '1',
      activeName: 'first',
      form:{
        judgeAnswer:undefined
      },
      //科目
      subject:{},
      //标签
      tags:{},
      //课程和知识点的级联
      subjectOne:[],
      knowledgeTwo:{},
      // 表单校验
      rules: {
       /* topicId: [{ required: true, message: "题目编号不能为空", trigger: "blur" }],*/
        correctAnswer: [{ required: true, message: "错误答案不能为空", trigger: "blur" }],
        /*userId: [{ required: true, message: "用户id不能为空", trigger: "blur" }],*/
        topicType: [{ required: true, message: "题目类型不能为空", trigger: "change" }],
        summary: [{ required: true, message: "个人总结不能为空", trigger: "blur" }],
       /* practiceCount: [{ required: true, message: "练习次数不能为空", trigger: "blur" }],*/
      }

    };
  },
  created() {
    this.getKnowledge();
    this.getTag();
  },
  methods: {
    //提交选择题
    submitForm(){

      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        // 添加的提交
        this.form.topicType="0"
        createWrong(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.form = {};
          this.getList();
        });

      });
    },
    //提交判断题
    submitJudgeForm(){

      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        // 添加的提交
        //设置题目类型
        this.form.topicType="1"
        createWrong(this.form).then(response => {
          this.$modal.msgSuccess("判断题新增成功");
          this.open = false;
          this.form = {};
          this.getList();
        });

      });
    },
    //提交问答题
    submitContentForm(){

      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        // 添加的提交
        //设置题目类型
        this.form.topicType="2"
        createWrong(this.form).then(response => {
          this.$modal.msgSuccess("判断题新增成功");
          this.open = false;
          //清空表单
          this.form = {};
          this.getList();
        });

      });
    },
    cancel(){

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
        this.tags = response.data.list;
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
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
      console.log(this.$route.path);
    },
    toSelection(){
      console.log("123")
      this.$router.push({path:"/selection"});
    },
    handleClick(tab, event) {
      //清空表单
      this.form={}
      console.log(tab, event);
    }
  }
}
</script>

<style scoped>

</style>
