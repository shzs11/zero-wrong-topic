package edu.gdpu.zero.module.topic.dal.dataobject.selection;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import edu.gdpu.zero.framework.mybatis.core.dataobject.BaseDO;

/**
 * 选择题 DO
 *
 * @author zero
 */
@TableName("topic_selection")
@KeySequence("topic_selection_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectionDO extends BaseDO {

    /**
     * 选择题标识
     */
    @TableId
    private Long id;
    /**
     * 题目
     */
    private String name;
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
    private String answer;
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
    /**
     * 是否错题
     */
    private Byte isWrong;

}
