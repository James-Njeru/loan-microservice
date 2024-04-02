package com.example.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Loans;

public interface LoanRepository extends JpaRepository<Loans, Long> {
	Optional<Loans> findByUserId(Long userId);
}
