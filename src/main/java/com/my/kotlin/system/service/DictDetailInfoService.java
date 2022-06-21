package com.my.kotlin.system.service;

import com.my.kotlin.system.entity.DictDetailInfo;
import com.my.kotlin.system.entity.DictInfo;
import com.my.kotlin.system.service.query.DictDetailInfoQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DictDetailInfoService {

    Page<DictDetailInfo> query(DictDetailInfoQuery dictDetailInfoQuery, Pageable pageable);


    DictDetailInfo save(DictDetailInfo dictDetailInfo);

    void del(Integer id);
}
