/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import dao.CategoryDAO;
import entity.Book;
import entity.Category;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            BookDAO bookDAO = new BookDAO();
            CategoryDAO categoryDAO = new CategoryDAO();
            List<Map<String, String>> randomBooks = bookDAO.getRandomBooks(); // Lấy danh sách sách ngẫu nhiên
            List<Category> categoryList = bookDAO.getAllCategory();
            List<Book> list4Book = bookDAO.getTop4();
            List<Book> list8Book = bookDAO.getTop8Books();
            List<Category> topCategories = null;
            try {
                topCategories = categoryDAO.getTopCategories();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Map<Integer, List<Book>> booksByCategory = new HashMap<>();
            // Lấy sách trong top category nhiều sách
            for (Category category : topCategories) {
                List<Book> books = bookDAO.getTopBooksByCategory(category.getCategoryId());
                booksByCategory.put(category.getCategoryId(), books);
            }
            request.setAttribute("bookDAO", bookDAO);
            request.setAttribute("randomBooks", randomBooks); // Truyền danh sách sách gồm ảnh & mô tả
            request.setAttribute("list4B", list4Book);
            request.setAttribute("list8Book", list8Book);
            request.setAttribute("topCategories", topCategories);
            request.setAttribute("booksByCategory", booksByCategory);
            request.getRequestDispatcher("Home.jsp").forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
