package com.finalproject.rest;

public class Transaction {
	private double amount;
	private String fundsAvailDate;
	private String txnDate;
	private String txnTypeCd;
	private int accountId;
	private int executionBranchId;
	private int tellerEmpId;
	private int txnId;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getFundsAvailDate() {
		return fundsAvailDate;
	}

	public void setFundsAvailDate(String fundsAvailDate) {
		this.fundsAvailDate = fundsAvailDate;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getTxnTypeCd() {
		return txnTypeCd;
	}

	public void setTxnTypeCd(String txnTypeCd) {
		this.txnTypeCd = txnTypeCd;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getExecutionBranchId() {
		return executionBranchId;
	}

	public void setExecutionBranchId(int executionBranchId) {
		this.executionBranchId = executionBranchId;
	}

	public int getTellerEmpId() {
		return tellerEmpId;
	}

	public void setTellerEmpId(int tellerEmpId) {
		this.tellerEmpId = tellerEmpId;
	}

	public int getTxnId() {
		return txnId;
	}

	public void setTxnId(int txnId) {
		this.txnId = txnId;
	}

}
