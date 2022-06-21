package com.my.kotlin.system.service.query;

import com.my.kotlin.system.config.query.annotation.Query;
import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
public class QuestionInfoQuery {

    @Query
    private Integer id;

    @Query(type = Query.Type.INNER_LIKE)
    private String questionTitle;

    @Query(type = Query.Type.INNER_LIKE)
    private String questionAnswer;

    @Query(type = Query.Type.INNER_LIKE)
    private String questionFocus;

    @Query(type = Query.Type.INNER_LIKE)
    private String questionType;

    @Query(type = Query.Type.INNER_LIKE)
    private String indexShow;

    @Query(type = Query.Type.INNER_LIKE)
    private String enable;

    private Timestamp createTime;
}
