package com.my.kotlin.system.service;

import com.my.kotlin.system.entity.UserInfo;
import com.my.kotlin.system.service.dto.UserInfoDto;

import java.util.Map;

public interface UserInfoService {
    UserInfo findById(Integer id);

    UserInfo findByUserName(String username);

    UserInfo login(UserInfoDto userInfo);

    Map<String, Object> getToken(UserInfoDto userInfoDto);
}
