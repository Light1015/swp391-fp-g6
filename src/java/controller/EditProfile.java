package controller;

import dao.UserDAO;
import entity.Account;
import entity.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;

@WebServlet(name = "EditProfile", urlPatterns = {"/editProfile"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class EditProfile extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String oldImage = request.getParameter("oldImage");

        Part filePart = request.getPart("image");
        String fileName = (filePart != null) ? filePart.getSubmittedFileName() : "";
        String imagePath = (fileName == null || fileName.isEmpty()) ? oldImage : fileName;

        if (fileName != null && !fileName.isEmpty()) {
            String uploadDir = getServletContext().getRealPath("/uploads");
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            try {
                filePart.write(uploadDir + File.separator + fileName);
                imagePath = "uploads/" + fileName;
            } catch (IOException e) {
                session.setAttribute("errorMessage", "Lỗi khi lưu ảnh. Vui lòng thử lại!");
                response.sendRedirect("userProfile");
                return;
            }
        }

        UserDAO userDAO = new UserDAO();
        boolean success = false;
        try {
            success = userDAO.updateUser(account.getAccountId(), fullName, phone, address, imagePath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Lỗi hệ thống. Vui lòng thử lại sau!");
            response.sendRedirect("userProfile");
            return;
        }

        if (success) {
            try {
                User updatedUser = userDAO.getUserById(account.getAccountId());
                session.setAttribute("account", updatedUser);
                session.setAttribute("successMessage", "Cập nhật hồ sơ thành công.");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                session.setAttribute("errorMessage", "Lỗi khi tải lại thông tin người dùng.");
            }
        } else {
            session.setAttribute("errorMessage", "Cập nhật hồ sơ thất bại.");
        }

        response.sendRedirect("userProfile");
    }

    @Override
    public String getServletInfo() {
        return "Servlet chỉnh sửa thông tin người dùng";
    }
}
