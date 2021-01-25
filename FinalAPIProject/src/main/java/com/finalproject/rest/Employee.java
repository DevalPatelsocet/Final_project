package com.finalproject.rest;

public class Employee {

	private int employeeId;
	private String enddate;
	private String firstName;
	private String lastName;
	private String startDate;
	private String title;
	private int assignBranchId;
	private int departmentId;
	private int superiorEmployeeId;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAssignBranchId() {
		return assignBranchId;
	}

	public void setAssignBranchId(int assignBranchId) {
		this.assignBranchId = assignBranchId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getSuperiorEmployeeId() {
		return superiorEmployeeId;
	}

	public void setSuperiorEmployeeId(int superiorEmployeeId) {
		this.superiorEmployeeId = superiorEmployeeId;
	}

}