package edu.gdpu.zero.framework.operatelog.config;

import edu.gdpu.zero.framework.operatelog.core.aop.OperateLogAspect;
import edu.gdpu.zero.framework.operatelog.core.service.OperateLogFrameworkService;
import edu.gdpu.zero.framework.operatelog.core.service.OperateLogFrameworkServiceImpl;
import edu.gdpu.zero.module.system.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ZeroOperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi) {
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }

}
