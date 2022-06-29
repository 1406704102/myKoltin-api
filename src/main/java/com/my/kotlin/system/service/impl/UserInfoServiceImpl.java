package com.my.kotlin.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.my.kotlin.system.config.query.QueryHelp;
import com.my.kotlin.system.config.security.JwtTokenUtil;
import com.my.kotlin.system.config.security.MyPasswordEncoder;
import com.my.kotlin.system.entity.UserInfo;
import com.my.kotlin.system.repository.UserInfoRepo;
import com.my.kotlin.system.service.UserInfoService;
import com.my.kotlin.system.service.dto.UserInfoDto;
import com.my.kotlin.system.service.query.UserInfoQuery;
import com.my.kotlin.util.RedisUtil;
import com.my.kotlin.util.WxUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepo userInfoRepo;
    private final MyPasswordEncoder myPasswordEncoder;
    private final UserDetailsService userDetailsService;
    private final RedisUtil redisUtil;
    private final JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.expiration}")
    private Long expiration;


    @Override
    public Page<UserInfo> query(UserInfoQuery userInfoQuery, Pageable pageable) {
        return userInfoRepo.findAll(((root, query, criteriaBuilder) -> QueryHelp.getPredicate(root, userInfoQuery, criteriaBuilder)),pageable);
    }

    @Override
    public UserInfo findById(Integer id) {
        Optional<UserInfo> byId = userInfoRepo.findById(id);
        return byId.orElseGet(UserInfo::new);
    }

    @Override
    public UserInfo findByUserName(String username) {
        return userInfoRepo.findByUserName(username).orElseGet(UserInfo::new);
    }

    @Override
    public UserInfo login(UserInfoDto userInfo) {
        String j2SR = WxUtil.getJ2SR(userInfo.getCode());
        JSONObject json = JSONObject.parseObject(j2SR);
        String openid = json.getString("openid");
        Optional<UserInfo> byUserName = userInfoRepo.findByUserName(openid);
        if (byUserName.isEmpty()) {
            String encodePassword = myPasswordEncoder.passwordEncoder().encode(openid);
            return userInfoRepo.save(UserInfo.builder().avatarUrl(userInfo.getAvatarUrl()).nickName(userInfo.getNickName()).userName(openid).password(encodePassword).createTime(Timestamp.from(Instant.now())).enable("1").build());
        } else {
            UserInfo userInfo1 = byUserName.orElseGet(UserInfo::new);
            userInfo1.setNickName(userInfo.getNickName());
            userInfo1.setAvatarUrl(userInfo.getAvatarUrl());
            return userInfoRepo.save(userInfo1);
        }
    }

    @Override
    public Map<String, Object> getToken(UserInfoDto userInfoDto) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userInfoDto.getUserName());
//            List<String> collect = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

            if (!myPasswordEncoder.passwordEncoder().matches(userInfoDto.getUserName(), userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            redisUtil.set("token:" + userInfoDto.getUserName(), token, expiration/60);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("token", tokenHead + " " + token);
        return data;
    }
}
