package edu.gdpu.zero.module.topic.controller.admin.message.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 错题 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MessageRespVO extends MessageBaseVO {

    @Schema(description = "错题号", required = true, example = "19034")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

    private String nameOfUser;

    private String nameOfSubject;


}
