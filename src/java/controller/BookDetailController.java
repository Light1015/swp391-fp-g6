package controller;

import dao.BookDAO;
import entity.Book;
import entity.Category;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "BookDetailController", urlPatterns = {"/detail"})
public class BookDetailController extends HttpServlet {

    public BookDetailController() {
        super(); // Constructor mặc định
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            throw new ServletException(ex);
        } catch (SQLException ex) {
            Logger.getLogger(BookDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            throw new ServletException(ex);
        } catch (SQLException ex) {
            Logger.getLogger(BookDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String bookId = request.getParameter("book_id");
        if (bookId == null || bookId.isEmpty()) {
            response.sendRedirect("error.jsp");
            return;
        }

        int book_id;
        try {
            book_id = Integer.parseInt(bookId);
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp");
            return;
        }

        BookDAO bookDao = new BookDAO();
        Book book = bookDao.getBookById(book_id);
        if (book == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        List<Book> topBooks = bookDao.getTop4();
        List<Category> listC = bookDao.getAllCategory();

        
        request.setAttribute("detail", book);
        request.setAttribute("book", book);
        request.setAttribute("topBooks", topBooks);
        request.setAttribute("listCC", listC);

        request.getRequestDispatcher("BookDetail.jsp").forward(request, response);
    }
}
