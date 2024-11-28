package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.example.util.DatabaseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/manage_user")
public class ManageUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String userId = req.getParameter("user_id");
		String userIdD = req.getParameter("user_id1");

		try (Connection con = DatabaseUtil.getConnection()) {
			switch (action) {
			case "insert":
				insertUser(con, username, password, resp);
				break;
			case "update":
				updateUser(con, userId, username, password, resp);
				break;
			case "delete":
				deleteUser(con, userIdD, resp);
				break;
			default:
				resp.getWriter().write("Invalid action.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			resp.getWriter().write("An error occurred: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void insertUser(Connection con, String username, String password, HttpServletResponse resp)
			throws SQLException, IOException {
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		String query = "INSERT INTO users (username, password) VALUES (?, ?)";
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setString(1, username);
			stmt.setString(2, hashedPassword);
			stmt.executeUpdate();
			resp.getWriter().write("User inserted successfully.");
		}
	}

	private void updateUser(Connection con, String userId, String username, String password, HttpServletResponse resp)
			throws SQLException, IOException {
		if (userId == null || userId.isEmpty()) {
			resp.getWriter().write("User ID is required for update.");
			return;
		}
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		String query = "UPDATE users SET username = ?, password = ? WHERE user_id = ?";
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setString(1, username);
			stmt.setString(2, hashedPassword);
			stmt.setInt(3, Integer.parseInt(userId));
			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				resp.getWriter().write("User updated successfully.");
			} else {
				resp.getWriter().write("Error: User not found or could not be updated.");
			}
		}
	}

	private void deleteUser(Connection con, String userIdD, HttpServletResponse resp) throws SQLException, IOException {

		if (userIdD != null) {
			int userIdInt;
			try {
				System.out.println(userIdD + "Hello");
				userIdInt = Integer.parseInt(userIdD); // Convert user_id to integer
			} catch (NumberFormatException e) {
				resp.getWriter().write("Error: Invalid User ID format.");
				return;
			}

			String deleteQry = "DELETE FROM users WHERE user_id = ?";
			try (PreparedStatement stmt = con.prepareStatement(deleteQry)) {
				stmt.setInt(1, userIdInt); // Use integer userId for the delete query
				int rowsAffected = stmt.executeUpdate();
				if (rowsAffected > 0) {
					resp.getWriter().write("User deleted successfully!");
				} else {
					resp.getWriter().write("Error: User not found or could not be deleted.");
				}
			}
		}
	}

	// Method to stop MySQL's AbandonedConnectionCleanupThread during servlet
	// shutdown
	@Override
	public void destroy() {
		try {
			// Stop the MySQL Abandoned Connection Cleanup thread
			com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.uncheckedShutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.destroy(); // Calling the superclass destroy method to ensure the lifecycle is correctly
							// managed
	}
}
