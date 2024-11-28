<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Use the implicit 'session' object provided by JSP
    String username = (session != null) ? (String) session.getAttribute("username") : null;
    String role = (session != null) ? (String) session.getAttribute("role") : null;
%>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome to the Application</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
        }
        header {
            background-color: #333;
            color: #fff;
            padding: 10px 20px;
            text-align: center;
        }
        main {
            margin: 20px;
            text-align: center;
        }
        a {
            display: inline-block;
            margin: 10px;
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
        }
        a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <header>
        <h1>Welcome to VRV App</h1>
    </header>
    <main>
        <% if (username != null && role != null) { %>
            <h2>Hello, <%= username %>!</h2>
            <p>You are logged in as <strong><%= role %></strong>.</p>
            <a href="<%= role.equals("ADMIN") ? "admin/dashboard.jsp" : "user/dashboard.jsp" %>">Go to Dashboard</a>
            <a href="LogoutServlet">Logout</a>
        <% } else { %>
            <h2>Welcome to the Application</h2>
            <p>Please log in to access the system.</p>
            <a href="login.jsp">Login</a>
        <% } %>
    </main>
</body>
</html>
