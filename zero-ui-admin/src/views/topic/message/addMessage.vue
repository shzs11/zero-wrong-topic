<template>
  <div>
    <el-row :gutter="20" >
      <el-form ref="message" :model="formData" :rules="rules" size="medium" label-width="100px"
               label-position="center">
        <el-col :span="12">
          <el-form-item label="错题题目" prop="name">
            <el-input v-model="formData.name" placeholder="请输入题目" :maxlength="50" show-word-limit
                      clearable prefix-icon='el-icon-mobile' :style="{width: '100%'}"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="13">
          <el-form-item label="错题描述" prop="descrption">
            <editor v-model="formData.descrption" placeholder="请输入错题描述" :height="350"></editor>
          </el-form-item>
        </el-col>
        <el-col :span="13">
          <el-form-item label="错题原答案" prop="originalAnswer">
            <editor v-model="formData.originalAnswer" placeholder="请输入错题原答案" :height="350">
            </editor>
          </el-form-item>
        </el-col>
        <el-col :span="13">
          <el-form-item label="错题正确答案" prop="correctAnswer">
            <editor v-model="formData.correctAnswer" placeholder="请输入错题正确答案" :height="350"></editor>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="科目" prop="subjectId">
            <el-select v-model="formData.subjectId" placeholder="请选择科目" clearable size="small">
              <el-option v-for="subject in this.subject"
                         :key="subject.id" :label="subject.name" :value="subject.id"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="标签" prop="tags">
            <el-checkbox-group v-model="formData.tags" size="medium">
              <el-checkbox v-for="(item, index) in tagsOptions" :key="index" :label="item.value"
                           :disabled="item.disabled">{{item.label}}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="是否公开" prop="isPublic">
            <el-select v-model="formData.isPublic" placeholder="请选择是否公开">
              <el-option v-for="dict in this.getDictDatas(DICT_TYPE.TOPIC_PUBLIC)"
                         :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item size="large">
            <el-button type="primary" @click="submitForm">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
  </div>
</template>
<script>

import { createMessage, updateMessage, deleteMessage, getMessage, getMessagePage, exportMessageExcel } from "@/api/topic/message";
import { createSubject, updateSubject, deleteSubject, getSubject, getSubjectPage, exportSubjectExcel } from "@/api/topic/subject"
import Editor from '@/components/Editor';
export default {
  components: {Editor},
  props: [],
  data() {
    return {
      formData: {
        name: undefined,
        descrption: null,
        originalAnswer: null,
        correctAnswer: null,
        subjectId: undefined,
        tags: [],
        isPublic: undefined,
      },
      subject:{

      },
      rules: {
        name: [{
          required: true,
          message: '请输入题目',
          trigger: 'blur'
        }],
        descrption: [{
          required: true,
          message: '请输入错题描述',
          trigger: 'blur'
        }],
        originalAnswer: [{
          required: true,
          message: '请输入错题原答案',
          trigger: 'blur'
        }],
        correctAnswer: [{
          required: true,
          message: '请输入错题正确答案',
          trigger: 'blur'
        }],
        subjectId: [{
          required: true,
          message: '请选择科目',
          trigger: 'change'
        }],
        tags: [],
        isPublic: [{
          required: true,
          message: '是否公开不能为空',
          trigger: 'change'
        }],
      },
      tagsOptions: [{
        "label": "选项一",
        "value": 1
      }, {
        "label": "选项二",
        "value": 2
      }],
      isPublicOptions: [{
        "label": "是",
        "value": 1
      }, {
        "label": "否",
        "value": 0
      }],
    }
  },
  computed: {},
  watch: {},
  created() {
    this.getSubject()
  },
  mounted() {},
  methods: {
    submitForm() {
      this.$refs['message'].validate(valid => {
        if (!valid) return
        // TODO 提交表单
      })
    },
    resetForm() {
      this.$refs['message'].resetFields()
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
  }
}

</script>
<style>
</style>
