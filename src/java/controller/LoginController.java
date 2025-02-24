/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import entity.Account;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private Connection conn;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Changed from username to user to match form parameter
        String username = request.getParameter("username");
        // Changed from password to pass to match form parameter
        String password = request.getParameter("password");

        AccountDAO dao = new AccountDAO();
        Account a = dao.login(username, password);

        if (a == null) {
            // Set error message and return to login page with script to show popup
            String errorScript = "<script>alert('Wrong username or password!'); window.location='Login.jsp';</script>";
            response.getWriter().println(errorScript);
        } else {
            HttpSession session = request.getSession();
//            session.setAttribute("acc", a);
//            session.setMaxInactiveInterval(1000);
            response.sendRedirect("home");
        }
    }

}
