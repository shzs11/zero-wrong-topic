package edu.gdpu.zero.module.topic.controller.admin.wrong.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 错题关联 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WrongRespVO extends WrongBaseVO {

    @Schema(description = "错题号", required = true, example = "25475")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

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
     * 参考答案
     */
    private String selectionAnswer;
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
    /**
     * 解析
     */
    private Long commentId;

    private String nameOfSubject;

    private String nameOfKnowledge;

    private String nameOfTags;

}
