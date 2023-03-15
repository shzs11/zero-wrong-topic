package edu.gdpu.zero.module.topic.controller.admin.tag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 标签更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TagUpdateReqVO extends TagBaseVO {

    @Schema(description = "标签号", required = true, example = "26348")
    @NotNull(message = "标签号不能为空")
    private Long id;

}
