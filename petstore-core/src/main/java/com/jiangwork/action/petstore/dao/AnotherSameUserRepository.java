package com.jiangwork.action.petstore.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AnotherSameUserRepository extends JpaRepository<AnotherSameUserDo, Long>, JpaSpecificationExecutor<AnotherSameUserDo> {

    List<AnotherSameUserDo> findByUsername(String username);
}