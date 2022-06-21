package com.my.kotlin.system.service.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DictInfoDto {

    private Integer id;

    private String dictName;

    private String dictKey;

    private Timestamp createTime;
}
