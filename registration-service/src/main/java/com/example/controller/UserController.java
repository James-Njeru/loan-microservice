package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.service.UserService;

@RestController
@RequestMapping("api/v1")
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping("/register-user")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		return userService.registerUser(user);
	}
}
