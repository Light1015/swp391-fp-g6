package controller;

import dao.InteractionDAO;
import entity.Account;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author SE18-CE181003-Lam Huy Hoang
 */
@WebServlet(name = "ReportCommentController", urlPatterns = {"/ReportCommentController"})
public class ReportCommentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Check if user is logged in
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        try {

            int interactionId = Integer.parseInt(request.getParameter("interaction_id"));
            int bookId = Integer.parseInt(request.getParameter("book_id"));

            InteractionDAO interactionDAO = new InteractionDAO();
            boolean success = interactionDAO.reportComment(interactionId, 1);

            // Mark the comment as reported in Interaction table
            if (success) {
                request.setAttribute("successMessage", "Comment has been reported successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to report comment. Please try again.");
            }

            // Redirect back to book detail page
            request.getRequestDispatcher("BookDetail.jsp?book_id=" + bookId).forward(request, response);

        } catch (Exception e) {
            // Handle exceptions
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("BookDetail.jsp").forward(request, response);
        }
    }
}
