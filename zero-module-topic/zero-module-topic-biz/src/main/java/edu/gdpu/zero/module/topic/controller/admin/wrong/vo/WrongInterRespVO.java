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
@Schema(description = "�����̨ - �ʴ������� Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WrongInterRespVO  extends WrongBaseVO{

    @Schema(description = "�����", required = true, example = "25475")
    private Long id;

    @Schema(description = "����ʱ��", required = true)
    private LocalDateTime createTime;


    /**
     * ѡ����Ŀ
     */
    private String interName;
    /**
     * �ο���
     */
    private String interAnswer;
    /**
     * �����ǩ
     */
    private String tags;
    /**
     * ��Ŀ���
     */
    private Long subjectId;
    /**
     * ֪ʶ��
     */
    private Long knowledgeId;
    /**
     * �Ѷ�
     */

    private Long difficulty;
    /**
     * ����
     */
    private Long commentId;

    private String nameOfSubject;

    private String nameOfKnowledge;

    private String nameOfTags;


}
