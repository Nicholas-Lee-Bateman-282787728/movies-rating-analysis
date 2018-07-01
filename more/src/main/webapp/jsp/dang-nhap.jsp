<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="/img/Logo_K.svg">

        <title>Đăng nhập</title>

        <link href="/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom fonts for this template -->
        <link href="/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic"
              rel="stylesheet"
              type="text/css">
        <!-- Custom styles for this template -->
        <link href="/css/dang-nhap.css" rel="stylesheet">
    </head>

    <body class="text-center">
        <form class="form-signin">
            <img class="mb-4" src="/img/Logo_K.svg" alt="" width="72" height="72">
            <h1 class="h3 mb-3 font-weight-normal"><span class="web-name">MoRe</span></h1>
            <div id="error">

            </div>
            <label for="username" class="sr-only">Username</label>
            <input type="text" id="username" class="form-control" placeholder="Tên tài khoản"
                   value="${sessionScope.NEW_USERNAME}"
                   required autofocus>
            <label for="password" class="sr-only">Password</label>
            <input type="password" id="password" class="form-control" placeholder="Mật khẩu" required>
            <button class="btn btn-lg btn-success btn-block" type="button"
                    onclick="authenticate()">
                Đăng nhập
            </button>
            <p class="mt-5 mb-3 text-muted">&copy; 2018</p>
        </form>

        <script src="/js/account-request.js" type="text/javascript"></script>
    </body>
</html>
