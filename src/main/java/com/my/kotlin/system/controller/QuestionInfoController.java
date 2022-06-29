package com.my.kotlin.system.controller;

import com.alibaba.druid.util.StringUtils;
import com.my.kotlin.system.entity.DictDetailInfo;
import com.my.kotlin.system.entity.QuestionInfo;
import com.my.kotlin.system.service.QuestionInfoService;
import com.my.kotlin.system.service.query.QuestionInfoQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/questionInfo")
public class QuestionInfoController {

    private final QuestionInfoService questionInfoService;

    @GetMapping
    @PreAuthorize("@roleCheck.check('question::query')")
    public ResponseEntity<Object> query(QuestionInfoQuery questionInfoQuery, Pageable pageable) {
        if (StringUtils.equals(questionInfoQuery.getQuestionType(), "null")) {
            questionInfoQuery.setQuestionType(null);
        }
        Page<QuestionInfo> pages = questionInfoService.query(questionInfoQuery, pageable);
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }


    @PutMapping
    @PreAuthorize("@roleCheck.check('question::add')")
    public ResponseEntity<Object> add(@RequestBody QuestionInfo questionInfo) {
        return new ResponseEntity<>(questionInfoService.save(questionInfo), HttpStatus.OK);
    }


    @DeleteMapping
    @PreAuthorize("@roleCheck.check('question::del')")
    public void del(@RequestBody QuestionInfo questionInfo) {
        questionInfoService.del(questionInfo.getId());
    }
}
