package com.finalproject.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

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
}
