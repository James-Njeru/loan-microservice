package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Loans;

public interface AccountRepository extends JpaRepository<Loans, Long> {
	Loans findByUserId(Long userId);
}
