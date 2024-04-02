package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Payments;

public interface PaymentRepository extends JpaRepository<Payments, Long> {

}
