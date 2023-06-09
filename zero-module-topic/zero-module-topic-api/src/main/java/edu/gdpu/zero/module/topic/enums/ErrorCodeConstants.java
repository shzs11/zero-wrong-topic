package edu.gdpu.zero.module.topic.enums;

import edu.gdpu.zero.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {

    // ========== 科目 TODO 补充编号 ==========
    ErrorCode SUBJECT_NOT_EXISTS = new ErrorCode(100000000, "科目不存在");



    // ========== 错题 TODO 补充编号 ==========
    ErrorCode MESSAGE_NOT_EXISTS = new ErrorCode(100000002, "错题不存在");

    // ========== 标签 TODO 补充编号 ==========
    ErrorCode TAG_NOT_EXISTS = new ErrorCode(1000000003, "标签不存在");

    ErrorCode SELECTION_NOT_EXISTS = new ErrorCode(1000000004, "选择题不存在");

    ErrorCode KNOWLEDGE_NOT_EXISTS = new ErrorCode(1000000005, "知识点不存在");

    // ========== 判断题 TODO 补充编号 ==========
    ErrorCode JUDGMENT_NOT_EXISTS = new ErrorCode(1000000006, "判断题不存在");

    // ========== 问答题 TODO 补充编号 ==========
    ErrorCode INTERLOCUTION_NOT_EXISTS = new ErrorCode(1000000007, "问答题不存在");

    // ========== 错题关联 TODO 补充编号 ==========
    ErrorCode WRONG_NOT_EXISTS = new ErrorCode(1000000008, "错题关联不存在");





}
