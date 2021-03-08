package com.carrot97.vhr.config;

import com.carrot97.vhr.model.Hr;
import com.carrot97.vhr.model.RespBean;
import com.carrot97.vhr.service.HrService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    HrService hrService;

    @Autowired
    AccessManager accessManager;

    @Autowired
    RequestUrlFilter requestUrlFilter;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .anyRequest().authenticated()
            .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                @Override
                public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                    o.setAccessDecisionManager(accessManager);
                    o.setSecurityMetadataSource(requestUrlFilter);
                    return o;
                }
            })
            .and()
            .formLogin()
            .usernameParameter("username")
            .passwordParameter("password")
            .loginPage("/login")
            .loginProcessingUrl("/doLogin")
            .successHandler(new AuthenticationSuccessHandler() {
                @Override
                public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    Hr hr = (Hr) authentication.getPrincipal();
                    hr.setPassword(null);
                    PrintWriter out = httpServletResponse.getWriter();
                    RespBean success = new  RespBean.Builder()
                            .setMsg("登陆成功")
                            .setObj(hr)
                            .create();
                    String s = new ObjectMapper().writeValueAsString(success);
                    out.write(s);
                    out.flush();
                    out.close();
                }
            })
            .failureHandler(new AuthenticationFailureHandler() {
                @Override
                public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    String msg = null;
                    if (e instanceof LockedException) {
                        msg = "账户被锁定";
                    } else if (e instanceof CredentialsExpiredException) {
                        msg = "密码过期";
                    } else if (e instanceof AccountExpiredException) {
                        msg = "账户过期";
                    } else if (e instanceof DisabledException) {
                        msg = "账户被禁用";
                    } else if (e instanceof BadCredentialsException) {
                        msg = "用户名或密码输入错误";
                    } else {
                        msg = "发生未知错误，请联系管理员";
                    }
                    RespBean error = new RespBean.Builder()
                            .setStatus(500)
                            .setMsg(msg)
                            .create();
                    String s = new ObjectMapper().writeValueAsString(error);
                    out.write(s);
                    out.flush();
                    out.close();
                }
            })
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(new LogoutSuccessHandler() {
                @Override
                public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    RespBean success = new RespBean.Builder()
                            .setMsg("注销成功")
                            .create();
                    String s = new ObjectMapper().writeValueAsString(success);
                    out.write(s);
                    out.flush();
                    out.close();
                }
            })
            .permitAll()
            .and()
            .csrf().disable()
            // 未认证直接访问页面，不重定向
            .exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
                @Override
                public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    httpServletResponse.setStatus(401);
                    PrintWriter out = httpServletResponse.getWriter();
                    String msg = null;
                    if (e instanceof InsufficientAuthenticationException) {
                        msg = "请求失败";
                    } else {
                        msg = "发生未知错误";
                    }
                    RespBean error = new RespBean.Builder()
                            .setStatus(500)
                            .setMsg(msg)
                            .create();
                    String s = new ObjectMapper().writeValueAsString(error);
                    out.write(s);
                    out.flush();
                    out.close();
                }
            });
    }
}
