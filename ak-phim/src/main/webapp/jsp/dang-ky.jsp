<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Đăng ký</title>
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
            <div class="alert alert-success">
                Đăng ký thành công<br/>Đang điều hướng về trang đăng nhập
            </div>
            <form class="register-form">
                <input type="text" placeholder="Tên tài khoản" />
                <input type="password" placeholder="Mật khẩu" />
                <input type="text" placeholder="Họ" />
                <input type="text" placeholder="Tên đệm" />
                <input type="text" placeholder="Tên" />
                <select name="" id="">
                    <option value="1">Nam</option>
                    <option value="0">Nữ</option>
                </select>
                <button>Tạo tài khoản</button>
                <p class="message">Bạn đã có tài khoản
                    <a href="/dang-nhap">Đăng nhập</a>
                </p>
            </form>
        </div>
    </div>
</body>

</html>