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

import org.json.JSONArray;
import org.json.JSONObject;

@Path("officer")
public class OfficerController {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	JSONObject mainobj = new JSONObject();
	JSONArray jsonArray = new JSONArray();
	JSONObject childObj = new JSONObject();
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOfficer(Officer officer) {
		Sqlconnector connection = new Sqlconnector();

		con = connection.getConnection();

		int status = 200;
		try {

			String query = "INSERT INTO officer(FIRST_NAME,LAST_NAME,START_DATE,TITLE,CUST_ID)VALUES(?,?,?,?,?);";

			preparedStatement = con.prepareStatement(query);

			preparedStatement.setString(1, officer.getFirstName());
			preparedStatement.setString(2, officer.getLastName());
			preparedStatement.setString(3, officer.getStartDate());
			preparedStatement.setString(4, officer.getTitle());
			preparedStatement.setInt(5, officer.getCustId());

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
