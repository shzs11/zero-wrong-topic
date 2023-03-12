package edu.gdpu.zero.framework.idempotent.config;

import edu.gdpu.zero.framework.idempotent.core.aop.IdempotentAspect;
import edu.gdpu.zero.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import edu.gdpu.zero.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import edu.gdpu.zero.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import edu.gdpu.zero.framework.idempotent.core.redis.IdempotentRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import edu.gdpu.zero.framework.redis.config.ZeroRedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = ZeroRedisAutoConfiguration.class)
public class ZeroIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
