package com.my.kotlin.system.service.dto;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
public class DictDetailInfoDto {

    private Integer id;

    private Integer dictId;

    private String dictDetailName;

    private String dictDetailKey;

    private Timestamp createTime;
}
