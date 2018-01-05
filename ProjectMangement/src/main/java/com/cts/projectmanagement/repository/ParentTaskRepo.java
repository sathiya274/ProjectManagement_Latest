package com.cts.projectmanagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.cts.projectmanagement.dao.ParentTaskDao;

public interface ParentTaskRepo extends CrudRepository<ParentTaskDao, Integer> {

}
