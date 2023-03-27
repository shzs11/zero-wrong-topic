package edu.gdpu.zero.module.topic.controller.admin.wrong.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 错题关联 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WrongRespVO extends WrongBaseVO {

    @Schema(description = "错题号", required = true, example = "25475")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}
