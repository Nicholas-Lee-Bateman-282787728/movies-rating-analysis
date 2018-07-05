<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>AK Phim</title>
        <link rel="icon" href="/img/Logo_K.svg">
        <!-- Bootstrap core CSS -->
        <link href="/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom fonts for this template -->
        <link href="/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="/vendor/simple-line-icons/css/simple-line-icons.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic"
              rel="stylesheet" type="text/css">
        <!-- Custom styles for this template -->
        <link href="/css/master.css" rel="stylesheet">
        <link href="/css/phim-moi.css" rel="stylesheet">
        <link href="/css/chi-tiet.css" rel="stylesheet">
    </head>
    <body>
        <!-- Header -->
        <jsp:include page="../fragments/header.jsp"/>

        <div class="container text-center sorry-message">
            <h1 class="message">Bạn chưa cung cấp dữ liệu</h1>
            <br>
            <h2 class="message">Vui lòng liên hệ với chúng tôi để biết thêm chi tiết</h2>
            <br>
            <div class="text-center">
                <img src="/img/contact2.jpg" class="img-responsive" style="width: 40%" alt="">
            </div>
            <br>
            <a href="/">
                <button class="btn btn-success">Quay lại</button>
            </a>
        </div>

        <!-- Footer -->
        <jsp:include page="../fragments/footer.jsp"/>
    </body>
</html>