package com.my.kotlin.system.controller;

import com.my.kotlin.system.config.security.RoleCheck;
import com.my.kotlin.system.config.security.annotation.WithoutToken;
import com.my.kotlin.system.entity.QuestionInfo;
import com.my.kotlin.system.entity.UserInfo;
import com.my.kotlin.system.service.UserInfoService;
import com.my.kotlin.system.service.dto.UserInfoDto;
import com.my.kotlin.system.service.query.UserInfoQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/userInfo")
@AllArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;

    @GetMapping("/{id}")
    @PreAuthorize("@roleCheck.check('user::query')")
    public ResponseEntity<Object> find(@PathVariable Integer id) {
        UserInfo byId = userInfoService.findById(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("@roleCheck.check('user::query')")
    public ResponseEntity<Object> query(UserInfoQuery userInfoQuery, Pageable pageable) {
        Page<UserInfo> pages = userInfoService.query(userInfoQuery, pageable);
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }

    @WithoutToken
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserInfoDto userInfo) {
        UserInfo user = userInfoService.login(userInfo);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @WithoutToken
    @PostMapping("/getToken")
    public ResponseEntity<Object> getToken(@RequestBody UserInfoDto userInfoDto) {
        Map<String, Object> data = userInfoService.getToken(userInfoDto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
