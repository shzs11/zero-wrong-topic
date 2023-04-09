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

    /**
     * 选择题目
     */
    private String selectionName;
    /**
     * 题目描述
     */
    private String description;
    /**
     * 选项A
     */
    private String optionsA;
    /**
     * 选项B
     */
    private String optionsB;
    /**
     * 选项C
     */
    private String optionsC;
    /**
     * 选项D
     */
    private String optionsD;
    /**
     * 选择题参考答案
     */
    private String selectionAnswer;

    //判断题选择和答案
    private String judgeName;

    private String judgeAnswer;
    /**
     * 错题标签
     */
    private String tags;
    /**
     * 科目编号
     */
    private Long subjectId;
    /**
     * 知识点
     */
    private Long knowledgeId;
    /**
     * 难度
     */
    private Long difficulty;




}
