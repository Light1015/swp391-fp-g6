<%-- 
    Document   : adminScreen
    Created on : Feb 19, 2025, 10:03:26 AM
    Author     : SE18-CE180628-Nguyen Pham Doan Trang
--%>
<%@ page import="model.Account" %>
<%
    Account account = (Account) session.getAttribute("account");
    if (account == null || !account.isAdmin()) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin Dashboard</title>
    </head>
    <body>
        <h2>Chào m?ng, <%= account.getUsername() %> (Admin)</h2>
        <a href="logout">??ng xu?t</a>
    </body>
</html>

