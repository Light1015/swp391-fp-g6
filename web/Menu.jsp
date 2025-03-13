<%@page import="entity.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="dao.CategoryDAO"%>
<%@page import="entity.Category"%>
<%@page import="java.util.List"%>

<%
    CategoryDAO categoryDAO = new CategoryDAO();
    List<Category> categories = categoryDAO.getAllCategories();
    request.setAttribute("categories", categories);
    Account acc = (Account) session.getAttribute("account");
    request.setAttribute("totalCategories", categories.size()); // Tính tổng số danh mục ngay trong Java
%>


<!DOCTYPE html>
<html>
    <head>
        <title>OBRW - Online Book Reading Website</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="format-detection" content="telephone=no">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="author" content="">
        <meta name="keywords" content="">
        <meta name="description" content="">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

        <link rel="stylesheet" type="text/css" href="css/normalize.css">
        <link rel="stylesheet" type="text/css" href="icomoon/icomoon.css">
        <link rel="stylesheet" type="text/css" href="css/vendor.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" type="text/css" href="css/menu.css">
    </head>
    <body>
        <div id="header-wrap">

            <div class="top-content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="social-links">
                                <ul>
                                    <li><a href="#"><i class="icon icon-facebook"></i></a></li>
                                    <li><a href="#"><i class="icon icon-twitter"></i></a></li>
                                    <li><a href="#"><i class="icon icon-youtube-play"></i></a></li>
                                    <li><a href="#"><i class="icon icon-behance-square"></i></a></li>
                                </ul>
                            </div><!--social-links-->
                        </div>
                        <div class="col-md-6">
                            <div class="right-element d-flex align-items-center justify-content-end gap-2">
                                <c:if test="${empty sessionScope.account}">
                                    <a href="Login.jsp" class="user-account for-buy d-flex align-items-center">
                                        <i class="fa-solid fa-user"></i><span class="ms-1">Login</span>
                                    </a>
                                </c:if>

                                <c:if test="${not empty sessionScope.account}">
                                    <a class="nav-link" href="UserProfile.jsp"><i class="fa-solid fa-user"></i></a>
                                    </c:if>  

                                <a href="#" class="cart for-buy"><i class="fa-solid fa-clipboard"></i></a>

                                <div class="action-menu d-flex align-items-center">
                                    <div class="search-bar">
                                        <a href="#" class="search-button search-toggle" data-selector="#header-wrap">
                                            <i class="fa-solid fa-magnifying-glass"></i>
                                        </a>
                                        <form role="search" method="get" class="search-box">
                                            <input class="search-field text search-input" placeholder="Search" type="search">
                                        </form>
                                    </div>
                                </div>
                            </div><!--top-right-->
                        </div>

                    </div>
                </div>
            </div><!--top-content-->

            <header id="header">
                <div class="container-fluid">
                    <div class="row align-items-center">
                        <!-- Logo -->
                        <div class="col-auto">
                            <div class="main-logo">
                                <a href="index.jsp" class="navbar-brand ms-4 ms-lg-0">
                                    <h1 class="fw-bold text-primary m-0">Book<span class="text-secondary"> Store</span></h1>
                                </a>
                            </div>
                        </div>

                        <!-- Menu chính -->
                        <div class="col d-flex align-items-center">
                            <nav id="navbar" class="w-100">
                                <div class="main-menu stellarnav d-flex w-100">
                                    <!-- Home giữ nguyên bên trái -->
                                    <ul class="menu-list d-flex justify-content-start">
                                        <li class="menu-item active"><a href="index.jsp">Home</a></li>
                                    </ul>

                                    <!-- Đưa Danh mục sách cùng nhóm với Hồ sơ & Logout (căn phải) -->
                                    <ul class="menu-list d-flex ms-auto">
                                        <li class="menu-item has-sub">
                                            <a href="#" class="nav-link">Danh mục sách</a>

                                            <ul class="mega-menu">
                                                <div class="menu-row">
                                                    <c:set var="itemsPerColumn" value="5" />
                                                    <c:set var="numColumns" value="${(totalCategories / itemsPerColumn) + (totalCategories % itemsPerColumn == 0 ? 0 : 1)}" />

                                                    <c:forEach var="cate" items="${categories}" varStatus="status">
                                                        <c:if test="${status.index % itemsPerColumn == 0}">
                                                            <div class="menu-column"> <!-- Mở cột mới -->
                                                            </c:if>

                                                            <li><a href="Category.jsp?id=${cate.categoryId}">${cate.categoryName}</a></li>

                                                            <c:if test="${status.index % itemsPerColumn == itemsPerColumn - 1 or status.last}">
                                                            </div> <!-- Đóng cột -->
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                            </ul>
                                                    
                                        </li>

                                        <li class="menu-item"><a class="nav-link" href="UserProfile.jsp">Hồ sơ</a></li>
                                        <li class="menu-item"><a class="nav-link" href="logout">Logout</a></li>
                                    </ul>
                                </div>
                            </nav>
                        </div>

                        <!-- Hamburger menu -->
                        <div class="col-auto">
                            <div class="hamburger">
                                <span class="bar"></span>
                                <span class="bar"></span>
                                <span class="bar"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </header>


        </div><!--header-wrap-->
    </body>
</html>