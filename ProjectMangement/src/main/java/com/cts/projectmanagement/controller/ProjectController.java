package com.cts.projectmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.projectmanagement.dao.ProjectDao;
import com.cts.projectmanagement.dao.UserDao;
import com.cts.projectmanagement.model.ProjectEntity;
import com.cts.projectmanagement.repository.ProjectRepo;
import com.cts.projectmanagement.repository.TaskRepo;
import com.cts.projectmanagement.repository.UserRepo;

@CrossOrigin
@RestController
@RequestMapping(path="/project")
public class ProjectController {

	@Autowired 
	private ProjectRepo projectRepo;
	
	@Autowired 
	private UserRepo userRepo;
	
	@Autowired 
	TaskRepo taskRepo;
	
	@PostMapping()
	public @ResponseBody ProjectEntity addProject(@RequestBody ProjectEntity p){
		ProjectDao project = new ProjectDao();
		project.setProject(p.getProjectName());
		project.setStartDate(p.getStartDate());
		project.setEndDate(p.getEndDate());
		project.setPriority(p.getPriority());
		project.setUserId(p.getUserId());
		
		projectRepo.save(project);
		
		p.setCompletedTaskNumber(0);
		p.setTaskNumber(0);
		
		return p;
	}
	
	@PutMapping
	public @ResponseBody ProjectEntity updateProject(@RequestBody ProjectEntity p){
		
		ProjectDao project = projectRepo.findOne(p.getProjectId());
		if(project!= null){
			project.setProject(p.getProjectName());
			project.setStartDate(p.getStartDate());
			project.setEndDate(p.getEndDate());
			project.setPriority(p.getPriority());
			project.setUserId(p.getUserId());
			projectRepo.save(project);
			return p;
		}else{
			return null;
		}
	}
	
	@GetMapping(path="/all")
	public @ResponseBody List<ProjectEntity> getAllProject() {
		
		 Iterable<ProjectDao> projectList =  projectRepo.findAll();
		 List<ProjectEntity> projectResponseList = new ArrayList<>();
		 for(ProjectDao project: projectList){
			 ProjectEntity p = new ProjectEntity();
			 p.setProjectId(project.getProjectId());
			 p.setProjectName(project.getProject());
			 p.setStartDate(project.getStartDate());
			 p.setEndDate(project.getEndDate());
			 p.setPriority(project.getPriority());
			 p.setTaskNumber(taskRepo.findAllByProjectId(project.getProjectId()).size());
			 p.setCompletedTaskNumber(taskRepo.findAllByProjectIdAndStatus(project.getProjectId(), "COMPLETED").size());
			 p.setUserId(project.getUserId());
			 if(project.getUserId() != null){
				 UserDao u = userRepo.findOne(project.getUserId());
				 if( u != null){
					 p.setManager(u.getFirstName());
				 }
			 }
			 
			 projectResponseList.add(p);
		 }
		return projectResponseList;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deleteProject(@PathVariable("id") Integer id){
		projectRepo.delete(id);
	     return "deleted";
		
	}
	
}
