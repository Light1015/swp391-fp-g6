<%@ page import="entity.User, dao.UserDAO" %>
<%@ page import="entity.Account, dao.AccountDAO" %>
<%@ page session="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.sql.*" %>
<%
    UserDAO userDAO = new UserDAO();
    AccountDAO accountDAO = new AccountDAO();
    Integer accountId = (Integer) session.getAttribute("accountId");

    if (accountId == null) {
        response.sendRedirect("Login.jsp");
        return;
    }

    User user = userDAO.getUserById(accountId);
    Account acc = accountDAO.getAccountById(accountId);

    // Get the active tab from request parameter or set default
    String activeTab = request.getParameter("tab") != null ? request.getParameter("tab") : "personalInfo";
   
    if (request.getMethod().equalsIgnoreCase("POST")) {
        String fullName = request.getParameter("full_name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String image = request.getParameter("image");

        boolean result = userDAO.updateUser(accountId, fullName, phone, address, image);
        if (result) {
            response.sendRedirect("UserProfile.jsp?success=true&tab=personalInfo");
        } else {
            session.setAttribute("updateError", "true");
            response.sendRedirect("UserProfile.jsp?success=false&tab=personalInfo");
        }
    }
%>


<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Thông tin cá nhân</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/userProfile.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
       
    </head>

    <body>

        <!-- Popup Notification System -->
        <div id="successNotification" class="notification-popup success" style="display:none;">
            <strong>Thành công!</strong> <span id="successMessage"></span>
        </div>
        <div id="errorNotification" class="notification-popup error" style="display:none;">
            <strong>Thất bại!</strong> <span id="errorMessage"></span>
        </div>

        <% if (session.getAttribute("errorMessage") != null) { %>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                document.getElementById('errorMessage').innerText = '<%= session.getAttribute("errorMessage") %>';
                showNotification('errorNotification');
            });
        </script>
        <% session.removeAttribute("errorMessage"); %>
        <% } %>

        <% if (session.getAttribute("successMessage") != null) { %>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                document.getElementById('successMessage').innerText = '<%= session.getAttribute("successMessage") %>';
                showNotification('successNotification');
            });
        </script>
        <% session.removeAttribute("successMessage"); %>
        <% } %>



        <!-- Navbar -->
        <div class="navbar navbar-expand-md bg-dark navbar-dark">
            <div class="container">
                <a href="index.jsp" class="navbar-brand text-info">OBRW</a>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainmenu"><span
                        class="navbar-toggler-icon"></span></button>

                <div class="collapse navbar-collapse" id="mainmenu">
                    <li class="navbar-nav nav-item">
                        <c:if test="${empty sessionScope.account}">
                            <a href="Login.jsp" class="user-account for-buy">
                                <i class="icon icon-user"></i><span>Login</span>
                            </a>
                        </c:if>

                        <c:if test="${not empty sessionScope.account}">
                            <a class="nav-link" href="UserProfile.jsp">Hello, ${sessionScope.account.username}</a>
                            <a class="nav-link" href="logout">Logout</a>
                        </c:if> 
                    </li>
                </div>
            </div>
        </div>

        <div class="container mt-5" style="width: 1500px; max-width: 100%; margin: 0 auto; padding: 10px; background-color: #23272A; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);">
            <div class="content-card"
                 style="margin-bottom: 0; border-radius: 0 0 4px 4px; background: #23272A; position: relative;">
                <div class="row">
                    <div class="col-md-4 sidebar-container">
                        <div class="sidebar">
                            <div class="avatar-container">
                                <img src="<%= user.getImage() %>" class="avatar" alt="Avatar">
                                <div class="display-name"><%= user.getFullName() %></div>
                            </div>
                            <h4 class="text-warning">Cài đặt tài khoản</h4>
                            <a id="sidebar-account" class="<%= activeTab.equals("personalInfo") ? "active" : "" %>" onclick="changeTab('personalInfo')">Tài khoản của tôi</a>
                            <a id="sidebar-profile" class="<%= activeTab.equals("profile") ? "active" : "" %>" onclick="changeTab('profile')">Hồ sơ</a>
                            <a id="sidebar-status" class="<%= activeTab.equals("userStatus") ? "active" : "" %>" onclick="changeTab('userStatus')">Trạng thái tài khoản</a>
                            <h4 class="text-warning mt-4">Thanh toán</h4>
                            <a id="sidebar-history" class="<%= activeTab.equals("paymentHistory") ? "active" : "" %>" onclick="changeTab('paymentHistory')">Lịch sử thanh toán</a>
                            <h4 class="text-warning">Cài đặt bảo mật</h4>
                            <a id="sidebar-security" class="<%= activeTab.equals("securitySettings") ? "active" : "" %>" onclick="changeTab('securitySettings')">Thông tin bảo mật</a>
                            <a id="sidebar-password" class="<%= activeTab.equals("changePassword") ? "active" : "" %>" onclick="changeTab('changePassword')">Đổi mật khẩu</a>
                            <a ></a>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="card p-4">
                            <!-- TAB CONTENT -->
                            <div class="tab-content">

                                <!-- Tab 1: Thông tin cá nhân -->
                                <div class="tab-pane <%= activeTab.equals("personalInfo") ? "show active" : "" %>" id="personalInfo">
                                    <div class="p-4 bg-light text-dark rounded shadow-lg">
                                        <h4 class="fw-bold text-primary mb-3">Thông tin cá nhân</h4>

                                        <div class="mb-3">
                                            <p class="mb-1"><strong class="fw-bold">Tên hiển thị:</strong></p>
                                            <p class="text-primary fw-bold"><%= user.getFullName() %></p>
                                        </div>

                                        <div class="mb-3">
                                            <p class="mb-1"><strong class="fw-bold">Số điện thoại:</strong></p>
                                            <p class="text-dark"><%= user.getPhone() %></p>
                                        </div>

                                        <div class="mb-3">
                                            <p class="mb-1"><strong class="fw-bold">Địa chỉ:</strong></p>
                                            <p class="text-dark"><%= user.getAddress() %></p>
                                        </div>
                                        <button class="btn btn-primary mt-3 px-4 py-2 fw-bold" onclick="changeTab('profile')">Thay đổi thông tin</button>
                                    </div>
                                </div>

                                <!-- Tab 2: Hồ sơ (Chỉnh sửa thông tin) -->
                                <div class="tab-pane <%= activeTab.equals("profile") ? "show active" : "" %>" id="profile">
                                    <div class="p-4 bg-light text-dark rounded shadow-lg">
                                        <h4 class="fw-bold text-primary mb-3">Cập nhật thông tin cá nhân</h4>
                                        <form method="POST" class="mt-3">
                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Ảnh đại diện (URL):</label>
                                                <input type="text" name="image" class="form-control border-primary" value="<%= user.getImage() %>">
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Tên hiển thị:</label>
                                                <input type="text" name="full_name" class="form-control border-primary" value="<%= user.getFullName() %>">
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Số điện thoại:</label>
                                                <input type="text" name="phone" class="form-control border-primary" value="<%= user.getPhone() %>">
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Địa chỉ:</label>
                                                <input type="text" name="address" class="form-control border-primary" value="<%= user.getAddress() %>">
                                            </div>
                                            <div class="d-flex justify-content-between">
                                                <button type="submit" class="btn btn-success fw-bold px-4 py-2">Cập nhật</button>
                                                <button type="button" class="btn btn-secondary fw-bold px-4 py-2" onclick="changeTab('personalInfo')">Hủy</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                                <!-- Tab 3: Trạng thái người dùng -->
                                <div class="tab-pane <%= activeTab.equals("userStatus") ? "show active" : "" %>" id="userStatus">
                                    <div class="p-4 bg-light text-dark rounded shadow-lg">
                                        <h4 class="fw-bold text-primary mb-3">Trạng thái tài khoản</h4>
                                        <p>Loại tài khoản: <strong>Thành viên VIP</strong></p>
                                        <p>Ngày tham gia: <strong>01/01/2023</strong></p>
                                        <p>Lần đăng nhập gần nhất: <strong>27/02/2025</strong></p>
                                        <p>Trạng thái hoạt động: <span class="badge bg-success">Đang hoạt động</span></p>
                                    </div>
                                </div>


                                <!-- Tab 4: Thanh toán - Lịch sử thanh toán -->
                                <div class="tab-pane <%= activeTab.equals("paymentHistory") ? "show active" : "" %>" id="paymentHistory">
                                    <div class="p-4 bg-light text-dark rounded shadow-lg">
                                        <h4 class="fw-bold text-primary mb-3">Lịch sử thanh toán</h4>
                                        <table class="table table-bordered">
                                            <thead>
                                                <tr>
                                                    <th>Ngày</th>
                                                    <th>Gói</th>
                                                    <th>Số tiền</th>
                                                    <th>Trạng thái</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>01/01/2023</td>
                                                    <td>VIP</td>
                                                    <td>500.000đ</td>
                                                    <td><span class="badge bg-success">Thành công</span></td>
                                                </tr>
                                                <tr>
                                                    <td>01/02/2023</td>
                                                    <td>VIP</td>
                                                    <td>500.000đ</td>
                                                    <td><span class="badge bg-success">Thành công</span></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>

                                <!-- Tab 5: Cài đặt bảo mật -->
                                <div class="tab-pane <%= activeTab.equals("securitySettings") ? "show active" : "" %>" id="securitySettings">
                                    <div class="p-4 bg-light text-dark rounded shadow-lg">
                                        <h4 class="fw-bold text-primary mb-3">Thông tin bảo mật</h4>
                                        <div class="mb-3">
                                            <p class="mb-1"><strong class="fw-bold">Username:</strong></p>

                                        </div>
                                        <div class="mb-3">
                                            <p class="mb-1"><strong class="fw-bold">Email:</strong></p>

                                        </div>
                                        <div class="mb-3">
                                            <p class="mb-1"><strong class="fw-bold">Password:</strong></p>
                                            <p class="text-dark">********</p> <!-- Không hiển thị mật khẩu thực tế -->

                                        </div>
                                    </div>
                                </div>

                                <!-- Tab 6: Đổi mật khẩu -->
                                <div class="tab-pane <%= activeTab.equals("changePassword") ? "show active" : "" %>" id="changePassword">
                                    <div class="p-4 bg-light text-dark rounded shadow-lg">
                                        <h4 class="fw-bold text-primary mb-3">Đổi mật khẩu</h4>
                                        <form method="POST" action="ChangePasswordServlet" class="mt-3">
                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Mật khẩu hiện tại:</label>
                                                <input type="password" name="currentPassword" class="form-control border-primary" required>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Mật khẩu mới:</label>
                                                <input type="password" name="newPassword" class="form-control border-primary" required>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Xác nhận mật khẩu mới:</label>
                                                <input type="password" name="confirmPassword" class="form-control border-primary" required>
                                            </div>
                                            <div class="d-flex justify-content-between">
                                                <button type="submit" class="btn btn-success fw-bold px-4 py-2">Cập nhật</button>
                                                <button type="button" class="btn btn-secondary fw-bold px-4 py-2" onclick="changeTab('changePassword')">Hủy</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Footer container with isolation -->
       
            <jsp:include page="Footer.jsp"></jsp:include>
       

        <script>
            // Function to show popup notification
            function showNotification(type) {
                const notification = document.getElementById(type + 'Notification');
                notification.classList.add('show');
                // Hide after 3 seconds
                setTimeout(() => {
                    notification.classList.remove('show');
                    notification.classList.add('hide');
                    // Remove hide class after animation completes
                    setTimeout(() => {
                        notification.classList.remove('hide');
                    }, 300);
                }, 3000);
            }

            // Function to change tabs
            function changeTab(tabId) {
                // Hide all tabs
                document.querySelectorAll('.tab-pane').forEach(tab => {
                    tab.classList.remove('show', 'active');
                });
                // Remove active class from sidebar links
                document.querySelectorAll('.sidebar a').forEach(link => {
                    link.classList.remove('active');
                });
                // Show selected tab
                document.getElementById(tabId).classList.add('show', 'active');
                // Add active class to sidebar link
                document.getElementById('sidebar-' + tabId).classList.add('active');
                // Update URL with tab parameter without refreshing
                const url = new URL(window.location.href);
                url.searchParams.set('tab', tabId);
                window.history.pushState({}, '', url);
            }

            // Check for URL parameters on page load
            document.addEventListener('DOMContentLoaded', function () {
                const urlParams = new URLSearchParams(window.location.search);
                const success = urlParams.get('success');
                if (success === 'true') {
                    showNotification('success');
                } else if (success === 'false') {
                    showNotification('error');
                }

                // Clean success parameter from URL after handling
                if (success) {
                    const url = new URL(window.location.href);
                    url.searchParams.delete('success');
                    window.history.replaceState({}, document.title, url);
                }
            });
            function showNotification(id) {
                var notification = document.getElementById(id);
                notification.style.display = 'block'; // Make it visible
                notification.classList.add('show');
                setTimeout(function () {
                    notification.classList.remove('show');
                    notification.style.display = 'none'; // Hide it after the animation
                }, 3000); // 3 seconds
            }
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>