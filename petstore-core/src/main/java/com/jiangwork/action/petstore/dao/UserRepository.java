package com.jiangwork.action.petstore.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jiangwork.action.petstore.model.User;


public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByUsername(String username);
}