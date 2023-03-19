package edu.gdpu.zero.module.topic.controller.admin.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
* 知识点 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class KnowledgeBaseVO {

    @Schema(description = "科目标识", required = true, example = "3503")
    @NotNull(message = "科目标识不能为空")
    private Long subjectId;

    @Schema(description = "知识点名称", required = true, example = "芋艿")
    @NotNull(message = "知识点名称不能为空")
    private String name;

    @Schema(description = "描述", example = "你猜")
    private String description;

}
