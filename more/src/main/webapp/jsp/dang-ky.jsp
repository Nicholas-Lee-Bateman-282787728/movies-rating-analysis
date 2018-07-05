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

        <title>Đăng ký</title>

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
        <form id="form-signup" class="form-signin">
            <img class="mb-4" src="/img/Logo_K.svg" alt="" width="72" height="72">
            <h1 class="h3 mb-3 font-weight-normal"><span class="web-name">AK Phim</span></h1>
            <div id="inform">

            </div>
            <label for="username" class="sr-only">Username</label>
            <input type="text" id="username" name="username"
                   class="form-control" placeholder="Tên tài khoản"
                   required autofocus>
            <label for="password" class="sr-only">Password</label>
            <input type="password" id="password" name="password"
                   class="form-control" placeholder="Mật khẩu"
                   required>
            <label for="re-password" class="sr-only">Re-enter Password</label>
            <input type="password" id="re-password"
                   class="form-control" placeholder="Nhập lại mật khẩu"
                   required>
            <label for="lastName" class="sr-only">Họ</label>
            <input type="text" id="lastName" name="lastName"
                   class="form-control" placeholder="Họ"
                   required autofocus>
            <label for="middleName" class="sr-only">Tên đệm</label>
            <input type="text" id="middleName" name="middleName"
                   class="form-control" placeholder="Tên đệm"
                   autofocus>
            <label for="firstName" class="sr-only">Tên</label>
            <input type="text" id="firstName" name="firstName"
                   class="form-control" placeholder="Tên"
                   required autofocus>
            <div class="form-group">
                <label for="gender" class="sr-only">Giới tính</label>
                <select class="form-control" id="gender" name="gender">
                    <option value="1">Nam</option>
                    <option value="0">Nữ</option>
                </select>
            </div>
            <button class="btn btn-lg btn-success btn-block" type="button"
                    onclick="signUp('form-signup');">
                Đăng ký
            </button>
            <p class="mt-5 mb-3 text-muted">&copy; 2018</p>
        </form>

        <script src="/js/account-request.js" type="text/javascript"></script>
    </body>
</html>
