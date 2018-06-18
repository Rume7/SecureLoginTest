package com.codehacks.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.codehacks.pojo.User;

public class UserHelper {
	
	private PreparedStatement authenticateUserStatement;

	/**
	 * 
	 */
	public UserHelper() {
		try {
			// set up connection
			Class.forName("com.mysql.jdbc.Driver");
			String jdbcUrl = "jdbc:mysql://localhost/securelogin";
			String mysqlUsername = "scott";
			String mysqlPassword = "tiger";
			Connection conn = DriverManager.getConnection(jdbcUrl, mysqlUsername, mysqlPassword);
			
			// Create the prepared statement(s)
			String sqlStatement = "SELECT * FROM user WHERE username = ? AND password = ?";
			authenticateUserStatement = conn.prepareStatement(sqlStatement);
		} catch(Exception e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	public User authenticateUser(String username, String password) {
		User user = null;
		try {
			// Add parameters to the ?'s in the prepared statements.
			authenticateUserStatement.setString(1, username);
			authenticateUserStatement.setString(2, password);
			ResultSet result = authenticateUserStatement.executeQuery();
			
			// if we've returned a row, turn that row into a new user object
			if (result.next()) {
				user = new User(result.getInt("userID"), result.getString("username"), 
						result.getString("password"));
			}
		} catch (SQLException e) {
			
		}
		return user;
	}
}
