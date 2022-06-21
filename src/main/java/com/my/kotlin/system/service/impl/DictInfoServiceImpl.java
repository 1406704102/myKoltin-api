package com.my.kotlin.system.service.impl;

import com.my.kotlin.system.config.query.QueryHelp;
import com.my.kotlin.system.entity.DictInfo;
import com.my.kotlin.system.repository.DictInfoRepo;
import com.my.kotlin.system.service.DictInfoService;
import com.my.kotlin.system.service.query.DictInfoQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DictInfoServiceImpl implements DictInfoService {

    private final DictInfoRepo dictInfoRepo;

    @Override
    public Page<DictInfo> queryAll(DictInfoQuery dictInfoQuery, Pageable pageable) {
        Page<DictInfo> all = dictInfoRepo.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, dictInfoQuery, criteriaBuilder), pageable);
        return all;
    }


    @Override
    public DictInfo save(DictInfo dictInfo) {
//        Optional<DictInfo> optionalDictInfo = dictInfoRepo.findAllByDictKey(dictInfo.getDictKey());
        dictInfo.setCreateTime(Timestamp.from(Instant.now()));
        return dictInfoRepo.save(dictInfo);
    }


    @Override
    public void del(Integer id) {
        dictInfoRepo.deleteById(id);
    }
}
