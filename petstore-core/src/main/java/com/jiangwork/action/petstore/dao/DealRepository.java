package com.jiangwork.action.petstore.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository extends CrudRepository<DealDO, Long>{
    List<DealDO> findByOfferId(long offerid);
}
