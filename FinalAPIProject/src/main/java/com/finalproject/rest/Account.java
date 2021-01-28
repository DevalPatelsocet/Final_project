package com.finalproject.rest;

public class Account {
	private String closeDate;
	private String status;
	private int accountId;
	private int count;
	private String productName;
	private double availBalance;
	private String lastActivtiyDate;
	private String openDate;
	private double pendingBalance;
	private int custId;
	private int openBranchId;
	private int openEmpId;
	private String productCD;

	public double getAvailBalance() {
		return availBalance;
	}

	public void setAvailBalance(double availBalance) {
		this.availBalance = availBalance;
	}

	public String getLastActivtiyDate() {
		return lastActivtiyDate;
	}

	public void setLastActivtiyDate(String lastActivtiyDate) {
		this.lastActivtiyDate = lastActivtiyDate;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public double getPendingBalance() {
		return pendingBalance;
	}

	public void setPendingBalance(double pendingBalance) {
		this.pendingBalance = pendingBalance;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public int getOpenBranchId() {
		return openBranchId;
	}

	public void setOpenBranchId(int openBranchId) {
		this.openBranchId = openBranchId;
	}

	public int getOpenEmpId() {
		return openEmpId;
	}

	public void setOpenEmpId(int openEmpId) {
		this.openEmpId = openEmpId;
	}

	public String getProductCD() {
		return productCD;
	}

	public void setProductCD(String productCD) {
		this.productCD = productCD;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
