package edu.gdpu.zero.module.topic.controller.admin.tag.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import edu.gdpu.zero.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static edu.gdpu.zero.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 标签 Excel 导出 Request VO，参数和 TagPageReqVO 是一致的")
@Data
public class TagExportReqVO {

    @Schema(description = "标签名称", example = "粗心")
    private String name;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
