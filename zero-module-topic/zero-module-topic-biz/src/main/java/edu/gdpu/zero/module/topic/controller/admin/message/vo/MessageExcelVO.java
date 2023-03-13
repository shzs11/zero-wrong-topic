package edu.gdpu.zero.module.topic.controller.admin.message.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import edu.gdpu.zero.framework.excel.core.annotations.DictFormat;
import edu.gdpu.zero.framework.excel.core.convert.DictConvert;


/**
 * 错题 Excel VO
 *
 * @author zero
 */
@Data
public class MessageExcelVO {

    @ExcelProperty("错题号")
    private Long id;

    @ExcelProperty("错题题目")
    private String name;

    @ExcelProperty("用户id")
    private Long userId;

    @ExcelProperty("科目编号")
    private Long subjectId;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
