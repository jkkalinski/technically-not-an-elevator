package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.User;

@PreAuthorize("isAuthenticated()")
@RestController
public class UserController {
	
	private UserDAO userDAO;

	public UserController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public List<User> findAll() {
		return userDAO.findAll();
	}
	
	@RequestMapping(path = "/users/{id}" , method = RequestMethod.GET)
	public User findUsernameById(@PathVariable ("id") int id) {
		return userDAO.findUsernameById(id);
	}
}
