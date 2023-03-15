package edu.gdpu.zero.module.topic.dal.dataobject.tag;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import edu.gdpu.zero.framework.mybatis.core.dataobject.BaseDO;

/**
 * 标签 DO
 *
 * @author zero
 */
@TableName("topic_tag")
@KeySequence("topic_tag_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDO extends BaseDO {

    /**
     * 标签号
     */
    @TableId
    private Long id;
    /**
     * 标签名称
     */
    private String name;

}
