package edu.gdpu.zero.module.topic.controller.admin.selection.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 选择题 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SelectionRespVO extends SelectionBaseVO {

    @Schema(description = "选择题标识", required = true, example = "14178")
    private Long id;

    @Schema(description = "解析", required = true, example = "14186")
    private Long commentId;

    @Schema(description = "是否错题", required = true)
    private Byte isWrong;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}
