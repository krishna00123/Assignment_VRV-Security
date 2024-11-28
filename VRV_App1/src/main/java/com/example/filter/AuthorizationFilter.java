package com.example.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String uri = httpRequest.getRequestURI();
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if ((uri.contains("/admin/") && !"ADMIN".equals(role)) || (uri.contains("/user/") && !"USER".equals(role))) {
            httpResponse.sendRedirect("../login.jsp?error=Access Denied");
            return;
        }

        chain.doFilter(request, response);
    }
}
