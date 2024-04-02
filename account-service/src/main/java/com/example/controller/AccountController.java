package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.AccountService;

@RestController
@RequestMapping("api/v1")
public class AccountController {
	@Autowired
	AccountService accountService;

	@GetMapping("loan-balance/{userId}")
	public ResponseEntity<String> getLoanBalance(@PathVariable Long userId) {
		return accountService.getLoanBalance(userId);
	}

	@GetMapping("loan-limit/{userId}")
	public ResponseEntity<String> getLoanLimit(@PathVariable Long userId) {
		return accountService.getLoanLimit(userId);
	}
}
