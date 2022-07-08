package com.my.kotlin.system.service;

import com.my.kotlin.system.entity.DictDetailInfo;
import com.my.kotlin.system.entity.QuestionInfo;
import com.my.kotlin.system.service.query.QuestionInfoQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface QuestionInfoService {
    Page<QuestionInfo> query(QuestionInfoQuery questionInfoQuery, Pageable pageable);

    QuestionInfo save(QuestionInfo questionInfo);

    void del(Integer id);

    String upload(MultipartFile file);

    void update(QuestionInfo questionInfo);
}
