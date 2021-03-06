package com.jiangwork.action.petstore.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<OfferDO, Long>, JpaSpecificationExecutor<OfferDO> {

    List<OfferDO> findByUserId(long userid);
}
