<%-- 
    Document   : BookDetail
    Created on : Mar 4, 2025, 10:01:39 AM
    Author     : SE18-CE180628-Nguyen Pham Doan Trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="entity.Book, dao.BookDAO, java.util.List, entity.Account, entity.Interaction, dao.InteractionDAO, java.text.SimpleDateFormat" %>

<%
    String bookIdParam = request.getParameter("book_id");
    Book book = null;
    List<Book> similarBooks = null;
    List<Interaction> comments = null;
    double averageRating = 0;
    int reviewCount = 0;

    if (bookIdParam != null) {
        try {
            int bookId = Integer.parseInt(bookIdParam);
            BookDAO bookDAO = new BookDAO();
            book = bookDAO.getBookById(bookId);
            similarBooks = bookDAO.getSimilarBooks(bookId); // Lấy sách cùng danh mục hoặc tác giả
            
            // Load comments, average rating and review count
            InteractionDAO interactionDAO = new InteractionDAO();
            comments = interactionDAO.getCommentsByBookId(bookId);
            averageRating = interactionDAO.getAverageRatingByBookId(bookId);
            reviewCount = interactionDAO.getReviewCountByBookId(bookId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    // Format for displaying dates
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
    // Get logged-in user
    Account loginAccount = (Account) session.getAttribute("account");
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title><%= (book != null) ? book.getTitle() : "Book Details" %></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
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

                        <!-- Display average rating -->
                        <div class="mb-3">
                            <div class="d-flex align-items-center">
                                <div class="ratings me-2">
                                    <% for (int i = 1; i <= 5; i++) { %>
                                    <% if (i <= Math.round(averageRating)) { %>
                                    <i class="fa fa-star text-warning"></i>
                                    <% } else { %>
                                    <i class="fa fa-star text-secondary"></i>
                                    <% } %>
                                    <% } %>
                                </div>
                                <span class="text-muted"><%= String.format("%.1f", averageRating) %> (<%= reviewCount %> đánh giá)</span>
                            </div>
                        </div>

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
                    <!-- Customer reviews section -->
                    <div class="mt-5">
                        <h3 class="mb-3">Đánh giá từ người đọc (<%= reviewCount %>)</h3>

                        <% if (request.getAttribute("errorMessage") != null) { %>
                        <div class="alert alert-danger" role="alert">
                            <%= request.getAttribute("errorMessage") %>
                        </div>
                        <% } %>

                        <% if (request.getAttribute("successMessage") != null) { %>
                        <div class="alert alert-success" role="alert">
                            <%= request.getAttribute("successMessage") %>
                        </div>
                        <% } %>

                        <!-- Comment form for logged-in users -->
                        <% if (loginAccount != null) { %>
                        <div class="card mb-4 p-3">
                            <h5>Viết đánh giá của bạn</h5>
                            <form action="CommentController" method="post">
                                <input type="hidden" name="book_id" value="<%= book.getBook_id() %>">

                                <div class="mb-3">

                                    <div class="rating">
                                        <div class="mb-3">
                                            <label for="rating" class="form-label">Đánh giá sao</label>
                                            <div class="rating">
                                                <input type="hidden" name="rating" id="rating" value="" required>
                                                <div class="star-rating">
                                                    <i class="far fa-star star-icon" data-rating="1"></i>
                                                    <i class="far fa-star star-icon" data-rating="2"></i>
                                                    <i class="far fa-star star-icon" data-rating="3"></i>
                                                    <i class="far fa-star star-icon" data-rating="4"></i>
                                                    <i class="far fa-star star-icon" data-rating="5"></i>
                                                    <span class="rating-text ms-2"></span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label for="comment" class="form-label">Nội dung đánh giá</label>
                                    <textarea class="form-control" id="comment" name="comment" rows="3" required></textarea>
                                </div>

                                <button type="submit" class="btn btn-primary">Gửi đánh giá</button>
                            </form>
                        </div>
                        <% } else { %>
                        <div class="alert alert-info" role="alert">
                            Vui lòng <a href="Login.jsp" class="alert-link">đăng nhập</a> để viết đánh giá.
                        </div>
                        <% } %>

                        <!-- Display existing comments -->
                        <% if (comments != null && !comments.isEmpty()) { %>
                        <div class="comments-section">
                            <% for (Interaction comment : comments) { %>
                            <div class="card mb-3">
                                <div class="card-body">
                                    <div class="d-flex mb-3">
                                        <div class="flex-shrink-0">
                                            <img src="https://static.vecteezy.com/system/resources/previews/026/434/417/original/default-avatar-profile-icon-of-social-media-user-photo-vector.jpg" 
                                                 alt="<%= comment.getUsername() %>" 
                                                 class="rounded-circle" width="50" height="50">
                                        </div>
                                        <div class="flex-grow-1 ms-3">
                                            <h5 class="mb-0"><%= comment.getUsername() %></h5>
                                            <div class="ratings">
                                                <% for (int i = 1; i <= 5; i++) { %>
                                                <% if (i <= comment.getRating()) { %>
                                                <i class="fa fa-star text-warning"></i>
                                                <% } else { %>
                                                <i class="fa fa-star text-secondary"></i>
                                                <% } %>
                                                <% } %>
                                                <span class="text-muted ms-2">
                                                    <%= comment.getCreated_at() != null ? dateFormat.format(comment.getCreated_at()) : "" %>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="ms-auto">
                                            <% if (loginAccount != null && comment.getAccount_id() != loginAccount.getAccountId()) { %>
                                            <button type="button" class="btn btn-sm btn-outline-danger report-comment-btn" 
                                                    data-interaction-id="<%= comment.getInteraction_id() %>"
                                                    data-bs-toggle="modal" data-bs-target="#reportCommentModal">
                                                <i class="fa fa-flag"></i> Report
                                            </button>
                                            <% } %>
                                        </div>
                                    </div>
                                    <p class="card-text"><%= comment.getComment() %></p>
                                </div>
                            </div>
                            <% } %>
                        </div>
                        <% } else { %>
                        <div class="alert alert-light" role="alert">
                            Chưa có đánh giá nào cho sách này. Hãy là người đầu tiên đánh giá!
                        </div>
                        <% } %>
                    </div>
                </div>
            </div>
            <% } else { %>
            <div class="alert alert-danger">Không tìm thấy sách.</div>
            <% } %>

        </div>

        <div class="modal fade" id="reportCommentModal" tabindex="-1" aria-labelledby="reportCommentModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form id="reportCommentForm" action="ReportCommentController" method="post">
                        <input type="hidden" name="interaction_id" id="reportInteractionId" value="">
                        <input type="hidden" name="book_id" value="<%= book.getBook_id() %>">

                        <div class="modal-header">
                            <h5 class="modal-title" id="reportCommentModalLabel">Report Inappropriate Comment</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mb-3">
                                <label for="reportReason" class="form-label">Reason for reporting:</label>
                                <select class="form-select" id="reportReason" name="reason" required>
                                    <option value="">Select a reason</option>
                                    <option value="offensive">Offensive or inappropriate language</option>
                                    <option value="spam">Spam or advertising</option>
                                    <option value="harassment">Harassment or bullying</option>
                                    <option value="incorrect">Factually incorrect</option>
                                    <option value="other">Other reason</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="reportDetails" class="form-label">Additional details (optional):</label>
                                <textarea class="form-control" id="reportDetails" name="details" rows="3"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-danger">Submit Report</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <footer>
            <jsp:include page="Footer.jsp"/>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const reportButtons = document.querySelectorAll('.report-comment-btn');

                reportButtons.forEach(button => {
                    button.addEventListener('click', function () {
                        const interactionId = this.getAttribute('data-interaction-id');
                        document.getElementById('reportInteractionId').value = interactionId;
                    });
                });
            });
            document.addEventListener('DOMContentLoaded', function () {
                const stars = document.querySelectorAll('.star-icon');
                const ratingInput = document.getElementById('rating');
                const ratingText = document.querySelector('.rating-text');

                const ratingLabels = {
                    1: "Kém",
                    2: "Bình thường",
                    3: "Tốt",
                    4: "Rất tốt",
                    5: "Xuất sắc"
                };

                stars.forEach(star => {
                    // Hover effect
                    star.addEventListener('mouseover', function () {
                        const rating = this.getAttribute('data-rating');
                        highlightStars(rating);
                    });

                    // Click event
                    star.addEventListener('click', function () {
                        const rating = this.getAttribute('data-rating');
                        ratingInput.value = rating;
                        highlightStars(rating);
                        ratingText.textContent = rating + ' - ' + ratingLabels[rating];
                    });
                });

                // Mouse leave event for the container
                document.querySelector('.star-rating').addEventListener('mouseleave', function () {
                    const currentRating = ratingInput.value;
                    highlightStars(currentRating);
                });

                function highlightStars(rating) {
                    stars.forEach(star => {
                        const starRating = star.getAttribute('data-rating');
                        if (starRating <= rating) {
                            star.classList.remove('far');
                            star.classList.add('fas', 'text-warning');
                        } else {
                            star.classList.remove('fas', 'text-warning');
                            star.classList.add('far');
                        }
                    });
                }
            });
        </script>
        <style>
            .star-icon {
                font-size: 1.5rem;
                cursor: pointer;
                transition: color 0.2s;
            }

            .star-rating {
                display: flex;
                align-items: center;
            }

            .rating-text {
                font-size: 0.9rem;
                color: #6c757d;
            }
        </style>
    </body>
</html>