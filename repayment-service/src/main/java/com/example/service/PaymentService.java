package com.example.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dao.PaymentRepository;
import com.example.feign.PaymentInterface;
import com.example.model.Payments;
import com.example.model.Repayment;

@Service
public class PaymentService {
	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	PaymentInterface paymentInterface;

	public ResponseEntity<Payments> repayLoan(Repayment repayment) {

		// call the updateLoanBalance from Loan Service
		ResponseEntity<String> response = paymentInterface.updateLoanBalance(repayment);

		if (response.getStatusCode().is2xxSuccessful()) {
			Payments payment = new Payments();
			payment.setLoanId(repayment.getLoanId());
			payment.setAmount(repayment.getAmount());
			payment.setPaymentDate(LocalDateTime.now());

			paymentRepository.save(payment);
			return ResponseEntity.ok(payment);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}
}
