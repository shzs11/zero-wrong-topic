package edu.gdpu.zero.module.topic.controller.admin.judgment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
* 判断题 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class JudgmentBaseVO {

    @Schema(description = "题目", required = true, example = "王五")
    @NotNull(message = "题目不能为空")
    private String name;

    @Schema(description = "参考答案：对/错", required = true)
    @NotNull(message = "参考答案：对/错不能为空")
    private String answer;

    @Schema(description = "错题标签", required = true)
    @NotNull(message = "错题标签不能为空")
    private String tags;

    @Schema(description = "科目编号", required = true, example = "22309")
    @NotNull(message = "科目编号不能为空")
    private Long subjectId;

    @Schema(description = "知识点", required = true, example = "6686")
    @NotNull(message = "知识点不能为空")
    private Long knowledgeId;

    @Schema(description = "难度", required = true)
    @NotNull(message = "难度不能为空")
    private Long difficulty;

}
