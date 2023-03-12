package edu.gdpu.zero.module.member.convert.auth;

import edu.gdpu.zero.module.member.controller.app.auth.vo.*;
import edu.gdpu.zero.module.member.controller.app.social.vo.AppSocialUserUnbindReqVO;
import edu.gdpu.zero.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import edu.gdpu.zero.module.system.api.sms.dto.code.SmsCodeSendReqDTO;
import edu.gdpu.zero.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import edu.gdpu.zero.module.system.api.social.dto.SocialUserBindReqDTO;
import edu.gdpu.zero.module.system.api.social.dto.SocialUserUnbindReqDTO;
import edu.gdpu.zero.module.system.enums.sms.SmsSceneEnum;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    SocialUserBindReqDTO convert(Long userId, Integer userType, AppAuthSocialLoginReqVO reqVO);
    SocialUserUnbindReqDTO convert(Long userId, Integer userType, AppSocialUserUnbindReqVO reqVO);

    SmsCodeSendReqDTO convert(AppAuthSmsSendReqVO reqVO);
    SmsCodeUseReqDTO convert(AppAuthResetPasswordReqVO reqVO, SmsSceneEnum scene, String usedIp);
    SmsCodeUseReqDTO convert(AppAuthSmsLoginReqVO reqVO, Integer scene, String usedIp);

    AppAuthLoginRespVO convert(OAuth2AccessTokenRespDTO bean);

}
