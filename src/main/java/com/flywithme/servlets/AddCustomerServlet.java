package com.flywithme.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flywithme.app.AppConfig;
import com.flywithme.app.EmailService;

/**
 * Servlet implementation class AddCustomerServlet
 */
public class AddCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String insertUser = "insert into user values(?,?,?,?,?,?)";
	private static final String insertCust = "insert into customer values(?,?,?,?,?,?,?,?)";
	private boolean registrationSuccess = true;
	public AddCustomerServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String address = request.getParameter("address");
		long zipcode = Long.parseLong(request.getParameter("zipcode"));
		long contactNo = Long.parseLong(request.getParameter("contactNo"));
		long creditCardNo = Long.parseLong(request.getParameter("creditCardNo"));
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		request.setAttribute("fName", firstName);

		int userId = 0;

		PreparedStatement ps = AppConfig.getPreparedStatement(insertUser);
		Statement st = AppConfig.getStatement();
		String encryptedPassword = AppConfig.passwordEncryptor().encryptPassword(password);

		try {

			ps.setString(1, null);
			ps.setString(2, email);
			ps.setString(3, encryptedPassword);
			ps.setString(4, firstName);
			ps.setString(5, lastName);
			ps.setString(6, "USER");

			ps.executeUpdate();

			ResultSet rs = st.executeQuery("select user_id from user where email='" + email + "'");

			while (rs.next()) {
				userId = Integer.parseInt(rs.getString("user_id"));
			}

			ps = AppConfig.getPreparedStatement(insertCust);

			ps.setInt(1, userId);
			ps.setDate(2, new java.sql.Date(new Date().getTime()));
			ps.setString(3, address);
			ps.setLong(4, creditCardNo);
			ps.setLong(5, zipcode);
			ps.setLong(6, contactNo);
			ps.setInt(7, 0);
			ps.setInt(8, 0);
			ps.executeUpdate();

			rs.close();
			ps.close();

		} catch (SQLException e1) {
			e1.printStackTrace();
			registrationSuccess = false;
		}
		
		if (registrationSuccess) {
			EmailService.sendEmail(email, "Successful Registration in FlyWithMe", "Welcome " + firstName
					+ ",\n\nCongratulations on your successful registration.\n Now enjoy the latest offers and cheapest flights on FLyWithme.",
					"cs539check@gmail.com", "cs539check@gmail.com", "Checkingcs539");
		}

		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println("User " + firstName + " added");

	}

}
