package controller;

import dao.BookDAO;
import entity.Book;
import entity.Category;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
@WebServlet(name = "BookDetailController", urlPatterns = {"/detail"})
public class BookDetailController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        String bookId = request.getParameter("id");
        int book_id = Integer.parseInt(bookId); // Ép kiểu String sang int
        BookDAO bookDao = new BookDAO();

        // Lấy chi tiết sách
        Book book = bookDao.getBookById(book_id);

        // Lấy 4 sách giới thiệu ngẫu nhiên
        List<Book> topBooks = bookDao.getTop4();

        // Lấy danh mục sách
        List<Category> listC = bookDao.getAllCategory();

        // Gửi dữ liệu qua JSP
        request.setAttribute("detail", book);
        request.setAttribute("topBooks", topBooks);
        request.setAttribute("listCC", listC);

        // Forward đến trang JSP
        request.getRequestDispatcher("BookDetail.jsp").forward(request, response);
    }

}
