package edu.gdpu.zero.module.topic.enums;

import edu.gdpu.zero.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {

    // ========== 科目 TODO 补充编号 ==========
    ErrorCode SUBJECT_NOT_EXISTS = new ErrorCode(100000000, "科目不存在");



    // ========== 错题 TODO 补充编号 ==========
    ErrorCode MESSAGE_NOT_EXISTS = new ErrorCode(100000002, "错题不存在");

    // ========== 标签 TODO 补充编号 ==========
    ErrorCode TAG_NOT_EXISTS = new ErrorCode(1000000003, "标签不存在");
}