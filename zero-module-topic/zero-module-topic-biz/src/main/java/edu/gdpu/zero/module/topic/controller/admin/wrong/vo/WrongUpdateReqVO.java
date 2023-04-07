package edu.gdpu.zero.module.topic.controller.admin.wrong.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 错题关联更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WrongUpdateReqVO extends WrongBaseVO {

    @Schema(description = "错题号", required = true, example = "25475")
    @NotNull(message = "错题号不能为空")
    private Long id;

    private String selectionName;


}
