package edu.gdpu.zero.module.topic.controller.admin.message.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 错题创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MessageCreateReqVO extends MessageBaseVO {

    @Schema(description = "题目描述", required = true, example = "随便")
    @NotNull(message = "题目描述不能为空")
    private String description;

    @Schema(description = "错题错误答案", required = true)
    @NotNull(message = "错题错误答案不能为空")
    private String originalAnswer;

    @Schema(description = "错题正确答案", required = true)
    @NotNull(message = "错题正确答案不能为空")
    private String correctAnswer;

    @Schema(description = "错题所有标签", required = true)
    @NotNull(message = "错题所有标签不能为空")
    private String tags;

    @Schema(description = "是否公开", required = true)
    @NotNull(message = "是否公开不能为空")
    private Byte isPublic;

}
