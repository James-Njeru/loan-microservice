package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Loans;
import com.example.model.Repayment;
import com.example.service.LoanService;

@RestController
@RequestMapping("api/v1")
public class LoanController {
	@Autowired
	LoanService loanService;

	@PostMapping("/request-loan")
	public ResponseEntity<Loans> requestLoan(@RequestBody Loans loans) {
		return loanService.requestLoan(loans);
	}

	@PostMapping("/loan-payment")
	public ResponseEntity<String> updateLoanBalance(@RequestBody Repayment repayment) {
		return loanService.updateLoanBalance(repayment);
	}
}
