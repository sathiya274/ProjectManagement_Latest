package com.cts.projectmanagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.cts.projectmanagement.dao.ProjectDao;

public interface ProjectRepo extends CrudRepository<ProjectDao,Integer>{

}
