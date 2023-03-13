package edu.gdpu.zero.module.topic.dal.dataobject.message;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import edu.gdpu.zero.framework.mybatis.core.dataobject.BaseDO;

/**
 * 错题 DO
 *
 * @author zero
 */
@TableName("topic_message")
@KeySequence("topic_message_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDO extends BaseDO {

    /**
     * 错题号
     */
    @TableId
    private Long id;
    /**
     * 错题题目
     */
    private String name;
    /**
     * 题目描述
     */
    private String description;
    /**
     * 错题错误答案
     */
    private String originalAnswer;
    /**
     * 错题正确答案
     */
    private String correctAnswer;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 错题所有标签
     */
    private String tags;
    /**
     * 科目编号
     */
    private Long subjectId;
    /**
     * 是否公开
     *
     * 枚举 {@link TODO topic_public 对应的类}
     */
    private Byte isPublic;

}
