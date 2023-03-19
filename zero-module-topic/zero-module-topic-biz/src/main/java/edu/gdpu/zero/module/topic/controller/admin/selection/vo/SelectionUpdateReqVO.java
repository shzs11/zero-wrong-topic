package edu.gdpu.zero.module.topic.controller.admin.selection.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 选择题更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SelectionUpdateReqVO extends SelectionBaseVO {

    @Schema(description = "选项A", required = true)
    @NotNull(message = "选项A不能为空")
    private String optionsA;

    @Schema(description = "选项B", required = true)
    @NotNull(message = "选项B不能为空")
    private String optionsB;

    @Schema(description = "选项C", required = true)
    @NotNull(message = "选项C不能为空")
    private String optionsC;

    @Schema(description = "选项D", required = true)
    @NotNull(message = "选项D不能为空")
    private String optionsD;

    @Schema(description = "参考答案", required = true)
    @NotNull(message = "参考答案不能为空")
    private String answer;

}
