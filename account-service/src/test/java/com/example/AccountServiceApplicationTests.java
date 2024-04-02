package com.example;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.dao.AccountRepository;
import com.example.model.Loans;
import com.example.service.AccountService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccountServiceApplicationTests {

	@InjectMocks
	private AccountService accountService;

	@Mock
	private AccountRepository accountRepository;

	@Test
	public void testGetLoanBalance_existingLoan_success() {
		Long userId = 1L;
		double loanAmount = 1000;

		Loans loans = new Loans(loanAmount, null, null);
		when(accountRepository.findByUserId(userId)).thenReturn(loans);

		ResponseEntity<String> response = accountService.getLoanBalance(userId);

		// Verify response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Your loan is " + loanAmount, response.getBody());
	}

	@Test
	public void testGetLoanBalance_noLoanFound_badRequest() {
		Long userId = 1L;

		when(accountRepository.findByUserId(userId)).thenReturn(null);

		ResponseEntity<String> response = accountService.getLoanBalance(userId);

		// Verify response
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Check Balance Failed", response.getBody());
	}

	@Test()
	public void testGetLoanBalance_repositoryException_throwsException() throws Exception {
		Long userId = 1L;

		when(accountRepository.findByUserId(userId)).thenThrow(new Exception());

		accountService.getLoanBalance(userId);
	}

	@Test
	public void testGetLoanLimit_existingLoan_success() {
		Long userId = 1L;
		double loanLimit = 5000;

		Loans loans = new Loans(null, loanLimit, null);
		when(accountRepository.findByUserId(userId)).thenReturn(loans);

		ResponseEntity<String> response = accountService.getLoanLimit(userId);

		// Verify response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Your limit is " + loanLimit, response.getBody());
	}

	@Test
	public void testGetLoanLimit_noLoanFound_badRequest() {
		Long userId = 1L;

		when(accountRepository.findByUserId(userId)).thenReturn(null);

		ResponseEntity<String> response = accountService.getLoanLimit(userId);

		// Verify response
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Check Loan Limit Failed", response.getBody());
	}

	@Test()
	public void testGetLoanLimit_repositoryException_throwsException() throws Exception {
		Long userId = 1L;

		when(accountRepository.findByUserId(userId)).thenThrow(new Exception());

		accountService.getLoanLimit(userId);
	}

}
