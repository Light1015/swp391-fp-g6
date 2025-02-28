<%@ page import="entity.User, dao.UserDAO" %>
<%@ page session="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    UserDAO userDAO = new UserDAO();
    Integer accountId = (Integer) session.getAttribute("accountId");

    if (accountId == null) {
        response.sendRedirect("Login.jsp");
        return;
    }

    User user = userDAO.getUserById(accountId);

    if (request.getMethod().equalsIgnoreCase("POST")) {
        String fullName = request.getParameter("full_name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String image = request.getParameter("image");

        boolean result = userDAO.updateUser(accountId, fullName, phone, address, image);
        if (result) {
            response.sendRedirect("Profile.jsp?success=true");
        } else {
            out.println("<script>alert('Cập nhật thất bại!');</script>");
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
        <link rel="stylesheet" type="text/css" href="css/UserProfile.css">
        <style>
            body {
                background-color: #121212;
                color: #ffffff;
            }
            .card {
                background-color: #1e1e1e;
                border-radius: 12px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
            }
            .sidebar {
                background: #2c2f33;
                padding: 20px;
                border-radius: 10px;
            }
            .sidebar a {
                display: block;
                color: #ffffff;
                padding: 12px;
                margin-bottom: 10px;
                border-radius: 8px;
                transition: background 0.3s;
                text-decoration: none;
            }
            .sidebar a:hover {
                background: #f1c40f;
                color: #2c2f33;
            }
            .avatar {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                object-fit: cover;
                border: 5px solid #f1c40f;
                transition: transform 0.3s;
            }
            .avatar:hover {
                transform: scale(1.1);
            }
            .info-row {
                display: flex;
                align-items: center;
                justify-content: space-between;
                padding: 10px 0;
                border-bottom: 1px solid #dee2e6;
            }
            .popup {
                display: none;
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background: #2c2f33;
                padding: 30px;
                border-radius: 10px;
                text-align: center;
                z-index: 10;
            }
            .popup.active {
                display: block;
            }
            
        </style>
    </head>
    <body>
        <jsp:include page="Menu.jsp"></jsp:include>
            <div class="container mt-5">
                <div class="row">
                    <div class="col-md-4">
                        <div class="sidebar">
                            <h4 class="text-warning">Cài đặt tài khoản</h4>
                            <a href="#">Tài khoản của tôi</a>
                            <a href="#">Hồ sơ</a>
                            <h4 class="text-warning mt-4">Thanh toán</h4>
                            <a href="#">Đăng ký</a>
                            <a href="#">Thanh toán</a>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="card p-4">
                            <div class="text-center">
                                <img src="<%= user.getImage() %>" class="avatar" alt="Avatar" onclick="togglePopup()">
                        </div>
                        <div class="popup" id="popup">
                            <h4>Thay đổi ảnh đại diện</h4>
                            <form method="POST">
                                <input type="text" name="image" class="form-control mb-2" placeholder="URL Avatar">
                                <button type="submit" class="btn btn-success">Cập nhật</button>
                                <button type="button" class="btn btn-danger" onclick="togglePopup()">Hủy</button>
                            </form>
                        </div>
                        <div class="info-row">
                            <span>Họ và tên: <%= user.getFullName() %></span>
                            <form method="POST" class="d-inline">
                                <button type="submit" class="btn btn-warning">Cập nhật</button>
                            </form>
                        </div>
                        <div class="info-row">
                            <span>Số điện thoại: <%= user.getPhone() %></span>
                            <form method="POST" class="d-inline">
                                <button type="submit" class="btn btn-warning">Cập nhật</button>
                            </form>
                        </div>
                        <div class="info-row">
                            <span>Địa chỉ: <%= user.getAddress() %></span>
                            <form method="POST" class="d-inline">
                                <button type="submit" class="btn btn-warning">Cập nhật</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <footer class="mt-5">
            <jsp:include page="Footer.jsp"></jsp:include>
        </footer>
        <script>
            function togglePopup() {
                document.getElementById("popup").classList.toggle("active");
            }
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>