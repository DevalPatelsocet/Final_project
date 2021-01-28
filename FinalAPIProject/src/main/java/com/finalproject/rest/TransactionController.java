package com.finalproject.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class TransactionController {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	JSONObject mainobj = new JSONObject();
	JSONArray jsonArray = new JSONArray();
	JSONObject childObj = new JSONObject();

	@GET
	@Path("/deleteTransaction/{id}")
	public Response deleteEmployee(@PathParam("id") int id) {
		Sqlconnector connection = new Sqlconnector();
		con = connection.getConnection();
		int status = 200;
		try {
			String query = "delete from acc_transaction where TXN_ID = " + id;

			stmt = con.createStatement();
			stmt.execute(query);
			mainobj.accumulate("Message", "Success");
			mainobj.accumulate("Status", "200");

		} catch (SQLException e) {
			mainobj.accumulate("Message", "Error!" + e.getMessage());
			mainobj.accumulate("Status", "500");
			status = 500;
			System.out.println("SQL Exception : " + e.getMessage());
		} finally {
			try {
				con.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println("Finally Block SQL Exception : " + e.getMessage());
			}
		}
		return Response.status(status).entity(mainobj.toString()).build();
	}

	@GET
	@Path("/getTransactions/{from}/{to}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTransactions(@PathParam("from") String from, @PathParam("to") String to) {
		Sqlconnector connection = new Sqlconnector();
		con = connection.getConnection();

		try {
			stmt = con.createStatement();
			String query = "select x.TXN_ID,x.AMOUNT,x.FUNDS_AVAIL_DATE,x.TXN_DATE,x.TXN_TYPE_CD,a.CUST_ID,i.FIRST_NAME,i.LAST_NAME from acc_transaction x,account a ,customer c,individual i where x.ACCOUNT_ID = a.ACCOUNT_ID and a.CUST_ID = c.CUST_ID and i.CUST_ID = c.CUST_ID and TXN_DATE between \""
					+ from + "\" and \"" + to + "\";";
			rs = stmt.executeQuery(query);
			System.out.println(query);
			while (rs.next()) {
				childObj = new JSONObject();

				childObj.accumulate("Trasaction ID", rs.getString("x.TXN_ID"));
				childObj.accumulate("Amount", rs.getString("x.AMOUNT"));
				childObj.accumulate("Funds Available Date", rs.getString("x.FUNDS_AVAIL_DATE"));
				childObj.accumulate("Transaction Date", rs.getString("x.TXN_DATE"));
				childObj.accumulate("Transaction Type Code", rs.getString("x.TXN_TYPE_CD"));
				childObj.accumulate("Customer ID", rs.getString("a.CUST_ID"));
				childObj.accumulate("First Name", rs.getString("i.FIRST_NAME"));
				childObj.accumulate("Last Name", rs.getString("i.LAST_NAME"));

				jsonArray.put(childObj);

			}
			mainobj.put("Transactions", jsonArray);
			// mainobj.put("individual", jsonArray);
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

		return Response.status(200).entity(mainobj.toString()).build();
	}
	
	@POST
	@Path("/deposit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deposit(Transaction transaction) {
		Sqlconnector connection = new Sqlconnector();

		con = connection.getConnection();

		int status = 200;
		try {

			String query = "UPDATE account set AVAIL_BALANCE = AVAIL_BALANCE+?  WHERE ACCOUNT_ID = ?;";
			
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setDouble(1, transaction.getAmount());
			preparedStatement.setInt(2, transaction.getAccountId());

			int rowCount = preparedStatement.executeUpdate();
			
			query = "INSERT INTO acc_transaction(AMOUNT,FUNDS_AVAIL_DATE,TXN_DATE,TXN_TYPE_CD,ACCOUNT_ID) VALUES(?,?,?,?,?);";

			preparedStatement = con.prepareStatement(query);

			preparedStatement.setDouble(1, transaction.getAmount());
			preparedStatement.setString(2, LocalDate.now().toString());
			preparedStatement.setString(3, LocalDate.now().toString());
			preparedStatement.setString(4, transaction.getTxnTypeCd());
			preparedStatement.setInt(5, transaction.getAccountId());

			rowCount = preparedStatement.executeUpdate();
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
	@Path("/transactionbyamount/{amount}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTransactionByAmount(@PathParam("amount") double amount) throws JsonProcessingException {
		Sqlconnector connection = new Sqlconnector();
		con = connection.getConnection();
		ObjectMapper mapper = new ObjectMapper();

		List<Transaction> transactionList = new ArrayList<Transaction>();
		try {
			stmt = con.createStatement();
			String query = "SELECT ACCOUNT_ID FROM acc_transaction WHERE AMOUNT >= "+amount;
			System.out.println(query);
			rs = stmt.executeQuery(query);
			Transaction transaction;
			while (rs.next()) {
				transaction = new Transaction();
				transaction.setAccountId(rs.getInt("ACCOUNT_ID"));
				transactionList.add(transaction);
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

		return Response.status(200).entity(mapper.writeValueAsString(transactionList)).build();
	}
	
	@GET
	@Path("/branchwisetotal/{branch}/{from}/{to}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBranchWiseTotal(@PathParam("branch") String branch,@PathParam("from") String from,@PathParam("to") String to) throws JsonProcessingException {
		Sqlconnector connection = new Sqlconnector();
		con = connection.getConnection();
		try {
			stmt = con.createStatement();
			String query = "select SUM(t.AMOUNT) all_amount from acc_transaction t,branch b where b.Name = \""+branch+"\" and t.TXN_DATE between \""+from+"\" and \""+to+"\"";			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				mainobj.put("Total", rs.getInt("all_amount"));
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

		return Response.status(200).entity(mainobj.toString()).build();
	}
}
