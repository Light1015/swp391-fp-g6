<%-- 
    Document   : BookDetail
    Created on : Mar 4, 2025, 10:01:39 AM
    Author     : SE18-CE180628-Nguyen Pham Doan Trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="entity.Book, dao.BookDAO, java.util.List" %>

<%
    String bookIdParam = request.getParameter("book_id");
    Book book = null;
    List<Book> similarBooks = null;

    if (bookIdParam != null) {
        try {
            int bookId = Integer.parseInt(bookIdParam);
            BookDAO bookDAO = new BookDAO();
            book = bookDAO.getBookById(bookId);
            similarBooks = bookDAO.getSimilarBooks(bookId); // Lấy sách cùng danh mục hoặc tác giả
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title><%= (book != null) ? book.getTitle() : "Book Details" %></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <jsp:include page="Menu.jsp"/>

        <div class="container my-5">
            <% if (book != null) { %>

            <!-- Breadcrumb navigation -->
            <%
      String[] categoryNames = new String[0];
      if (book != null) {
          try {
              // Lấy danh sách thể loại từ BookDAO
              String categories = new BookDAO().getCategoriesForBook(book.getBook_id());
              // Tách danh sách thể loại thành mảng
              categoryNames = categories.split(", ");
          } catch (ClassNotFoundException e) {
              e.printStackTrace();
          }
      }
            %>

            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="index.jsp">Home</a></li>
                        <% for (String category : categoryNames) { %>
                    <li class="breadcrumb-item"><a href="#"><%= category %></a></li>
                        <% } %>
                    <li class="breadcrumb-item active" aria-current="page"><%= book.getTitle() %></li>
                </ol>
            </nav>

            <div class="row">
                <!-- Cột bên trái: Chỉ chứa ảnh bìa -->
                <div class="col-md-4 text-center">
                    <img src="<%= book.getCoverImage() %>" alt="<%= book.getTitle() %>" class="img-fluid rounded shadow-lg">

                    <div class="mt-2 d-flex gap-3">
                        <a href="CartServlet?book_id=<%= book.getBook_id() %>&action=add" 
                           class="btn btn-light btn-lg w-50 d-flex align-items-center justify-content-center border rounded-3 shadow-sm">
                            <i class="fas fa-shopping-cart me-2"></i> Thêm vào giỏ hàng
                        </a>
                        <a href="CheckoutServlet?book_id=<%= book.getBook_id() %>" 
                           class="btn btn-light btn-lg w-50 d-flex align-items-center justify-content-center border rounded-3 shadow-sm">
                            <i class="fas fa-credit-card me-2"></i> Mua ngay
                        </a>
                    </div>
                </div>

                <!-- Cột bên phải: Chứa tất cả thông tin còn lại -->
                <div class="col-md-8">
                    <div class="card p-4">
                        <h4 class="display-4 mb-4"><%= book.getTitle() %></h4>

                        <div class="row">
                            <div class="col-12 col-md-6">
                                <p><strong>Nhà xuất bản:</strong> <%= book.getPublisher() %></p>
                            </div>
                            <div class="col-12 col-md-6">
                                <p><strong>Tác giả:</strong> <%= book.getAuthorName() %></p>
                            </div>
                            <div class="col-12 col-md-6">
                                <p><strong>Hình thức sách:</strong> <%= book.getBookType() %></p>
                            </div>
                            <div class="col-12 col-md-6">
                                <p class="lead text-success"><strong>Giá:</strong> $<%= book.getPrice() %></p>
                            </div>
                            <div class="col-12 col-md-6">
                                <p><strong>Trạng thái:</strong> 
                                    <% if (book.getStockQuantity() > 0) { %>
                                    <span class="text-success">Còn hàng</span>
                                    <% } else { %>
                                    <span class="text-danger">Hết hàng</span>
                                    <% } %>
                                </p>
                            </div>
                        </div>
                    </div>

                    <!-- Xem thêm sản phẩm tương tự -->
                    <div class="mt-5">
                        <h3 class="mb-3">Xem thêm các sản phẩm tương tự</h3>
                        <div class="row row-cols-2 row-cols-md-4 g-3">
                            <% if (similarBooks != null && !similarBooks.isEmpty()) { %>
                            <% for (Book sBook : similarBooks) { %>
                            <div class="col">
                                <div class="card h-100 text-center shadow-sm" style="width: 100%; max-width: 200px;">
                                    <img src="<%= sBook.getCoverImage() %>" class="card-img-top" alt="<%= sBook.getTitle() %>" 
                                         style="height: 180px; width: 100%; object-fit: contain;">
                                    <div class="card-body p-2">
                                        <h6 class="card-title text-truncate">
                                            <a href="BookDetail.jsp?book_id=<%= sBook.getBook_id() %>" class="text-dark text-decoration-none">
                                                <%= sBook.getTitle() %>
                                            </a>
                                        </h6>
                                        <p class="fw-bold text-danger mb-0">$<%= sBook.getPrice() %></p>
                                    </div>
                                </div>
                            </div>
                            <% } %>
                            <% } else { %>
                            <p class="text-muted">Không có sản phẩm tương tự.</p>
                            <% } %>
                        </div>
                    </div>


                    <!-- Thông tin chi tiết khác -->
                    <div class="mt-5">
                        <h3>Thông tin chi tiết</h3>
                        <ul class="list-group">
                            <li class="list-group-item"><strong>Ngôn ngữ:</strong> <%= book.getLanguage() %></li>
                            <li class="list-group-item"><strong>Năm xuất bản:</strong> <%= book.getPublicationYear() %></li>
                            <li class="list-group-item"><strong>Kho hàng:</strong> <%= book.getStockQuantity() %> sản phẩm</li>
                                <% if (book.getSeriesName() != null) { %>
                            <li class="list-group-item"><strong>Bộ sách:</strong> <%= book.getSeriesName() %> (Tập <%= book.getVolumeNumber() %>)</li>
                                <% } %>
                        </ul>
                    </div>

                    <!-- Mô tả chi tiết sách (hiển thị dưới đây) -->
                    <div class="mt-5">
                        <h3>Mô tả sản phẩm</h3>
                        <p><%= book.getDescription() %></p> <!-- Hiển thị mô tả sản phẩm tại đây -->
                    </div>

                </div>
            </div>
            <% } else { %>
            <div class="alert alert-danger">Không tìm thấy sách.</div>
            <% } %>

        </div>

        <footer>
            <jsp:include page="Footer.jsp"/>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>