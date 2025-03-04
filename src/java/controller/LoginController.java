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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private Connection conn;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            AccountDAO accountDAO = new AccountDAO();
            Account account = accountDAO.login(username, password);

            if (account == null || !account.getPassword().equals(password)) {
                String errorScript = "<script>alert('Sai tên đăng nhập hoặc mật khẩu!'); window.location='Login.jsp';</script>";
                response.getWriter().println(errorScript);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("account", account); // Lưu Object Account
                session.setAttribute("accountId", account.getAccountId()); // Lưu ID

                session.setMaxInactiveInterval(1000);

                if (account.isRole()) {
                    response.sendRedirect("adminDashboard.jsp");
                } else {
                    session.setAttribute("account", account);
                    session.setMaxInactiveInterval(1000);
                    response.sendRedirect("home");
                    //request.getRequestDispatcher("Home.jsp").forward(request, response);

                }
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
