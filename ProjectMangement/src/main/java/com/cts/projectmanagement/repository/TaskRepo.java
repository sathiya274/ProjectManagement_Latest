package com.cts.projectmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cts.projectmanagement.dao.TaskDao;

public interface TaskRepo extends CrudRepository<TaskDao,Integer>{
	

	List<TaskDao> findAllByProjectId(Integer id);
		
	List<TaskDao> findAllByProjectIdAndStatus(Integer id, String status);
	
	@Query("SELECT TS FROM TaskDao TS where TS.status = :status")
	List<TaskDao> findTaskByStatus(@Param("status") String status);
 	
}
