package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dao.UserRepository;
import com.example.model.User;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Async
	@Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 5000))
	public ResponseEntity<String> registerUser(User user) {
		// Validate user input
		List<String> validationErrors = validateUser(user);
		if (!validationErrors.isEmpty()) {
			return new ResponseEntity<>(String.join(", ", validationErrors), HttpStatus.BAD_REQUEST);
		}

		// Encrypt password
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		try {
			userRepository.save(user);
			return new ResponseEntity<>("User Registered", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("User Registration Failed", HttpStatus.BAD_REQUEST);
	}

	private List<String> validateUser(User user) {
		List<String> errors = new ArrayList<>();

		if (StringUtils.isBlank(user.getUsername())) {
			errors.add("Username is required");
		}
		if (StringUtils.isBlank(user.getEmail())) {
			errors.add("Email is required");
		}
		if (!EmailValidator.getInstance().isValid(user.getEmail())) {
			errors.add("Invalid email format");
		}
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			errors.add("Email already exists");
		}
		if (StringUtils.isBlank(user.getPhonenumber())) {
			errors.add("Phone number is required");
		}
		if (StringUtils.isBlank(user.getPassword())) {
			errors.add("Password is required");
		}
		if (user.getPassword().length() < 6) {
			errors.add("Password should be at least 6 characters");
		}

		return errors;
	}
}
