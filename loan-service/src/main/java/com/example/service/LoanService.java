package com.example.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.dao.LoanRepository;
import com.example.model.Loans;
import com.example.model.Repayment;

@Service
public class LoanService {
	@Autowired
	LoanRepository loanRepository;

	public ResponseEntity<String> updateLoanBalance(Repayment repayment) {
		Optional<Loans> optionalLoan = loanRepository.findById(repayment.getLoanId());

		if (optionalLoan.isPresent()) {
			try {
				Loans loans = optionalLoan.get();
				double newBalance = loans.getAmount() - repayment.getAmount();
				loans.setAmount(newBalance);

				loanRepository.save(loans);
				return new ResponseEntity<>("Payment Successful", HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseEntity<>("Payment Failed", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>("Loan with ID " + repayment.getLoanId() + " not found", HttpStatus.BAD_REQUEST);
		}
	}

	@Async
	@Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 5000))
	public ResponseEntity<Loans> requestLoan(Loans loans) {
		loans.setStatus("pending");
		loans.setCreatedAt(LocalDateTime.now());

		try {
			Loans savedLoan = loanRepository.save(loans);
			processLoan(loans);
			return ResponseEntity.status(getLoanStatusHttpStatus(loans)).body(savedLoan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}

	private void processLoan(Loans loans) {
		loans.setStatus("approved");
		loans.setUpdatedAt(LocalDateTime.now());
		loanRepository.save(loans);
	}

	private HttpStatus getLoanStatusHttpStatus(Loans loans) {
		switch (loans.getStatus()) {
		case "pending":
			return HttpStatus.ACCEPTED; // 202
		case "approved":
			return HttpStatus.OK; // 200
		case "rejected":
			return HttpStatus.BAD_REQUEST; // 400
		default:
			return HttpStatus.INTERNAL_SERVER_ERROR; // 500
		}
	}

}
