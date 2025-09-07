package com.cms.servlet;

import java.io.*;
import java.sql.*;
import com.cms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
        String uname = request.getParameter("username");
        String pass = request.getParameter("password");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?"
            );
            ps.setString(1, uname);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                HttpSession session = request.getSession();
                session.setAttribute("username", uname);
                session.setAttribute("role", role);

                if (role.equals("admin")) {
                    response.sendRedirect("adminDashboard.jsp");
                } else {
                    response.sendRedirect("userDashboard.jsp");
                }
            } else {
                out.println("<h2>Invalid Username or Password</h2>");
                out.println("<a href='index.jsp'>Try Again</a>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}
