package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;  // If using BCrypt for password hashing

import com.example.util.DatabaseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DatabaseUtil.getConnection()) {
            // Query to get the username, password, and role
            String query = "SELECT username, password, role FROM users WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String dbPassword = rs.getString("password"); // Password stored in DB
                        String role = rs.getString("role");

                        // BCrypt to compare hashed passwords:
                        if (BCrypt.checkpw(password, dbPassword)) {
                            // Password matches
                            HttpSession session = request.getSession();
                            session.setAttribute("username", username);
                            session.setAttribute("role", role);

                            // Redirect based on role
                            if ("ADMIN".equals(role)) {
                                response.sendRedirect("admin/dashboard.jsp");
                            } else {
                                response.sendRedirect("user/dashboard.jsp");
                            }
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Invalid credentials, redirect back to login with error
        response.sendRedirect("login.jsp?error=Invalid+credentials");
    }
}
