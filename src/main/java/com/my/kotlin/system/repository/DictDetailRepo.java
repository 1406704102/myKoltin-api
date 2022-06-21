package com.my.kotlin.system.repository;

import com.my.kotlin.system.entity.DictDetailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DictDetailRepo extends JpaRepository<DictDetailInfo,Integer>, JpaSpecificationExecutor<DictDetailInfo> {
}
