package com.my.kotlin.system.service.query;

import com.my.kotlin.system.config.query.annotation.Query;
import lombok.Data;

@Data
public class DictDetailInfoQuery {

    @Query
    private Integer id;

    @Query
    private Integer dictId;

    @Query(type = Query.Type.INNER_LIKE)
    private String dictDetailName;

    @Query(type = Query.Type.INNER_LIKE)
    private String dictDetailKey;
}
