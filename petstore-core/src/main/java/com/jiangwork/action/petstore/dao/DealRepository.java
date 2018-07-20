package com.jiangwork.action.petstore.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jiangwork.action.petstore.model.Deal;
import com.jiangwork.action.petstore.model.Offer;

public interface DealRepository extends CrudRepository<Deal, Long>{
    List<Deal> findByOfferId(long offerid);
}
