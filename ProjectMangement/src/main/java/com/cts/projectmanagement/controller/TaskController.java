package com.cts.projectmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.projectmanagement.dao.ParentTaskDao;
import com.cts.projectmanagement.dao.ProjectDao;
import com.cts.projectmanagement.dao.TaskDao;
import com.cts.projectmanagement.dao.UserDao;
import com.cts.projectmanagement.model.TaskEntity;
import com.cts.projectmanagement.repository.ParentTaskRepo;
import com.cts.projectmanagement.repository.ProjectRepo;
import com.cts.projectmanagement.repository.TaskRepo;
import com.cts.projectmanagement.repository.UserRepo;

@CrossOrigin
@Controller
@RequestMapping(path="/task")
public class TaskController {
	
	@Autowired 
	TaskRepo taskRepo;
	
	@Autowired
	private ParentTaskRepo repo;
	
	@Autowired
	private ProjectRepo projectRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewTask (@RequestBody TaskEntity task) {
		//System.out.println(task.isParentTask());
		if(task.isParentTask()){
			ParentTaskDao pTask = new ParentTaskDao();
			pTask.setParentTask(task.getTaskName());
			repo.save(pTask);
		}else{
			TaskDao t = new TaskDao();
			t.setParentId(task.getParentTaskId());
			t.setProjectId(task.getProjectId());
			t.setTask(task.getTaskName());
			t.setStartDate(task.getStartDate());
			t.setEndDate(task.getEndDate());
			t.setPriority(task.getPriority());
			t.setUserId(task.getUserId());	
			t.setStatus("STARTED");
			taskRepo.save(t);
		}
		
		return "Saved";
	}

	@GetMapping(path="/all")
	public @ResponseBody List<TaskEntity> getAllTasks() {
		List<TaskEntity> taskObjList = new ArrayList<>();
		List<TaskDao> taskList =  (List<TaskDao>) taskRepo.findAll();
		for(TaskDao t: taskList){
			TaskEntity obj = new TaskEntity();
			obj.setTaskId(t.getTaskId());
			obj.setParentTaskId(t.getParentId());
			obj.setProjectId(t.getProjectId());
			obj.setTaskName(t.getTask());
			obj.setStartDate(t.getStartDate());
			obj.setEndDate(t.getEndDate());
			obj.setPriority(t.getPriority());
			obj.setStatus(t.getStatus());
			obj.setUserId(t.getUserId());
			if(t.getParentId() != null){
				ParentTaskDao pTask = repo.findOne(t.getParentId());
				if(pTask != null){
					obj.setParentTaskName(pTask.getParentTask());
				}
			}
			if(t.getProjectId() != null){
				ProjectDao p = projectRepo.findOne(t.getProjectId());
				if(p != null){
					obj.setProjectName(p.getProject());
				}	
			}
			if(t.getUserId() != null){
				UserDao u = userRepo.findOne(t.getUserId());
				if(u != null){
					obj.setUserName(u.getFirstName());
				}
			}
			
			taskObjList.add(obj);
			
		}
		return taskObjList;
	}
	
	@PutMapping(path="/update")
	public @ResponseBody TaskDao updateTask(@RequestBody TaskEntity task){
		
		TaskDao t = taskRepo.findOne(task.getTaskId());
		t.setParentId(task.getParentTaskId());
		t.setProjectId(task.getProjectId());
		t.setTask(task.getTaskName());
		t.setStartDate(task.getStartDate());
		t.setEndDate(task.getEndDate());
		t.setPriority(task.getPriority());
        t.setStatus(task.getStatus());     
		return taskRepo.save(t);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deleteTask(@PathVariable("id") Integer id){
		TaskDao t = taskRepo.findOne(id);
		t.setStatus("completed");
         taskRepo.save(t);
	     return "return";
		
	}
	@GetMapping(path="/allTaskcompleted")
	public @ResponseBody List<TaskEntity> getAllCompletedTasks() {
		List<TaskEntity> taskObjList = new ArrayList<>();
		List<TaskDao> taskList = taskRepo.findTaskByStatus("completed");
		for(TaskDao t: taskList){
			TaskEntity obj = new TaskEntity();
			obj.setTaskId(t.getTaskId());
			obj.setParentTaskId(t.getParentId());
			obj.setProjectId(t.getProjectId());
			obj.setTaskName(t.getTask());
			obj.setStartDate(t.getStartDate());
			obj.setEndDate(t.getEndDate());
			obj.setPriority(t.getPriority());
			obj.setStatus(t.getStatus());
			obj.setUserId(t.getUserId());
			if(t.getParentId() != null){
				ParentTaskDao pTask = repo.findOne(t.getParentId());
				if(pTask != null){
					obj.setParentTaskName(pTask.getParentTask());
				}
			}
			if(t.getProjectId() != null){
				ProjectDao p = projectRepo.findOne(t.getProjectId());
				if(p != null){
					obj.setProjectName(p.getProject());
				}	
			}
			if(t.getUserId() != null){
				UserDao u = userRepo.findOne(t.getUserId());
				if(u != null){
					obj.setUserName(u.getFirstName());
				}
			}
			
			taskObjList.add(obj);
			
		}
		return taskObjList;
		
	}
	
	@RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
	public @ResponseBody Iterable<TaskEntity> getTasksByProject(@PathVariable("id") Integer id){
 
		List<TaskEntity> taskObjList = new ArrayList<>();
		List<TaskDao> taskList = taskRepo.findAllByProjectId(id);
		for(TaskDao t: taskList){
			TaskEntity obj = new TaskEntity();
			obj.setTaskId(t.getTaskId());
			obj.setParentTaskId(t.getParentId());
			obj.setProjectId(t.getProjectId());
			obj.setTaskName(t.getTask());
			obj.setStartDate(t.getStartDate());
			obj.setEndDate(t.getEndDate());
			obj.setPriority(t.getPriority());
			obj.setStatus(t.getStatus());
			obj.setUserId(t.getUserId());
			if(t.getParentId() != null){
				ParentTaskDao pTask = repo.findOne(t.getParentId());
				if(pTask != null){
					obj.setParentTaskName(pTask.getParentTask());
				}
			}
			if(t.getProjectId() != null){
				ProjectDao p = projectRepo.findOne(t.getProjectId());
				if(p != null){
					obj.setProjectName(p.getProject());
				}	
			}
			if(t.getUserId() != null){
				UserDao u = userRepo.findOne(t.getUserId());
				if(u != null){
					obj.setUserName(u.getFirstName());
				}
			}
			
			taskObjList.add(obj);
			
		}
		return taskObjList;
		
	}

}
