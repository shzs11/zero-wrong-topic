package edu.gdpu.zero.module.topic.controller.admin.tag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 标签 Excel VO
 *
 * @author zero
 */
@Data
public class TagExcelVO {

    @ExcelProperty("标签号")
    private Long id;

    @ExcelProperty("标签名称")
    private String name;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
