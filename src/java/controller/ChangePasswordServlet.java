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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/ChangePasswordServlet"})
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        Integer accountId = account.getAccountId();

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            session.setAttribute("errorMessage", "Mật khẩu mới và xác nhận không khớp.");
            response.sendRedirect("UserProfile.jsp?tab=changePassword");
            return;
        }

        if (newPassword.length() < 8 || !newPassword.matches(".*[A-Z].*") || !newPassword.matches(".*\\d.*")) {
            session.setAttribute("errorMessage", "Mật khẩu phải có ít nhất 8 ký tự, một chữ hoa và một số.");
            response.sendRedirect("UserProfile.jsp?tab=changePassword");
            return;
        }

        AccountDAO accountDAO = new AccountDAO();

        try {
            boolean isPasswordChanged = accountDAO.changePassword(accountId, currentPassword, newPassword);

            if (isPasswordChanged) {
                session.setAttribute("successMessage", "Đổi mật khẩu thành công.");
                response.sendRedirect("UserProfile.jsp?tab=changePassword");
            } else {
                session.setAttribute("errorMessage", "Mật khẩu hiện tại không đúng.");
                response.sendRedirect("UserProfile.jsp?tab=changePassword");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            session.setAttribute("errorMessage", "Đã xảy ra lỗi. Vui lòng thử lại sau.");
            response.sendRedirect("UserProfile.jsp?tab=changePassword");
        }
    }
}
