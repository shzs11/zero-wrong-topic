package edu.gdpu.zero.module.system.controller.admin.mail.vo.account;

import edu.gdpu.zero.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 邮箱账号分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MailAccountPageReqVO extends PageParam {

    @Schema(description = "邮箱", required = true, example = "zeroyuanma@123.com")
    private String mail;

    @Schema(description = "用户名" , required = true , example = "zero")
    private String username;

}
