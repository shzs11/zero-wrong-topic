package edu.gdpu.zero.module.topic.dal.dataobject.judgment;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import edu.gdpu.zero.framework.mybatis.core.dataobject.BaseDO;

/**
 * 判断题 DO
 *
 * @author zero
 */
@TableName("topic_judgment")
@KeySequence("topic_judgment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgmentDO extends BaseDO {

    /**
     * 判断题标识
     */
    @TableId
    private Long id;
    /**
     * 题目
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 参考答案：对/错
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
     * 是否错题
     */
    private Byte isWrong;

}
