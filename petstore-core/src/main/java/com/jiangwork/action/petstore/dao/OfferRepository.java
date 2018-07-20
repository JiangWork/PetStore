package com.jiangwork.action.petstore.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jiangwork.action.petstore.model.Offer;

public interface OfferRepository extends CrudRepository<Offer, Long> {

    List<Offer> findByUserid(long userid);
}
