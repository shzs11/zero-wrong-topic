package edu.gdpu.zero.module.topic.controller.admin.interlocution.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 问答题 Excel VO
 *
 * @author zero
 */
@Data
public class InterlocutionExcelVO {

    @ExcelProperty("问答题标识")
    private Long id;

    @ExcelProperty("题目")
    private String content;

    @ExcelProperty("正确答案")
    private String answer;

    @ExcelProperty("题目所有标签")
    private String tags;

    @ExcelProperty("科目编号")
    private Long subjectId;

    @ExcelProperty("是否错题")
    private Byte isWrong;

    @ExcelProperty("知识点")
    private Long knowledgeId;

    @ExcelProperty("难度")
    private Long difficulty;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
