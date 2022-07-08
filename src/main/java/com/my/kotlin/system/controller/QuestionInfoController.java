package com.my.kotlin.system.controller;

import com.alibaba.druid.util.StringUtils;
import com.my.kotlin.system.annotation.PreventDuplication;
import com.my.kotlin.system.entity.DictDetailInfo;
import com.my.kotlin.system.entity.QuestionInfo;
import com.my.kotlin.system.service.QuestionInfoService;
import com.my.kotlin.system.service.query.QuestionInfoQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/questionInfo")
public class QuestionInfoController {

    private final QuestionInfoService questionInfoService;

    @GetMapping
    @PreAuthorize("@roleCheck.check('question::query')")
//    @PreventDuplication(value = "questionInfo.query",expireSeconds = 100)
    public ResponseEntity<Object> query(QuestionInfoQuery questionInfoQuery, Pageable pageable) {
        if (StringUtils.equals(questionInfoQuery.getQuestionType(), "null")) {
            questionInfoQuery.setQuestionType(null);
        }
        Page<QuestionInfo> pages = questionInfoService.query(questionInfoQuery, pageable);
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("@roleCheck.check('question::add')")
    public ResponseEntity<Object> add(@RequestBody QuestionInfo questionInfo) {
        return new ResponseEntity<>(questionInfoService.save(questionInfo), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("@roleCheck.check('question::edit')")
    public ResponseEntity<Object> update(@RequestBody QuestionInfo questionInfo) {
        questionInfoService.update(questionInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping
    @PreAuthorize("@roleCheck.check('question::del')")
    public void del(@RequestBody QuestionInfo questionInfo) {
        questionInfoService.del(questionInfo.getId());
    }

    @PostMapping("/uploadQiNiu")
    public ResponseEntity<Object> uploadQiNiu(@RequestParam MultipartFile file){
        String url = questionInfoService.upload(file);
        return new ResponseEntity<>(url,HttpStatus.OK);
    }
}
