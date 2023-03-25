package edu.gdpu.zero.module.topic.controller.admin.judgment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 判断题更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class JudgmentUpdateReqVO extends JudgmentBaseVO {

    @Schema(description = "判断题标识", required = true, example = "29156")
    @NotNull(message = "判断题标识不能为空")
    private Long id;

    @Schema(description = "是否错题", required = true)
    @NotNull(message = "是否错题不能为空")
    private Byte isWrong;

}
