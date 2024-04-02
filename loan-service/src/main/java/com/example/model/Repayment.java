package com.example.model;

public class Repayment {
	private Long loanId;
	private double amount;

	public Repayment() {
	}

	public Repayment(Long loanId, double amount) {
		this.loanId = loanId;
		this.amount = amount;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
