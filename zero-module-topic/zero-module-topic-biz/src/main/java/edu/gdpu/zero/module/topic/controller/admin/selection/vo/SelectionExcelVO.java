package edu.gdpu.zero.module.topic.controller.admin.selection.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 选择题 Excel VO
 *
 * @author zero
 */
@Data
public class SelectionExcelVO {

    @ExcelProperty("选择题标识")
    private Long id;

    @ExcelProperty("题目")
    private String name;

    @ExcelProperty("错题标签")
    private String tags;

    @ExcelProperty("科目编号")
    private Long subjectId;

    @ExcelProperty("知识点")
    private Long knowledgeId;

    @ExcelProperty("难度")
    private Long difficulty;

    @ExcelProperty("解析")
    private Long commentId;

    @ExcelProperty("是否错题")
    private Byte isWrong;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
