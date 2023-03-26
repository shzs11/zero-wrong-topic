package edu.gdpu.zero.module.topic.controller.admin.interlocution.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 问答题 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InterlocutionRespVO extends InterlocutionBaseVO {

    @Schema(description = "问答题标识", required = true, example = "28640")
    private Long id;

    @Schema(description = "是否错题", required = true)
    private Byte isWrong;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

    //翻译
    private String nameOfSubject;

    private String nameOfKnowledge;

    private String nameOfTag;

}
