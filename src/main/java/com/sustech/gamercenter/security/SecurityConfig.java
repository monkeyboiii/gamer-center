package com.sustech.gamercenter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*
     * Now obsolete, useful after full construction
     * */
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public SecurityConfig(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }

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
//
//                .and()
//                .httpBasic();

    }


}
