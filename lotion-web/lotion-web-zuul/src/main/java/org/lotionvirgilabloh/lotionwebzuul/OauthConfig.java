package org.lotionvirgilabloh.lotionwebzuul;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OauthConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests()
                .anyRequest().authenticated()
                .antMatchers("/testroot").hasAuthority ("root")
                .antMatchers("/testadmin").hasAuthority("adm")
                .and ().csrf ().disable ()
                .logout().logoutUrl("/logout").permitAll()
                .logoutSuccessUrl("/");
        http.headers().frameOptions().disable();
        http.headers().cacheControl();
    }

}
