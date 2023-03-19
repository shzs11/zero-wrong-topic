package edu.gdpu.zero.module.topic.controller.admin.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 知识点 Excel VO
 *
 * @author zero
 */
@Data
public class KnowledgeExcelVO {

    @ExcelProperty("知识点编号")
    private Long id;

    @ExcelProperty("科目标识")
    private Long subjectId;

    @ExcelProperty("知识点名称")
    private String name;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
