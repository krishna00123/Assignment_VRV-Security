<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    
    <style>
        /* Style for Admin Dashboard */
        .navbar {
            background-color: #333;
            padding: 15px;
            color: white;
            text-align: center;
        }

        .navbar a {
            color: white;
            padding: 14px 20px;
            text-decoration: none;
            text-align: center;
        }

        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }

        .content {
            margin: 20px;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
        }

        .card {
            background-color: #f9f9f9;
            border-radius: 8px;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
            margin: 15px 0;
            padding: 20px;
        }

        .admin-card {
            background-color: #4CAF50;
            color: white;
        }

        .button {
            background-color: #2196F3;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            margin-top: 10px;
        }

        .logout-btn {
            background-color: #f44336;
            color: white;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
            text-decoration: none;
            border-radius: 5px;
        }

        .logout-btn:hover {
            background-color: #d32f2f;
        }
    </style>
</head>
<body>

<div class="navbar">
    <a href="dashboard.jsp">Dashboard</a>
    <a href="manage-users.html">Manage Users</a>
   
    <a href="profile.jsp">Profile</a>
     <a href="${pageContext.request.contextPath}/logoutServlet" class="logout-btn">Logout</a>
</div>

<div class="content">
    <c:if test="${not empty sessionScope.username}">
        <h2>Welcome, Admin <b>${sessionScope.username}</b>!</h2>
    </c:if>

    <div class="admin-card card">
        <h3>Admin Options</h3>
        <p>As an admin, you have access to manage users, settings, and more.</p>
       <!--  <a href="manage-users.html" class="button">Manage Users</a> -->
        <a href="view-reports.jsp" class="button">View Reports</a>
    </div>
</div>

</body>
</html>
