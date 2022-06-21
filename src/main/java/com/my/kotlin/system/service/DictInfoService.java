package com.my.kotlin.system.service;

import com.my.kotlin.system.entity.DictInfo;
import com.my.kotlin.system.service.query.DictInfoQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DictInfoService {
    DictInfo save(DictInfo dictInfo);

    Page<DictInfo> queryAll(DictInfoQuery dictInfoQuery, Pageable pageable);

    void del(Integer id);
}
