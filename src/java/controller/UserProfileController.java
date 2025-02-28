package controller;

import dao.UserDAO;
import entity.Account;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/userProfile")
@MultipartConfig
public class UserProfileController extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        try {
            Account account = (Account) session.getAttribute("account");
            int accountId = account.getAccountId();

            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String oldImage = request.getParameter("oldImage");
            Part filePart = request.getPart("image");
            String fileName = oldImage;

            if (filePart != null && filePart.getSize() > 0) {
                fileName = UUID.randomUUID().toString() + "_" + filePart.getSubmittedFileName();
                String uploadPath = getServletContext().getRealPath("") + "uploads";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                filePart.write(uploadPath + File.separator + fileName);
            }

            boolean updated = userDAO.updateUser(accountId, fullName, phone, address, fileName);
            if (updated) {
                session.setAttribute("successMessage", "Cập nhật thông tin thành công!");
                User updatedUser = userDAO.getUserById(accountId);
                session.setAttribute("user", updatedUser);
            } else {
                session.setAttribute("errorMessage", "Cập nhật thất bại!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Có lỗi xảy ra!");
        }
        response.sendRedirect("UserProfile.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        try {
            Account account = (Account) session.getAttribute("account");
            int accountId = account.getAccountId();
            User user = userDAO.getUserById(accountId);
            session.setAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Có lỗi xảy ra!");
        }
        response.sendRedirect("UserProfile.jsp");
    }
}
