package com.my.kotlin.system.config.security;

import com.my.kotlin.system.config.security.annotation.WithoutToken;
import com.my.kotlin.system.entity.MenuInfo;
import com.my.kotlin.system.entity.UserInfo;
import com.my.kotlin.system.service.MenuInfoService;
import com.my.kotlin.system.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SpringSecurityConfig {

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MenuInfoService menuInfoService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()// 由于使用的是JWT，我们这里不需要csrf
                .sessionManagement()// 基于token，所以不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 允许对于网站静态资源的无授权访问
                .antMatchers(HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**"
                )
                .permitAll()
                // 对登录注册要允许匿名访问
                .antMatchers("/userInfo/login", "/userInfo/register")
                .permitAll()
                //允许WithoutToken注解标注的方法匿名访问
                .antMatchers(getWithoutTokenUrls())
                .permitAll()
                //跨域请求会先进行一次options请求
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .anyRequest()// 除上面外的所有请求全部需要鉴权认证
                .authenticated();
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        // 添加JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint);
        return httpSecurity.build();
    }

//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf().disable()// 由于使用的是JWT，我们这里不需要csrf
//                .sessionManagement()// 基于token，所以不需要session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                // 允许对于网站静态资源的无授权访问
//                .antMatchers(HttpMethod.GET,
//                        "/",
//                        "/*.html",
//                        "/favicon.ico",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js",
//                        "/swagger-resources/**",
//                        "/v2/api-docs/**"
//                )
//                .permitAll()
//                // 对登录注册要允许匿名访问
//                .antMatchers("/userInfo/login", "/userInfo/register")
//                .permitAll()
//                //允许WithoutToken注解标注的方法匿名访问
//                .antMatchers(getWithoutTokenUrls())
//                .permitAll()
//                //跨域请求会先进行一次options请求
//                .antMatchers(HttpMethod.OPTIONS)
//                .permitAll()
//                .anyRequest()// 除上面外的所有请求全部需要鉴权认证
//                .authenticated();
//        // 禁用缓存
//        httpSecurity.headers().cacheControl();
//        // 添加JWT filter
//        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//        //添加自定义未授权和未登录结果返回
//        httpSecurity.exceptionHandling()
//                .accessDeniedHandler(customAccessDeniedHandler)
//                .authenticationEntryPoint(customAuthenticationEntryPoint);
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService())
//                .passwordEncoder(myPasswordEncoder.passwordEncoder());
//    }


    /*
     * @Author PangJie___
     * @Description //TODO 修改 403 和401返回
     * @Date 下午4:18 16/6/2022
     * param 
     * return 
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return (request, response, accessDeniedException) -> {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("非法请求");
            response.setStatus(403);
        };
    }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint (){
        return (request, response, authException) -> {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("请登录");
            response.setStatus(401);
        };
    }
    /**
     * 获取标有注解 WithoutToken 的访问路径
     */
    private String[] getWithoutTokenUrls() {
        // 获取所有的 RequestMapping
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
        Set<String> allWithoutToken = new HashSet<>();
        // 循环 RequestMapping
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethods.entrySet()) {
            HandlerMethod value = infoEntry.getValue();
            // 获取方法上 WithoutToken 类型的注解
            WithoutToken withoutToken = value.getMethodAnnotation(WithoutToken.class);
            // 如果方法上标注了 WithoutToken 注解，就获取该方法的访问全路径
            if (withoutToken != null) {
                allWithoutToken.addAll(infoEntry.getKey().getPathPatternsCondition().getPatterns().stream().map(PathPattern::getPatternString).collect(Collectors.toSet()));
            }
        }
        return allWithoutToken.toArray(new String[0]);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> {
            UserInfo user = userInfoService.findByUserName(username);
            List<MenuInfo> l = new ArrayList<>();
            if (user != null) {
//                List<MenuInfo> permissionList = menuInfoService.findBy(user.getUserId());
//                return new JwtUser(user,permissionList);
                return new JwtUser(user,l);
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
    }


    /**
     * JWT filter
     */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }
}