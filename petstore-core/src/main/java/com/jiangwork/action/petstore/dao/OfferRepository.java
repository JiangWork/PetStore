package com.jiangwork.action.petstore.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends CrudRepository<OfferDO, Long> {

    List<OfferDO> findByUserId(long userid);
}
