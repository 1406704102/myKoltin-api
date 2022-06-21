package com.my.kotlin.system.service.dto;

import javax.persistence.Column;
import java.sql.Timestamp;

public class MenuInfoDto {

    private Integer id;

    private String menuName;

    private String permission;

    private Integer parentId;

    private Integer levelNum;

    private Integer sort;

    private String component;

    private String subCount;

    private Timestamp createTime;
}
