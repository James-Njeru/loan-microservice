package com.example.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Loans {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private double amount;
	private String purpose;
	private String status;
	private double loanLimit;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Loans() {
	}

	public Loans(Long id, double amount, String status, LocalDateTime createdAt) {
		this.id = id;
		this.amount = amount;
		this.status = status;
		this.createdAt = createdAt;
	}

	public Loans(double amount, String purpose, String status) {
		this.amount = amount;
		this.purpose = purpose;
		this.status = status;
	}

	public Loans(Long id, Long userId, double amount, String purpose, String status, double loanLimit,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.userId = userId;
		this.amount = amount;
		this.purpose = purpose;
		this.status = status;
		this.loanLimit = loanLimit;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getLoanLimit() {
		return loanLimit;
	}

	public void setLoanLimit(double loanLimit) {
		this.loanLimit = loanLimit;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Loans [id=" + id + ", userId=" + userId + ", amount=" + amount + ", purpose=" + purpose + ", status="
				+ status + ", loanLimit=" + loanLimit + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
