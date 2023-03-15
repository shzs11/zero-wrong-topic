package edu.gdpu.zero.module.topic.controller.admin.tag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 标签 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TagRespVO extends TagBaseVO {

    @Schema(description = "标签号", required = true, example = "26348")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}
