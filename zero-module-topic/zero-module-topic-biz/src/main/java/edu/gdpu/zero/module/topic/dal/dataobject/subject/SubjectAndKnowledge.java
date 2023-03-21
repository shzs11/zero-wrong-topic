package edu.gdpu.zero.module.topic.dal.dataobject.subject;


import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import edu.gdpu.zero.framework.mybatis.core.dataobject.BaseDO;
import edu.gdpu.zero.module.topic.dal.dataobject.knowledge.KnowledgeDO;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: zero-wrong-topic
 * @BelongsPackage: edu.gdpu.zero.module.topic.dal.dataobject.subject
 * @Author: zero
 * @Date: 2023/3/20 14:53
 * @Description: TODO
 */
@TableName("topic_subject")
@KeySequence("topic_subject_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectAndKnowledge implements Serializable {

    private Long id;

    private String name;

    private List<KnowledgeDO> knowledgeDOList;

}
