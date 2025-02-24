<%-- 
    Document   : ForgotPassword
    Created on : Feb 24, 2025, 12:26:37 AM
    Author     : Oanh Nguyen
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên mật khẩu</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/forgotPassword.css"> <!-- Thêm file CSS riêng -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="card">
        <h3 class="text-center">Quên mật khẩu</h3>
        <form id="forgotForm" action="ForgotPassword" method="post">
            <div class="form-group">
                <label>Nhập email hoặc tên đăng nhập:</label>
                <input type="text" name="emailOrUsername" id="emailOrUsername" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block" id="sendOtpBtn">Gửi yêu cầu</button>
        </form>

        <!-- Form nhập OTP, ẩn đi ban đầu -->
        <form id="otpForm" action="verify-otp" method="post" style="display: none;">
            <div class="form-group">
                <label>Nhập mã OTP:</label>
                <input type="text" name="otp" id="otp" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-success btn-block">Xác nhận</button>
        </form>

        <a href="Login.jsp" class="btn btn-secondary btn-block mt-3">Quay lại đăng nhập</a>

        <script>
            $(document).ready(function() {
                $("#forgotForm").submit(function(event) {
                    event.preventDefault(); // Ngăn form submit mặc định

                    var emailOrUsername = $("#emailOrUsername").val();

                    $.post("forgot-password", { emailOrUsername: emailOrUsername }, function(response) {
                        if (response === "success") {
                            alert("Mã OTP đã được gửi!");
                            $("#otpForm").show(); // Hiện ô nhập OTP
                            $("#sendOtpBtn").text("Gửi lại yêu cầu"); // Đổi text nút gửi yêu cầu
                        } else {
                            alert("Tài khoản không tồn tại!");
                        }
                    });
                });
            });
        </script>
    </div>
</body>
</html>



