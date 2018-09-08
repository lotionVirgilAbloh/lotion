package org.lotionvirgilabloh.lotionwebindex.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
@EnableOAuth2Sso
public class OauthConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/dashboard/**").authorizeRequests().anyRequest()
                .authenticated().and ().csrf ().disable ();
//                .csrfTokenRepository(csrfTokenRepository()).and()
//                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
//                .logout().logoutUrl("/dashboard/logout").permitAll()
//                .logoutSuccessUrl("/");
    }

//    private Filter csrfHeaderFilter() {
//        return new OncePerRequestFilter () {
//            @Override
//            protected void doFilterInternal(HttpServletRequest request,
//                                            HttpServletResponse response, FilterChain filterChain)
//                    throws ServletException, IOException {
//                CsrfToken csrf = (CsrfToken) request
//                        .getAttribute(CsrfToken.class.getName());
//                if (csrf != null) {
//                    Cookie cookie = new Cookie("XSRF-TOKEN",
//                            csrf.getToken());
//                    cookie.setPath("/");
//                    response.addCookie(cookie);
//                }
//                filterChain.doFilter(request, response);
//            }
//        };
//    }1
//
//    private CsrfTokenRepository csrfTokenRepository() {
//        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository ();
//        repository.setHeaderName("X-XSRF-TOKEN");
//        return repository;
//    }
}
