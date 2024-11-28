package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;

//import com.example.util.DBConnection;
import com.example.util.DatabaseUtil;

public class UserDAO {
	public static String authenticateUser(String username, String password) {
		try (Connection conn = DatabaseUtil.getConnection()) {
			String query = "SELECT u.password, r.role_name FROM users u JOIN roles r ON u.role_id = r.role_id WHERE u.username = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String storedPassword = rs.getString("password");
				String role = rs.getString("role_name");

				if (BCrypt.checkpw(password, storedPassword)) {
					return role; // Return the role if authenticated
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null; // Return null if authentication fails
	}
}
