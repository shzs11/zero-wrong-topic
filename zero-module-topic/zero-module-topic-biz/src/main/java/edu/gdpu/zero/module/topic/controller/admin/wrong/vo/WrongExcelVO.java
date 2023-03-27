package edu.gdpu.zero.module.topic.controller.admin.wrong.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 错题关联 Excel VO
 *
 * @author zero
 */
@Data
public class WrongExcelVO {

    @ExcelProperty("错题号")
    private Long id;

    @ExcelProperty("题目编号")
    private Long topicId;

    @ExcelProperty("错误答案")
    private String correctAnswer;

    @ExcelProperty("用户id")
    private Long userId;

    @ExcelProperty("题目类型")
    private Long topicType;

    @ExcelProperty("个人总结")
    private String summary;

    @ExcelProperty("练习次数")
    private Integer practiceCount;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
