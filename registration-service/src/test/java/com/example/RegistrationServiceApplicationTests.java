package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.dao.UserRepository;
import com.example.model.User;
import com.example.service.UserService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RegistrationServiceApplicationTests {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	public void testRegisterUser_validUser_returnsCreated() throws Exception {
		// Mock user repository behavior
		User user = new User("username", "valid@email.com", "1234567890", "password");
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
		when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
		when(userRepository.save(user)).thenReturn(user);

		ResponseEntity<String> response = userService.registerUser(user);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("User Registered", response.getBody());
	}

	@Test
	public void testRegisterUser_emptyUsername_returnsBadRequest() {
		User user = new User("", "valid@email.com", "1234567890", "password");

		ResponseEntity<String> response = userService.registerUser(user);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Username is required"));
	}

	@Test
	public void testRegisterUser_invalidEmailFormat_returnsBadRequest() {
		User user = new User("username", "invalidEmail", "1234567890", "password");

		ResponseEntity<String> response = userService.registerUser(user);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Invalid email format"));
	}

	@Test
	public void testRegisterUser_existingEmail_returnsBadRequest() throws Exception {
		User user = new User("username", "valid@email.com", "1234567890", "password");
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(new User()));

		ResponseEntity<String> response = userService.registerUser(user);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Email already exists"));
	}

	@Test
	public void testRegisterUser_emptyPassword_returnsBadRequest() {
		User user = new User("username", "valid@email.com", "1234567890", "");

		ResponseEntity<String> response = userService.registerUser(user);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Password is required"));
	}

	@Test
	public void testRegisterUser_shortPassword_returnsBadRequest() {
		User user = new User("username", "valid@email.com", "1234567890", "short");

		ResponseEntity<String> response = userService.registerUser(user);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Password should be at least 6 characters"));
	}

	@Test()
	public void testRegisterUser_userRepositoryException_throwsException() throws Exception {
		User user = new User("username", "valid@email.com", "1234567890", "password");
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
		when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
		when(userRepository.save(user)).thenThrow(new Exception());

		userService.registerUser(user);
	}

}
