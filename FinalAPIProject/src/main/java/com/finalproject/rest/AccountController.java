package com.finalproject.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("account")
public class AccountController {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	JSONObject mainobj = new JSONObject();
	JSONArray jsonArray = new JSONArray();
	JSONObject childObj = new JSONObject();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/close")
	@Produces(MediaType.APPLICATION_JSON)
	public Response closeAccount(Account account) {
		Sqlconnector connection = new Sqlconnector();
		con = connection.getConnection();
		Status status = Status.OK;
		try {
			String query = "UPDATE account SET CLOSE_DATE = ?, STATUS = ? WHERE ACCOUNT_ID = ?;";

			preparedStatement = con.prepareStatement(query);

			preparedStatement.setString(1, account.getCloseDate());
			preparedStatement.setString(2, account.getStatus());
			preparedStatement.setInt(3, account.getAccountId());

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
	
	@GET
	@Path("/accountsbyproduct")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccountByProduct() throws JsonProcessingException {
		Sqlconnector connection = new Sqlconnector();
		con = connection.getConnection();
		ObjectMapper mapper = new ObjectMapper();

		List<Account> accountList = new ArrayList<Account>();
		Account account=null;
		try {
			stmt = con.createStatement();
			String query = "SELECT count(a.ACCOUNT_ID) cnt,p.NAME FROM account a INNER JOIN product p ON a.PRODUCT_CD = p.PRODUCT_CD";
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				account = new Account();
				account.setCount(rs.getInt("cnt"));
				account.setProductName(rs.getString("c.NAME"));
				accountList.add(account);

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

		return Response.status(200).entity(mapper.writeValueAsString(account)).build();
	}
	
	@GET
	@Path("/accountsbyopendate/{date}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccountByOpenDate(@PathParam("date") String date) throws JsonProcessingException {
		Sqlconnector connection = new Sqlconnector();
		con = connection.getConnection();
		ObjectMapper mapper = new ObjectMapper();

		List<Account> accountList = new ArrayList<Account>();
		try {
			stmt = con.createStatement();
			String query = "select * from account where OPEN_DATE>"+date;
			System.out.println(query);
			rs = stmt.executeQuery(query);
			Account account;
			while (rs.next()) {
				account = new Account();
				account.setCloseDate(rs.getString("CLOSE_DATE"));
				account.setStatus(rs.getString("OPEN_DATE"));
				account.setAccountId(rs.getInt("ACCOUNT_ID"));
				account.setAvailBalance(rs.getDouble("AVAIL_BALANCE"));
				account.setLastActivtiyDate(rs.getString("LAST_ACTIVITY_DATE"));
				account.setOpenDate(rs.getString("OPEN_DATE"));
				account.setPendingBalance(rs.getDouble("PENDING_BALANCE"));
				account.setCustId(rs.getInt("CUST_ID"));
				account.setOpenBranchId(rs.getInt("OPEN_BRANCH_ID"));
				account.setOpenEmpId(rs.getInt("OPEN_EMP_ID"));
				account.setProductCD(rs.getString("PRODUCT_CD"));
				accountList.add(account);

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

		return Response.status(200).entity(mapper.writeValueAsString(accountList)).build();
	}

}
