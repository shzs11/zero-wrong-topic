package edu.gdpu.zero.module.topic.controller.admin.wrong.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import edu.gdpu.zero.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static edu.gdpu.zero.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 错题关联 Excel 导出 Request VO，参数和 WrongPageReqVO 是一致的")
@Data
public class WrongExportReqVO {

    @Schema(description = "题目编号", example = "10638")
    private Long topicId;

    @Schema(description = "错误答案")
    private String correctAnswer;

    @Schema(description = "用户id", example = "12089")
    private Long userId;

    @Schema(description = "题目类型", example = "2")
    private Long topicType;

    @Schema(description = "个人总结")
    private String summary;

    @Schema(description = "练习次数", example = "28729")
    private Integer practiceCount;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
