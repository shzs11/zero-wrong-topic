package edu.gdpu.zero.module.system.mq.producer.permission;

import edu.gdpu.zero.module.system.mq.message.permission.RoleMenuRefreshMessage;
import edu.gdpu.zero.framework.mq.core.RedisMQTemplate;
import edu.gdpu.zero.module.system.mq.message.permission.UserRoleRefreshMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Permission 权限相关消息的 Producer
 */
@Component
public class PermissionProducer {

    @Resource
    private RedisMQTemplate redisMQTemplate;

    /**
     * 发送 {@link RoleMenuRefreshMessage} 消息
     */
    public void sendRoleMenuRefreshMessage() {
        RoleMenuRefreshMessage message = new RoleMenuRefreshMessage();
        redisMQTemplate.send(message);
    }

    /**
     * 发送 {@link UserRoleRefreshMessage} 消息
     */
    public void sendUserRoleRefreshMessage() {
        UserRoleRefreshMessage message = new UserRoleRefreshMessage();
        redisMQTemplate.send(message);
    }

}
