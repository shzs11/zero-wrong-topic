package edu.gdpu.zero.module.topic.controller.admin.wrong.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 错题关联创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WrongCreateReqVO extends WrongBaseVO {


    @Schema(description = "选项A", required = true)
    private String optionsA;

    @Schema(description = "选项B", required = true)
    private String optionsB;

    @Schema(description = "选项C", required = true)
    private String optionsC;

    @Schema(description = "选项D", required = true)
    private String optionsD;

    @Schema(description = "选择题正确答案", required = true)
    private String selectionAnswer;

    @Schema(description = "选择题详情",required = true)
    private String selectionName;

    @Schema(description = "判断题详情",required = true)
    private String judgementName;

    @Schema(description = "问答题详情",required = true)
    private String content;

    @Schema(description = "判断题错题答案", required = true)
    private String judgeCorrectAnswer;

    @Schema(description = "判断题正确答案", required = true)
    private String judgeAnswer;

    @Schema(description = "问答题正确答案",required = true)
    private String interAnswer;

    @Schema(description = "问答题错误答案",required = true)
    private String interCorrectAnswer;

    private String tags;

    private Long subjectId;

    private Long knowledgeId;

    private Long difficulty;

}
