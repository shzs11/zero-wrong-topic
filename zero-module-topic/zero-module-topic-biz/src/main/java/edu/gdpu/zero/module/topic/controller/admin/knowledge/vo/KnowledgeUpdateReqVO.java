package edu.gdpu.zero.module.topic.controller.admin.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 知识点更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class KnowledgeUpdateReqVO extends KnowledgeBaseVO {

    @Schema(description = "知识点编号", required = true, example = "20188")
    @NotNull(message = "知识点编号不能为空")
    private Long id;

}
