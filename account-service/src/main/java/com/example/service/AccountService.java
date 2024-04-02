package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.dao.AccountRepository;
import com.example.model.Loans;

@Service
public class AccountService {
	@Autowired
	AccountRepository accountRepository;

	public ResponseEntity<String> getLoanBalance(Long userId) {
		try {
			Loans loans = accountRepository.findByUserId(userId);
			return new ResponseEntity<>("Your loan is " + String.valueOf(loans.getAmount()), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Check Balance Failed", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> getLoanLimit(Long userId) {
		try {
			Loans loans = accountRepository.findByUserId(userId);
			return new ResponseEntity<>("Your limit is " + String.valueOf(loans.getLoanLimit()), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Check Loan Limit Failed", HttpStatus.BAD_REQUEST);
	}
}
