package com.flywithme.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class AppConfig {

	private static final String HOSTNAME = "jdbc:mysql://dbms1.ckz0sfiltvup.us-east-2.rds.amazonaws.com:3306/flywithme";
	private static final String USERNAME = "";
	private static final String PASSWORD = "";

	private static Statement st = null;
	private static java.sql.PreparedStatement ps = null;
	private static Connection con = null;

	public static Connection getConnection() {

		if (con != null) {
			return con;
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(HOSTNAME, USERNAME, PASSWORD);
			st = con.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static Statement getStatement() {

		try {
			st = getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;

	}

	public static PreparedStatement getPreparedStatement(String sql) {

		try {
			ps = getConnection().prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;

	}

	public static StrongPasswordEncryptor passwordEncryptor() {
		return new StrongPasswordEncryptor();
	}

	public static boolean checkPassword(String pass1, String pass2) {
		return passwordEncryptor().checkPassword(pass1, pass2);
	}
}
