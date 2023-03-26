package edu.gdpu.zero.module.topic.controller.admin.interlocution.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 问答题更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InterlocutionUpdateReqVO extends InterlocutionBaseVO {

    @Schema(description = "问答题标识", required = true, example = "28640")
    @NotNull(message = "问答题标识不能为空")
    private Long id;

}
