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
        httpSecurity.csrf().disable()// ??????????????????JWT????????????????????????csrf
                .sessionManagement()// ??????token??????????????????session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // ????????????????????????????????????????????????
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
                // ????????????????????????????????????
                .antMatchers("/userInfo/login", "/userInfo/register")
                .permitAll()
                //??????WithoutToken?????????????????????????????????
                .antMatchers(getWithoutTokenUrls())
                .permitAll()
                //??????????????????????????????options??????
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .anyRequest()// ???????????????????????????????????????????????????
                .authenticated();
        // ????????????
        httpSecurity.headers().cacheControl();
        // ??????JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //????????????????????????????????????????????????
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint);
        return httpSecurity.build();
    }

//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf().disable()// ??????????????????JWT????????????????????????csrf
//                .sessionManagement()// ??????token??????????????????session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                // ????????????????????????????????????????????????
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
//                // ????????????????????????????????????
//                .antMatchers("/userInfo/login", "/userInfo/register")
//                .permitAll()
//                //??????WithoutToken?????????????????????????????????
//                .antMatchers(getWithoutTokenUrls())
//                .permitAll()
//                //??????????????????????????????options??????
//                .antMatchers(HttpMethod.OPTIONS)
//                .permitAll()
//                .anyRequest()// ???????????????????????????????????????????????????
//                .authenticated();
//        // ????????????
//        httpSecurity.headers().cacheControl();
//        // ??????JWT filter
//        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//        //????????????????????????????????????????????????
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
     * @Description //TODO ?????? 403 ???401??????
     * @Date ??????4:18 16/6/2022
     * param 
     * return 
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return (request, response, accessDeniedException) -> {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("????????????");
            response.setStatus(403);
        };
    }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint (){
        return (request, response, authException) -> {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("?????????");
            response.setStatus(401);
        };
    }
    /**
     * ?????????????????? WithoutToken ???????????????
     */
    private String[] getWithoutTokenUrls() {
        // ??????????????? RequestMapping
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
        Set<String> allWithoutToken = new HashSet<>();
        // ?????? RequestMapping
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethods.entrySet()) {
            HandlerMethod value = infoEntry.getValue();
            // ??????????????? WithoutToken ???????????????
            WithoutToken withoutToken = value.getMethodAnnotation(WithoutToken.class);
            // ???????????????????????? WithoutToken ?????????????????????????????????????????????
            if (withoutToken != null) {
                allWithoutToken.addAll(infoEntry.getKey().getPathPatternsCondition().getPatterns().stream().map(PathPattern::getPatternString).collect(Collectors.toSet()));
            }
        }
        return allWithoutToken.toArray(new String[0]);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        //????????????????????????
        return username -> {
            UserInfo user = userInfoService.findByUserName(username);
            List<MenuInfo> l = new ArrayList<>();
            if (user != null) {
//                List<MenuInfo> permissionList = menuInfoService.findBy(user.getUserId());
//                return new JwtUser(user,permissionList);
                return new JwtUser(user,l);
            }
            throw new UsernameNotFoundException("????????????????????????");
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