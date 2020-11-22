package com.sustech.gamercenter.util.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // TODO discard SecurityConfig class, switch to full token

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // For web browser
//                .and()
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll();
//                .antMatchers("/api/**").hasRole(PLAYER.name())
//                .antMatchers(HttpMethod.POST, "/manage/api/**").hasAuthority(PLAYER_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET, "/manage/api/**").hasAnyRole(ADMIN.name(), ADMIN_TRAINEE.name())

//                .anyRequest()
//                .authenticated()

//                .and()
//                .httpBasic();

    }


}
