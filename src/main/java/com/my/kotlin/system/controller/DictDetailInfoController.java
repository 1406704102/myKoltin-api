package com.my.kotlin.system.controller;


import com.my.kotlin.system.entity.DictDetailInfo;
import com.my.kotlin.system.entity.DictInfo;
import com.my.kotlin.system.service.DictDetailInfoService;
import com.my.kotlin.system.service.query.DictDetailInfoQuery;
import com.my.kotlin.system.service.query.DictInfoQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dictDetailInfo")
@AllArgsConstructor
public class DictDetailInfoController {

    private final DictDetailInfoService dictDetailInfoService;

    @GetMapping
    @PreAuthorize("@roleCheck.check('dict::query')")
    public ResponseEntity<Object> query(DictDetailInfoQuery dictDetailInfoQuery, Pageable pageable) {
        Page<DictDetailInfo> pages = dictDetailInfoService.query(dictDetailInfoQuery, pageable);
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }


    @PutMapping
    @PreAuthorize("@roleCheck.check('dict::add')")
    public ResponseEntity<Object> add(@RequestBody DictDetailInfo dictDetailInfo) {
        return new ResponseEntity<>(dictDetailInfoService.save(dictDetailInfo), HttpStatus.OK);
    }


    @DeleteMapping
    @PreAuthorize("@roleCheck.check('dict::del')")
    public void del(@RequestBody DictInfo dictInfo) {
        dictDetailInfoService.del(dictInfo.getId());
    }
}