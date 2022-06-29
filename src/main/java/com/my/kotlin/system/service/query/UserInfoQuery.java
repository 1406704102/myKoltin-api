package com.my.kotlin.system.service.query;

import com.my.kotlin.system.config.query.annotation.Query;
import lombok.Data;

import javax.persistence.Column;

@Data
public class UserInfoQuery {

    @Query
    private Integer id;

    @Query(type = Query.Type.INNER_LIKE)
    private String nickName;

    @Query(type = Query.Type.INNER_LIKE)
    private String userName;

}
