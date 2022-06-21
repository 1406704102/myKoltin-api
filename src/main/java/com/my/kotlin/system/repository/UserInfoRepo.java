package com.my.kotlin.system.repository;

import com.my.kotlin.system.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepo extends JpaRepository<UserInfo,Integer>, JpaSpecificationExecutor<UserInfo> {
    Optional<UserInfo> findByUserName(String userName);
}
