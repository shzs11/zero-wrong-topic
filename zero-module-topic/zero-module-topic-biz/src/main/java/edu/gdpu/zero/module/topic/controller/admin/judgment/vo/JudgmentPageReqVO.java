package edu.gdpu.zero.module.topic.controller.admin.judgment.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import edu.gdpu.zero.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static edu.gdpu.zero.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 判断题分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class JudgmentPageReqVO extends PageParam {

    @Schema(description = "题目", example = "王五")
    private String name;

    @Schema(description = "参考答案：对/错")
    private String answer;

    @Schema(description = "错题标签")
    private String tags;

    @Schema(description = "科目编号", example = "22309")
    private Long subjectId;

    @Schema(description = "知识点", example = "6686")
    private Long knowledgeId;

    @Schema(description = "难度")
    private Long difficulty;

    @Schema(description = "是否错题")
    private Byte isWrong;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
