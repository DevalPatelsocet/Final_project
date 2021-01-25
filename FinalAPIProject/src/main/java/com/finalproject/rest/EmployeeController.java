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
}
