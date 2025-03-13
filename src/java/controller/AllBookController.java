package controller;

import dao.BookDAO;
import dao.CategoryDAO;
import entity.Book;
import entity.Category;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AllBookController", urlPatterns = {"/allBook"})
public class AllBookController extends HttpServlet {

    private BookDAO bookDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        try {
            bookDAO = new BookDAO();
            categoryDAO = new CategoryDAO();
        } catch (Exception e) {
            throw new ServletException("Lỗi khi kết nối đến CSDL!", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int page = 1;
        int booksPerPage = 20;
        String categoryId = request.getParameter("category");
        String filter = request.getParameter("filter");

        // Xử lý số trang nếu có tham số truyền vào
        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }
        } catch (NumberFormatException e) {
            Logger.getLogger(AllBookController.class.getName()).log(Level.WARNING, "Số trang không hợp lệ: " + request.getParameter("page"), e);
            page = 1; // Mặc định về trang đầu tiên nếu lỗi
        }

        try {
            // Lấy danh mục
            List<Category> categories = categoryDAO.getAllCategories();
            request.setAttribute("categories", categories);
        } catch (Exception ex) {
            Logger.getLogger(AllBookController.class.getName()).log(Level.SEVERE, "Lỗi khi lấy danh mục", ex);
            request.setAttribute("error", "Không thể tải danh mục.");
        }

        try {
            // Lấy sách theo danh mục và bộ lọc
            List<Book> books = bookDAO.getBooksByCategoryAndFilter(categoryId, filter, page, booksPerPage);
            int totalBooks = bookDAO.getTotalBooks(categoryId, filter);
            int totalPages = (int) Math.ceil((double) totalBooks / booksPerPage);

            request.setAttribute("books", books);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);
            request.setAttribute("selectedCategory", categoryId);
            request.setAttribute("selectedFilter", filter);
        } catch (Exception e) {
            Logger.getLogger(AllBookController.class.getName()).log(Level.SEVERE, "Lỗi khi lấy danh sách sách", e);
            request.setAttribute("error", "Không thể tải danh sách sách.");
        }

        // Chuyển tiếp request đến JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("AllBook.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void destroy() {
        if (bookDAO != null) {
            bookDAO.closeResources();
        }
        if (categoryDAO != null) {
            categoryDAO.closeResources();
        }
    }
}
