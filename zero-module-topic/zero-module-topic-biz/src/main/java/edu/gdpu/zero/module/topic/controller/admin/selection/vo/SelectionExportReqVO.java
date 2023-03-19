package edu.gdpu.zero.module.topic.controller.admin.selection.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import edu.gdpu.zero.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static edu.gdpu.zero.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 选择题 Excel 导出 Request VO，参数和 SelectionPageReqVO 是一致的")
@Data
public class SelectionExportReqVO {

    @Schema(description = "题目", example = "1")
    private String name;

    @Schema(description = "错题标签")
    private String tags;

    @Schema(description = "科目编号", example = "3899")
    private Long subjectId;

    @Schema(description = "知识点", example = "1493")
    private Long knowledgeId;

    @Schema(description = "难度")
    private Long difficulty;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
