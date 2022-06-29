package com.my.kotlin.system.service;

import com.my.kotlin.system.entity.UserInfo;
import com.my.kotlin.system.service.dto.UserInfoDto;
import com.my.kotlin.system.service.query.UserInfoQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface UserInfoService {
    UserInfo findById(Integer id);

    UserInfo findByUserName(String username);

    UserInfo login(UserInfoDto userInfo);

    Map<String, Object> getToken(UserInfoDto userInfoDto);

    Page<UserInfo> query(UserInfoQuery userInfoQuery, Pageable pageable);
}
