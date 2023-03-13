package edu.gdpu.zero.module.topic.controller.admin.message.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import edu.gdpu.zero.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static edu.gdpu.zero.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 错题分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MessagePageReqVO extends PageParam {

    @Schema(description = "错题题目", example = "张三")
    private String name;

    @Schema(description = "用户id", example = "978")
    private Long userId;

    @Schema(description = "错题所有标签")
    private String tags;

    @Schema(description = "科目编号", example = "14257")
    private Long subjectId;

    @Schema(description = "是否公开")
    private Byte isPublic;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
