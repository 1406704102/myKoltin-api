package com.my.kotlin.system.service.query;

import com.my.kotlin.system.config.query.annotation.Query;
import lombok.Data;

@Data
public class DictInfoQuery {

    @Query
    private Integer id;

    @Query(type = Query.Type.INNER_LIKE)
    private String dictName;

    @Query(type = Query.Type.INNER_LIKE)
    private String dictKey;
}
