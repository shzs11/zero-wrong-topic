package edu.gdpu.zero.module.topic.controller.admin.subject.vo;


import edu.gdpu.zero.module.topic.dal.dataobject.knowledge.KnowledgeDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @BelongsProject: zero-wrong-topic
 * @BelongsPackage: edu.gdpu.zero.module.topic.controller.admin.subject.vo
 * @Author: zero
 * @Date: 2023/3/20 14:37
 * @Description: TODO
 */
@Schema(description = "管理后台 - 科目和知识点Response VO")
@Data
@ToString(callSuper = true)
public class SubjectAndKnowledgeVo{

    @Schema(description = "科目名称", required = true, example = "语文")
    @NotNull(message = "科目名称不能为空")
    private String SubjectName;

    private Long SubjectId;

    private List<KnowledgeDO> knowledgeDOList;



}
