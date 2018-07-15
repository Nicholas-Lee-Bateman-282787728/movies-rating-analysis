<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Đăng ký</title>
        <link rel="icon" href="/img/Logo_K.svg">
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <link href="/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="/vendor/simple-line-icons/css/simple-line-icons.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="/css/master.css">
        <link rel="stylesheet" href="/css/dang-ky.css">
    </head>

    <body>

        <div class="login-page">
            <img src="/img/Logo_K.svg" alt="">
            <br/>
            <span class="website-name">AK Phim</span>
            <div class="form">
                <div id="inform">

                </div>
                <form id="form-signup" class="register-form">
                    <input type="text" placeholder="Tên tài khoản"
                           required
                           id="username"
                           name="username"/>
                    <input type="password" placeholder="Mật khẩu"
                           required
                           id="password"
                           name="password"/>
                    <input type="password" placeholder="Nhập lại mật khẩu"
                           required
                           id="re-password"
                           name="re-password"/>
                    <input type="text" placeholder="Họ"
                           required
                           id="lastName"
                           name="lastName"/>
                    <input type="text" placeholder="Tên đệm"
                           id="middleName"
                           name="middleName"/>
                    <input type="text" placeholder="Tên"
                           required
                           id="firstName"
                           name="firstName"/>
                    <select id="gender" name="gender">
                        <option value="1">Nam</option>
                        <option value="0">Nữ</option>
                    </select>
                    <button type="button" onclick="signUp('form-signup');">Tạo tài khoản</button>
                    <p class="message">Bạn đã có tài khoản
                        <a href="/dang-nhap">Đăng nhập</a>
                    </p>
                </form>
            </div>
        </div>

        <script src="/js/account-request.js" type="text/javascript"></script>
    </body>

</html>