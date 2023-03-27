package edu.gdpu.zero.module.topic.dal.dataobject.wrong;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import edu.gdpu.zero.framework.mybatis.core.dataobject.BaseDO;

/**
 * 错题关联 DO
 *
 * @author zero
 */
@TableName("topic_wrong")
@KeySequence("topic_wrong_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WrongDO extends BaseDO {

    /**
     * 错题号
     */
    @TableId
    private Long id;
    /**
     * 题目编号
     */
    private Long topicId;
    /**
     * 错误答案
     */
    private String correctAnswer;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 题目类型
     */
    private Long topicType;
    /**
     * 个人总结
     */
    private String summary;
    /**
     * 练习次数
     */
    private Integer practiceCount;

}
