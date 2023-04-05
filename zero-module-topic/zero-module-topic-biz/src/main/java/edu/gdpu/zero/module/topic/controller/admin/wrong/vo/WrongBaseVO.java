package edu.gdpu.zero.module.topic.controller.admin.wrong.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
* 错题关联 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class WrongBaseVO {

    @Schema(description = "题目编号", required = true, example = "10638")
    private Long topicId;

    @Schema(description = "选择题错误答案", required = true)
    private String correctAnswer;

    @Schema(description = "用户id", required = true, example = "12089")
    private Long userId;

    @Schema(description = "题目类型", required = true, example = "2")
    @NotNull(message = "题目类型不能为空")
    private Long topicType;

    @Schema(description = "个人总结", required = true)
    @NotNull(message = "个人总结不能为空")
    private String summary;

    @Schema(description = "练习次数", required = true, example = "28729")
    private Integer practiceCount;

}
