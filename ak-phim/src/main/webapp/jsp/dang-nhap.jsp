<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Đăng nhập</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <link href="/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="/vendor/simple-line-icons/css/simple-line-icons.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="/css/master.css">
    <link rel="stylesheet" href="/css/dang-nhap.css">

</head>

<body>
    <div class="login-page">
        <img src="/img/Logo_K.svg" alt="">
        <br/>
        <span class="website-name">AK Phim</span>
        <div class="form">
            <div id="error">
            </div>
            <form class="login-form">
                <input type="text" placeholder="Tên tài khoản" id="username" />
                <input type="password" placeholder="Mật khẩu" id="password" />
                <button type="button" onclick="authenticate()">Đăng nhập</button>
                <p class="message">Bạn chưa có tài khoản?
                    <a href="/dang-ky">Đăng ký</a>
                </p>
            </form>
        </div>
    </div>

    <script src="/js/account-request.js" type="text/javascript"></script>
</body>

</html>