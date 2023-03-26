package edu.gdpu.zero.module.topic.dal.dataobject.interlocution;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import edu.gdpu.zero.framework.mybatis.core.dataobject.BaseDO;

/**
 * 问答题 DO
 *
 * @author zero
 */
@TableName("topic_interlocution")
@KeySequence("topic_interlocution_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterlocutionDO extends BaseDO {

    /**
     * 问答题标识
     */
    @TableId
    private Long id;
    /**
     * 题目
     */
    private String content;
    /**
     * 描述
     */
    private String description;
    /**
     * 正确答案
     */
    private String answer;
    /**
     * 题目所有标签
     */
    private String tags;
    /**
     * 科目编号
     */
    private Long subjectId;
    /**
     * 是否错题
     */
    private Byte isWrong;
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

}
