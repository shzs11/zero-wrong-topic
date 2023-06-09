package edu.gdpu.zero.module.system.mq.message.auth;

import edu.gdpu.zero.framework.mq.core.pubsub.AbstractChannelMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OAuth 2.0 客户端的数据刷新 Message
 *
 * @author 芋道源码
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OAuth2ClientRefreshMessage extends AbstractChannelMessage {

    @Override
    public String getChannel() {
        return "system.oauth2-client.refresh";
    }

}
