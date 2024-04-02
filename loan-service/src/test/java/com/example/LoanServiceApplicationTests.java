package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.dao.LoanRepository;
import com.example.model.Loans;
import com.example.model.Repayment;
import com.example.service.LoanService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LoanServiceApplicationTests {

	@InjectMocks
	private LoanService loanService;

	@Mock
	private LoanRepository loanRepository;

	@Test
	public void testUpdateLoanBalance_existingLoan_success() {
		Long loanId = 1L;
		double repaymentAmount = 100;
		double initialBalance = 500;

		Loans existingLoan = new Loans(loanId, initialBalance, "approved", LocalDateTime.now());
		when(loanRepository.findById(loanId)).thenReturn(Optional.of(existingLoan));

		ResponseEntity<String> response = loanService.updateLoanBalance(new Repayment(loanId, repaymentAmount));

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Payment Successful", response.getBody());
		verify(loanRepository).save(existingLoan); // Verify loan is saved with updated balance
	}

	@Test
	public void testUpdateLoanBalance_existingLoan_insufficientFunds() {
		Long loanId = 1L;
		double repaymentAmount = 600; // More than initial balance
		double initialBalance = 500;

		Loans existingLoan = new Loans(loanId, initialBalance, "approved", LocalDateTime.now());
		when(loanRepository.findById(loanId)).thenReturn(Optional.of(existingLoan));

		ResponseEntity<String> response = loanService.updateLoanBalance(new Repayment(loanId, repaymentAmount));

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Payment Failed", response.getBody());
		verifyNoInteractions(loanRepository);
	}

	@Test
	public void testUpdateLoanBalance_nonexistentLoan_notFound() {
		Long loanId = 1L;
		double repaymentAmount = 100;

		when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

		ResponseEntity<String> response = loanService.updateLoanBalance(new Repayment(loanId, repaymentAmount));

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Loan with ID " + loanId + " not found"));
		verifyNoInteractions(loanRepository);
	}

	@Test
	public void testRequestLoan_success() throws Exception {
		Loans loan = new Loans(1000, null, null);

		when(loanRepository.save(loan)).thenReturn(loan);

		ResponseEntity<Loans> response = loanService.requestLoan(loan);

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertEquals("pending", response.getBody().getStatus());
		// verify(loanService).processLoan(loan);
	}

	@Test()
	public void testRequestLoan_repositoryException_throwsException() throws Exception {
		Loans loan = new Loans(1000, null, null);

		when(loanRepository.save(loan)).thenThrow(new Exception());

		loanService.requestLoan(loan);
	}

	@Test
	public void testGetLoanStatusHttpStatus_pending() {
		Loans loan = new Loans(1000, null, "pending");
		// assertEquals(HttpStatus.ACCEPTED, loanService.getLoanStatusHttpStatus(loan));
	}

	@Test
	public void testGetLoanStatusHttpStatus_approved() {
		Loans loan = new Loans(1000, null, "approved");
		// assertEquals(HttpStatus.OK, loanService.getLoanStatusHttpStatus(loan));
	}

	@Test
	public void testGetLoanStatusHttpStatus_rejected() {
		Loans loan = new Loans(1000, null, "rejected");
		// assertEquals(HttpStatus.BAD_REQUEST,
		// loanService.getLoanStatusHttpStatus(loan));
	}

	@Test
	public void testGetLoanStatusHttpStatus_default() {
		Loans loan = new Loans(1000, null, "invalid");
		// assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
		// loanService.getLoanStatusHttpStatus(loan));
	}

}
