package edu.gdpu.zero.module.topic.controller.admin.judgment.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 判断题创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class JudgmentCreateReqVO extends JudgmentBaseVO {

}
