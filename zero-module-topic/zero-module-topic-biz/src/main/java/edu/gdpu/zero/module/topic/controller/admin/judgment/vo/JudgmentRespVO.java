package edu.gdpu.zero.module.topic.controller.admin.judgment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 判断题 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class JudgmentRespVO extends JudgmentBaseVO {

    @Schema(description = "判断题标识", required = true, example = "29156")
    private Long id;

    @Schema(description = "是否错题", required = true)
    private Byte isWrong;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

    private String nameOfSubject;

    private String nameOfKnowledge;

    private String nameOfTag;

}
