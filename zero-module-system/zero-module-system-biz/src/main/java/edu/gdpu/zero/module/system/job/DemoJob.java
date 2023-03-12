package edu.gdpu.zero.module.system.job;

import edu.gdpu.zero.framework.quartz.core.handler.JobHandler;
import edu.gdpu.zero.framework.tenant.core.context.TenantContextHolder;
import edu.gdpu.zero.framework.tenant.core.job.TenantJob;
import edu.gdpu.zero.module.system.dal.dataobject.user.AdminUserDO;
import edu.gdpu.zero.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@TenantJob // 标记多租户
public class DemoJob implements JobHandler {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public String execute(String param) throws Exception {
        System.out.println("当前租户：" + TenantContextHolder.getTenantId());
        List<AdminUserDO> users = adminUserMapper.selectList();
        return "用户数量：" + users.size();
    }

}
