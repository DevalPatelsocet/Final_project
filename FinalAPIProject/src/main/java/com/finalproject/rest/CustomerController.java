package com.finalproject.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("customer")
public class CustomerController {
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
	public Response createCustomer(Customer customer) {
		Sqlconnector connection = new Sqlconnector();

		con = connection.getConnection();

		int status = 200;
		try {

			String query = "INSERT INTO customer(CUST_ID,ADDRESS,CITY,CUST_TYPE_CD,FED_ID,POSTAL_CODE,STATE) VALUES (?,?,?,?,?,?,?);";

			preparedStatement = con.prepareStatement(query);

			preparedStatement.setInt(1, customer.getId());
			preparedStatement.setString(2, customer.getAddress());
			preparedStatement.setString(3, customer.getCity());
			preparedStatement.setString(4, customer.getCustType());
			preparedStatement.setString(5, customer.getFedId());
			preparedStatement.setString(6, customer.getPostalCode());
			preparedStatement.setString(7, customer.getState());

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

	@GET
	@Path("/getCustomer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer(@PathParam("id") String custId) throws JsonProcessingException {
		Sqlconnector connection = new Sqlconnector();
		con = connection.getConnection();
		ObjectMapper mapper = new ObjectMapper();

		Customer customer = new Customer();
		try {
			stmt = con.createStatement();
			String query = "SELECT * FROM customer c \r\n"
					+ "	LEFT OUTER JOIN officer o on c.CUST_ID = o.CUST_ID \r\n"
					+ "	LEFT OUTER JOIN business b on c.CUST_ID = b.CUST_ID \r\n"
					+ "	LEFT OUTER JOIN individual i ON c.CUST_ID = i.CUST_ID\r\n"
					+ " WHERE c.CUST_ID = "+custId+";";
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				customer.setAddress(rs.getString("c.ADDRESS"));
				customer.setId(rs.getInt("c.CUST_ID"));
				customer.setCity(rs.getString("c.CITY"));
				customer.setCustType(rs.getString("c.CUST_TYPE_CD"));
				customer.setFedId(rs.getString("c.FED_ID"));
				customer.setPostalCode(rs.getString("c.POSTAL_CODE"));
				customer.setState(rs.getString("c.STATE"));
				customer.setOfficerId(rs.getString("o.OFFICER_ID"));
				customer.setEndDate(rs.getString("o.END_DATE"));
				customer.setStartDate(rs.getString("o.START_DATE"));
				customer.setFirstName(rs.getString("i.FIRST_NAME"));
				customer.setLastName(rs.getString("i.LAST_NAME"));
				customer.setOffFirstName(rs.getString("o.FIRST_NAME"));
				customer.setOffLastName(rs.getString("o.LAST_NAME"));
				customer.setTitle(rs.getString("o.TITLE"));
				customer.setInCorpDate(rs.getString("b.INCORP_DATE"));
				customer.setBusineesName(rs.getString("b.NAME"));
				customer.setBusinessStateId(rs.getString("b.STATE_ID"));
				customer.setBirthDate(rs.getString("i.BIRTH_DATE"));

			}
		} catch (SQLException e) {
			System.out.println("SQL Exception : " + e.getMessage());
		} finally {
			try {
				con.close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				System.out.println("Finally Block SQL Exception : " + e.getMessage());
			}
		}

		return Response.status(200).entity(mapper.writeValueAsString(customer)).build();
	}
	
	@POST
	@Path("/update_address")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCustomer(Customer customer) {
		Sqlconnector connection = new Sqlconnector();

		con = connection.getConnection();

		int status = 200;
		try {

			String query = "update customer set ADDRESS = ? where CUST_ID = ?;";

			preparedStatement = con.prepareStatement(query);

			preparedStatement.setInt(2, customer.getId());
			preparedStatement.setString(1, customer.getAddress());

			int rowCount = preparedStatement.executeUpdate();

			if (rowCount > 0) {
				System.out.println("Record Update Successfully");

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
