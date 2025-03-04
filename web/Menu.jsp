<%@page import="entity.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    Account acc = (Account) session.getAttribute("account");
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

        <link rel="stylesheet" type="text/css" href="css/normalize.css">
        <link rel="stylesheet" type="text/css" href="icomoon/icomoon.css">
        <link rel="stylesheet" type="text/css" href="css/vendor.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
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
                            <div class="right-element d-flex align-items-center justify-content-end">
                                <c:if test="${empty sessionScope.account}">
                                    <a href="Login.jsp" class="user-account for-buy">
                                        <i class="icon icon-user"></i><span>Login</span>
                                    </a>
                                </c:if>

                                <c:if test="${not empty sessionScope.account}">
                                    <a class="nav-link" href="UserProfile.jsp">Hello, ${sessionScope.account.username}</a>
                                    <a class="nav-link" href="logout">Logout</a>
                                </c:if>  

                                <a href="#" class="cart for-buy"><i class="icon icon-clipboard"></i></a>

                                <div class="action-menu d-flex align-items-center">
                                    <div class="search-bar">
                                        <a href="#" class="search-button search-toggle" data-selector="#header-wrap">
                                            <i class="icon icon-search"></i>
                                        </a>
                                        <form role="search" method="get" class="search-box">
                                            <input class="search-field text search-input" placeholder="Search"
                                                   type="search">
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
                    <div class="row">
                        <div class="col-md-2">
                            <div class="main-logo">
                                <a href="Home.jsp"><img src="images/main-logo2.png" alt="logo"></a>
                            </div>
                        </div>

                        <div class="col-md-10">
                            <nav id="navbar">
                                <div class="main-menu stellarnav">
                                    <ul class="menu-list">
                                        <li class="menu-item active"><a href="Home.jsp">Home</a></li>
                                        <li class="menu-item has-sub">
                                            <a href="#" class="nav-link">Pages</a>
                                            <ul>
                                                <li><a href="About.jsp">About</a></li>
                                                <li><a href="Styles.jsp">Styles</a></li>
                                                <li><a href="Blog.jsp">Blog</a></li>
                                                <li><a href="PostSingle.jsp">Post Single</a></li>
                                                <li><a href="Store.jsp">Our Store</a></li>
                                                <li><a href="ProductSingle.jsp">Product Single</a></li>
                                                <li><a href="Contact.jsp">Contact</a></li>
                                            </ul>
                                        </li>
                                        <li class="menu-item"><a href="FeaturedBooks.jsp">Featured</a></li>
                                        <li class="menu-item"><a href="PopularBooks.jsp">Popular</a></li>
                                        <li class="menu-item"><a href="SpecialOffer.jsp">Offer</a></li>
                                        <li class="menu-item"><a href="LatestBlog.jsp">Articles</a></li>
                                        <li class="menu-item"><a href="DownloadApp.jsp">Download App</a></li>
                                    </ul>

                                    <div class="hamburger">
                                        <span class="bar"></span>
                                        <span class="bar"></span>
                                        <span class="bar"></span>
                                    </div>
                                </div>
                            </nav>
                        </div>

                    </div>
                </div>
            </header>
        </div><!--header-wrap-->
    </body>
</html>
