package com.my.kotlin.system.service.dto;

import com.my.kotlin.system.entity.RoleInfo;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
public class UserInfoDto {
    private Integer id;

    private String userName;
    private String avatarUrl;

    private String nickName;

    private String passWord;

    private Timestamp createTime;

    private Set<RoleInfo> roleInfos;

    private String enable;

    private String code;
}
