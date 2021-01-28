package com.finalproject.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

public class EmployeeController {
	
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	JSONObject mainobj = new JSONObject();
	JSONArray jsonArray = new JSONArray();
	JSONObject childObj = new JSONObject();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateDepartmentByEmployee")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateDepartmentOfEmployee(Employee employees) {
		Sqlconnector connection = new Sqlconnector();
		con = connection.getConnection();
		Status status = Status.OK;
		try {
			String query = "UPDATE `midterm`.`employee` SET `DEPT_ID` =? WHERE `EMP_ID` =?";

			preparedStatement = con.prepareStatement(query);

			preparedStatement.setInt(1, employees.getDepartmentId());
			preparedStatement.setInt(2, employees.getEmployeeId());

			int rawCount = preparedStatement.executeUpdate();

			if (rawCount > 0) {
				status = Status.OK;
				mainobj.accumulate("Status", Status.OK);
				mainobj.accumulate("Message", "Data Successfully Updated!!");
			} else {
				status = Status.NOT_MODIFIED;
				mainobj.accumulate("Status", status);
				mainobj.accumulate("Message", "Something went wrong!!!");

			}

		} catch (SQLException e) {
			e.printStackTrace();
			status = Status.NOT_MODIFIED;
			mainobj.accumulate("Status", status);
			mainobj.accumulate("Message", "Something went wrong!!!");
		}

		return Response.status(status).entity(mainobj.toString()).build();
	}
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createEmployee(Employee employee) {
		Sqlconnector connection = new Sqlconnector();

		con = connection.getConnection();

		int status = 200;
		try {

			String query = "INSERT INTO employee(EMP_ID,END_DATE,FIRST_NAME,LAST_NAME,START_DATE,TITLE,ASSIGNED_BRANCH_ID,DEPT_ID,SUPERIOR_EMP_ID)VALUES(?,?,?,?,?,?,?,?,?);";

			preparedStatement = con.prepareStatement(query);

			preparedStatement.setInt(1, employee.getEmployeeId());
			preparedStatement.setString(2, employee.getEnddate());
			preparedStatement.setString(3, employee.getFirstName());
			preparedStatement.setString(4, employee.getLastName());
			preparedStatement.setString(5, employee.getStartDate());
			preparedStatement.setString(6, employee.getTitle());
			preparedStatement.setInt(7, employee.getAssignBranchId());
			preparedStatement.setInt(8, employee.getDepartmentId());
			preparedStatement.setInt(9, employee.getSuperiorEmployeeId());

			int rowCount = preparedStatement.executeUpdate();

			if (rowCount > 0) {
				System.out.println("Record Inserted Successfully");

				mainobj.accumulate("status", 200);
				mainobj.accumulate("message", "Successfully Updated");
			} else {
				status = 500;
				mainobj.accumulate("status", 500);
				mainobj.accumulate("message", "Something Wrong");
			}

		} catch (SQLException e) {
			status = 500;
			mainobj.accumulate("status", 500);
			mainobj.accumulate("message", e.getMessage());
		} finally {
			try {
				con.close();
				preparedStatement.close();
			} catch (SQLException e) {
				System.out.println("Finally Block SQL Exception : " + e.getMessage());
			}
		}
		return Response.status(status).entity(mainobj.toString()).build();
	}

}
