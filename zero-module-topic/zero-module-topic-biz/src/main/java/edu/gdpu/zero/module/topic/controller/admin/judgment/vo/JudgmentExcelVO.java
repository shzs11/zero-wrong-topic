package edu.gdpu.zero.module.topic.controller.admin.judgment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 判断题 Excel VO
 *
 * @author zero
 */
@Data
public class JudgmentExcelVO {

    @ExcelProperty("判断题标识")
    private Long id;

    @ExcelProperty("题目")
    private String name;

    @ExcelProperty("参考答案：对/错")
    private String answer;

    @ExcelProperty("错题标签")
    private String tags;

    @ExcelProperty("科目编号")
    private Long subjectId;

    @ExcelProperty("知识点")
    private Long knowledgeId;

    @ExcelProperty("难度")
    private Long difficulty;

    @ExcelProperty("是否错题")
    private Byte isWrong;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
