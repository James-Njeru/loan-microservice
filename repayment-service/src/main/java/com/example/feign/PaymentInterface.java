package com.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.model.Repayment;

@FeignClient("LOAN-SERVICE")
public interface PaymentInterface {
	@PostMapping("api/v1/loan-payment")
	public ResponseEntity<String> updateLoanBalance(@RequestBody Repayment repayment);
}
