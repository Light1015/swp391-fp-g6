<%-- 
    Document   : BookDetail
    Created on : Mar 4, 2025, 10:01:39 AM
    Author     : SE18-CE180628-Nguyen Pham Doan Trang
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="entity.Book" %>
<%@ page import="dao.BookDAO" %>
<%@ page import="java.sql.*" %>

<%
    String bookIdParam = request.getParameter("book_id");
    Book book = null;
    if (bookIdParam != null) {
        try {
            int bookId = Integer.parseInt(bookIdParam);
            BookDAO bookDAO = new BookDAO();
            book = bookDAO.getBookById(bookId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Book Details</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/style.css">
    </head>
    <body>
        <jsp:include page="Menu.jsp"/>

        <div class="container my-5">
            <% if (book != null) { %>
            <div class="row">
                <div class="col-md-4 text-center">
                    <img src="<%= book.getCoverImage() %>" alt="<%= book.getTitle() %>" class="img-fluid rounded shadow-lg">
                </div>
                <div class="col-md-8">
                    <h2 class="display-4"><%= book.getTitle() %></h2>
                    <p class="text-muted">By <%= book.getAuthorName() %></p>
                    <p class="lead">Price: $<%= book.getPrice() %></p>
                    <p><%= book.getDescription() %></p>
                    <a href="CartServlet?book_id=<%= book.getBookId() %>&action=add" class="btn btn-primary">Add to Cart</a>
                </div>
            </div>
            <% } else { %>
            <div class="alert alert-danger">Book not found.</div>
            <% } %>
        </div>

        <footer>
            <jsp:include page="Footer.jsp"/>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>