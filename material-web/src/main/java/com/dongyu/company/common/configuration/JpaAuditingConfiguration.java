package com.dongyu.company.common.configuration;

import com.dongyu.company.user.domain.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * jpa自动生成creatBy
 *有问题当初始化数据时无法SecurityUtils.getSubject()
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
@Configuration
public class JpaAuditingConfiguration {
    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> Optional.ofNullable(SecurityUtils.getSubject().getPrincipal()).map(o -> {
            User user = (User) o;
            return (Long) user.getId();
        }).orElse(0L);
    }
}
