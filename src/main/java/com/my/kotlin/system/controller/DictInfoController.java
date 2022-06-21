package com.my.kotlin.system.controller;

import com.my.kotlin.system.entity.DictInfo;
import com.my.kotlin.system.service.DictInfoService;
import com.my.kotlin.system.service.dto.DictInfoDto;
import com.my.kotlin.system.service.query.DictInfoQuery;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/dictInfo")
public class DictInfoController {

    private final DictInfoService dictInfoService;

    @GetMapping
    @PreAuthorize("@roleCheck.check('dict::query')")
    public ResponseEntity<Object> query(DictInfoQuery dictInfoQuery, Pageable pageable) {
        Page<DictInfo> pages = dictInfoService.queryAll(dictInfoQuery, pageable);
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("@roleCheck.check('dict::add')")
    public ResponseEntity<Object> add(@RequestBody DictInfo dictInfo) {
        return new ResponseEntity<>(dictInfoService.save(dictInfo), HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("@roleCheck.check('dict::del')")
    public void del(@RequestBody DictInfo dictInfo) {
        dictInfoService.del(dictInfo.getId());
    }
}
