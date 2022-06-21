package com.my.kotlin.system.repository;


import com.my.kotlin.system.entity.QuestionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuestionInfoRepo extends JpaRepository<QuestionInfo,Integer>, JpaSpecificationExecutor<QuestionInfo> {
}
