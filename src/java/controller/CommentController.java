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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author son
 */
@WebServlet(name = "CommentController", urlPatterns = {"/CommentController"})
public class CommentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        try {
            Account loginAccount = (Account) session.getAttribute("account");
            // Get parameters from form
            int bookId = Integer.parseInt(request.getParameter("book_id"));
            double rating = Double.parseDouble(request.getParameter("rating"));
            String comment = request.getParameter("comment");

            // Validate input
            if (rating < 1 || rating > 5) {
                // Invalid rating
                request.setAttribute("errorMessage", "Rating must be between 1 and 5");
                request.getRequestDispatcher("BookDetail.jsp?book_id=" + bookId).forward(request, response);
                return;
            }

            if (comment == null || comment.trim().isEmpty()) {
                // Empty comment
                request.setAttribute("errorMessage", "Comment cannot be empty");
                request.getRequestDispatcher("BookDetail.jsp?book_id=" + bookId).forward(request, response);
                return;
            }

            // Add comment to database
            int accountId = loginAccount.getAccountId();
            InteractionDAO bookDAO = new InteractionDAO();
            boolean success = bookDAO.addComment(accountId, bookId, rating, comment);

            if (success) {
                request.setAttribute("successMessage", "Your review has been submitted successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to submit your review. Please try again.");
            }

            // Redirect back to book detail page
            response.sendRedirect("BookDetail.jsp?book_id=" + bookId);

        } catch (NumberFormatException e) {
            response.sendRedirect("index.jsp");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
