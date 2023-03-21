package edu.gdpu.zero.module.topic.dal.dataobject.subject;


import lombok.*;

/**
 * @BelongsProject: zero-wrong-topic
 * @BelongsPackage: edu.gdpu.zero.module.topic.dal.dataobject.subject
 * @Author: zero
 * @Date: 2023/3/20 15:25
 * @Description: TODO
 */
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeOne {

    private Long knowledgeId;

    private String knowledgeName;

}
