package com.cts.projectmanagement.controller;


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

import com.cts.projectmanagement.dao.UserDao;
import com.cts.projectmanagement.repository.UserRepo;


@CrossOrigin
@Controller   
@RequestMapping(path="/user")
public class UserController {
	
	@Autowired 
	private UserRepo userRepo;

	@PostMapping(path="/add")
	public @ResponseBody UserDao addNewUser (@RequestBody UserDao user) {

		UserDao n = new UserDao();
		n.setFirstName(user.getFirstName());
		n.setLastName(user.getLastName());
		n.setEmployeeId(user.getEmployeeId());
		return userRepo.save(n);
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<UserDao> getAllUsers() {
		return userRepo.findAll();
	}
	
	@PutMapping(path="/update")
	public @ResponseBody UserDao updateUser(@RequestBody UserDao user){
		
		UserDao u = userRepo.findOne(user.getUserId());
		u.setEmployeeId(user.getEmployeeId());
		u.setFirstName(user.getFirstName());
		u.setLastName(user.getLastName());
		return userRepo.save(u);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deleteUser(@PathVariable("id") Integer id){
	     userRepo.delete(id);
	     return "return";
		
	}
}

