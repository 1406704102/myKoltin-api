package com.my.kotlin.system.service.impl;

import com.my.kotlin.system.config.query.QueryHelp;
import com.my.kotlin.system.entity.QuestionInfo;
import com.my.kotlin.system.repository.QuestionInfoRepo;
import com.my.kotlin.system.service.QuestionInfoService;
import com.my.kotlin.system.service.query.QuestionInfoQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@AllArgsConstructor
public class QuestionInfoServiceImpl implements QuestionInfoService {

    private final QuestionInfoRepo questionInfoRepo;

    @Override
    public Page<QuestionInfo> query(QuestionInfoQuery questionInfoQuery, Pageable pageable) {
        return questionInfoRepo.findAll(((root, query, criteriaBuilder) -> QueryHelp.getPredicate(root, questionInfoQuery, criteriaBuilder)),pageable);
    }

    @Override
    public QuestionInfo save(QuestionInfo questionInfo) {
        questionInfo.setCreateTime(Timestamp.from(Instant.now()));
        return questionInfoRepo.save(questionInfo);
    }

    @Override
    public void del(Integer id) {
        questionInfoRepo.deleteById(id);
    }
}
