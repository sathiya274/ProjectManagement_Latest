package com.cts.projectmanagement.repository;


import org.springframework.data.repository.CrudRepository;

import com.cts.projectmanagement.dao.UserDao;


public interface UserRepo extends CrudRepository<UserDao, Integer> {

}