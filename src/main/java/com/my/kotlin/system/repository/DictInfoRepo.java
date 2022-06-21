package com.my.kotlin.system.repository;

import com.my.kotlin.system.entity.DictInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DictInfoRepo extends JpaRepository<DictInfo,Integer>, JpaSpecificationExecutor<DictInfo> {
    Optional<DictInfo> findAllByDictKey(String dictKey);
}
