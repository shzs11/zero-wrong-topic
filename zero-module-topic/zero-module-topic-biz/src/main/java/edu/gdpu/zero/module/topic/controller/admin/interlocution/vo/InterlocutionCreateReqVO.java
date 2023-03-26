package edu.gdpu.zero.module.topic.controller.admin.interlocution.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 问答题创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InterlocutionCreateReqVO extends InterlocutionBaseVO {

}
