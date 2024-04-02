package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Payments;
import com.example.model.Repayment;
import com.example.service.PaymentService;

@RestController
@RequestMapping("api/v1")
public class PaymentController {
	@Autowired
	PaymentService paymentService;

	@PostMapping("/payment")
	public ResponseEntity<Payments> repayLoan(@RequestBody Repayment repayment) {
		return paymentService.repayLoan(repayment);
	}
}
