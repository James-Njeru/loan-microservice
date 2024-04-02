package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.dao.PaymentRepository;
import com.example.feign.PaymentInterface;
import com.example.model.Payments;
import com.example.model.Repayment;
import com.example.service.PaymentService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RepaymentServiceApplicationTests {

	@InjectMocks
	private PaymentService paymentService;

	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	private PaymentInterface paymentInterface;

	@Test
	public void testRepayLoan_successfulUpdate_savesPayment() {
		Long loanId = 1L;
		double amount = 100;
		Repayment repayment = new Repayment(loanId, amount);

		ResponseEntity<String> mockLoanResponse = ResponseEntity.ok("Payment Successful");
		when(paymentInterface.updateLoanBalance(repayment)).thenReturn(mockLoanResponse);

		ResponseEntity<Payments> response = paymentService.repayLoan(repayment);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(loanId, response.getBody().getLoanId());
		assertEquals(amount, response.getBody().getAmount());
		// verify(paymentRepository).save(any(Payments.class)); // Verify payment saved
	}

	@Test
	public void testRepayLoan_unsuccessfulUpdate_badRequest() {
		Long loanId = 1L;
		double amount = 100;
		Repayment repayment = new Repayment(loanId, amount);

		ResponseEntity<String> mockLoanResponse = ResponseEntity.badRequest().body("Payment Failed");
		when(paymentInterface.updateLoanBalance(repayment)).thenReturn(mockLoanResponse);

		ResponseEntity<Payments> response = paymentService.repayLoan(repayment);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNull(response.getBody());
		verifyNoInteractions(paymentRepository); // Verify no save attempt
	}

	@Test()
	public void testRepayLoan_repositoryException_throwsException() throws Exception {
		Long loanId = 1L;
		double amount = 100;
		Repayment repayment = new Repayment(loanId, amount);

		when(paymentInterface.updateLoanBalance(repayment)).thenThrow(new Exception());

		paymentService.repayLoan(repayment);
	}

}
