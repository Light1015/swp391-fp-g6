<%@page import="java.util.List"%>
<%@page import="entity.Book"%>
<%@page import="entity.Category"%>
<%@page import="dao.BookDAO"%>
<%@page import="dao.CategoryDAO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    int currentPage = 1;
    int booksPerPage = 8;
    String selectedCategory = request.getParameter("category");
    
    if (request.getParameter("page") != null) {
        currentPage = Integer.parseInt(request.getParameter("page"));
    }

    BookDAO bookDAO = new BookDAO();
    CategoryDAO categoryDAO = new CategoryDAO();
    List<Book> books = bookDAO.getBooksByCategoryAndFilter(selectedCategory, null, currentPage, booksPerPage);
    List<Category> categories = categoryDAO.getAllCategory();
    int totalBooks = bookDAO.getTotalBooks(selectedCategory, null);
    int totalPages = (int) Math.ceil((double) totalBooks / booksPerPage);
%>
<!DOCTYPE html>
<html>
    <head>
        <title>All Books</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" type="text/css" href="css/allBook.css">
       
    </head>
    <body>
        <jsp:include page="Menu.jsp"></jsp:include>
            <div class="container mt-4">
                <div class="row">
                    <!-- Sidebar Categories -->
                    <div class="col-md-3">
                        <h3>Loại sách</h3>
                        <ul class="list-group">
                        <% for (Category category : categories) { %>
                        <li class="list-group-item <%= (selectedCategory != null && selectedCategory.equals(String.valueOf(category.getCategoryId()))) ? "active" : "" %>">
                            <a href="AllBook.jsp?category=<%= category.getCategoryId() %>" class="text-decoration-none"><%= category.getCategoryName() %></a>
                        </li>
                        <% } %>
                    </ul>
                </div>

                <!-- Book Grid -->
                <div class="col-md-9">
                    <div class="row book-grid">
                        <% for (Book book : books) { %>
                        <div class="col-lg-3 col-md-4 col-sm-6 d-flex align-items-stretch">
                            <div class="product-item w-100">
                                <figure class="product-style">
                                    <img src="<%= book.getCoverImage() %>" alt="<%= book.getTitle() %>" class="w-100">
                                    <button type="button" class="add-to-cart">Add to Cart</button>
                                </figure>
                                <figcaption>
                                    <h3><a href="BookDetail.jsp?book_id=<%= book.getBook_id() %>"><%= book.getTitle() %></a></h3>
                                    <span><%= book.getAuthorName() %></span>
                                    <div class="item-price">$ <%= book.getPrice() %></div>
                                </figcaption>
                            </div>
                        </div>
                        <% } %>
                    </div>

                    <!-- Pagination -->
                    <nav>
                        <ul class="pagination justify-content-center py-5">
                            <% for (int i = 1; i <= totalPages; i++) { %>
                            <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                                <a class="page-link" href="AllBook.jsp?page=<%= i %>&category=<%= selectedCategory != null ? selectedCategory : "" %>"><%= i %></a>
                            </li>
                            <% } %>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>

        <footer>
            <jsp:include page="Footer.jsp"></jsp:include>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>