package org.lotionvirgilabloh.lotionwebzuul.core.config;

import org.apache.logging.log4j.core.config.Order;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置
 * @ EnableWebSecurity 启用web安全
 */
@Configuration
@EnableWebSecurity
@Order (101)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * http安全配置
     * @param http http安全对象
     * @throws Exception http安全异常信息
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();  // 禁用csrf
    }
}
