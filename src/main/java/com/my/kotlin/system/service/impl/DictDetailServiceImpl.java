package com.my.kotlin.system.service.impl;

import com.my.kotlin.system.config.query.QueryHelp;
import com.my.kotlin.system.entity.DictDetailInfo;
import com.my.kotlin.system.entity.DictInfo;
import com.my.kotlin.system.repository.DictDetailRepo;
import com.my.kotlin.system.service.DictDetailInfoService;
import com.my.kotlin.system.service.query.DictDetailInfoQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DictDetailServiceImpl implements DictDetailInfoService {

    private final DictDetailRepo dictDetailRepo;

    @Override
    public Page<DictDetailInfo> query(DictDetailInfoQuery dictDetailInfoQuery, Pageable pageable) {
        return dictDetailRepo.findAll(((root, query, criteriaBuilder) -> QueryHelp.getPredicate(root, dictDetailInfoQuery, criteriaBuilder)), pageable);
    }

    @Override
    public DictDetailInfo save(DictDetailInfo dictDetailInfo) {
        return dictDetailRepo.save(dictDetailInfo);
    }

    @Override
    public void del(Integer id) {
        dictDetailRepo.deleteById(id);
    }
}
