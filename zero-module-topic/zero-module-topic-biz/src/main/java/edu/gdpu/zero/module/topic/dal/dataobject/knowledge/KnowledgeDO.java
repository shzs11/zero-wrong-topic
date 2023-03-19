package edu.gdpu.zero.module.topic.dal.dataobject.knowledge;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import edu.gdpu.zero.framework.mybatis.core.dataobject.BaseDO;

/**
 * 知识点 DO
 *
 * @author zero
 */
@TableName("topic_knowledge")
@KeySequence("topic_knowledge_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeDO extends BaseDO {

    /**
     * 知识点编号
     */
    @TableId
    private Long id;
    /**
     * 科目标识
     */
    private Long subjectId;
    /**
     * 知识点名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;

}
