package com.my.kotlin.system.service.dto;

import com.my.kotlin.system.entity.MenuInfo;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

public class RoleInfoDto {

    private Integer id;

    private String roleName;

    private String roleKey;

    private Timestamp createTime;

    private List<MenuInfo> menus;
}
