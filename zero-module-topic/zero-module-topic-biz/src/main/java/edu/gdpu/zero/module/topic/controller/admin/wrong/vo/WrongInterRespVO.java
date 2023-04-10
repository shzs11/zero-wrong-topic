package edu.gdpu.zero.module.topic.controller.admin.wrong.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @BelongsProject: zero-wrong-topic
 * @BelongsPackage: edu.gdpu.zero.module.topic.controller.admin.wrong.vo
 * @Author: zero
 * @Date: 2023/4/10 0:47
 * @Description: TODO
 */
@Schema(description = "管理后台 - 问答错题关联 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WrongInterRespVO  extends WrongBaseVO{

    @Schema(description = "错题号", required = true, example = "25475")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;


    /**
     * 选择题目
     */
    private String interName;
    /**
     * 参考答案
     */
    private String interAnswer;
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
