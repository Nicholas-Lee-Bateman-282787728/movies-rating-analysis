<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<html lang="en">
    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>MoRe</title>
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
        <link href="/css/vendor-manager.css" rel="stylesheet">

    </head>

    <body>

        <!-- Header -->
        <jsp:include page="../fragments/header.jsp"/>

        <c:set var="currentUser" value="${sessionScope.USER}"/>

        <div class="container text-center sorry-message">
            <c:if test="${currentUser.role == 0}">

                <c:set var="vendors" value="${requestScope.VENDORS}"/>

                <div class="row">
                    <div class="col-md-6">
                        <c:import charEncoding="UTF-8" url="/xsl/vendor-manager.xsl" var="xslDoc"/>
                        <x:transform doc="${vendors}" xslt="${xslDoc}"/>
                    </div>

                    <div class="col-md-6">
                        <form id="form-signup" class="form-signin">
                            <div id="inform">

                            </div>
                            <label for="username" class="sr-only">Username</label>
                            <input type="text" id="username" name="username" class="form-control"
                                   placeholder="Tên tài khoản" required autofocus>
                            <label for="password" class="sr-only">Password</label>
                            <input type="password" id="password" name="password" class="form-control"
                                   placeholder="Mật khẩu"
                                   required>
                            <label for="re-password" class="sr-only">Re-enter Password</label>
                            <input type="password" id="re-password" class="form-control" placeholder="Nhập lại mật khẩu"
                                   required>
                            <label for="name" class="sr-only">Tên khách hàng</label>
                            <input type="text" id="name" name="name" class="form-control" placeholder="Tên khách hàng"
                                   required autofocus>
                            <label for="address1" class="sr-only">Địa chỉ</label>
                            <input type="text" id="address1" name="address1" class="form-control" placeholder="Địa chỉ"
                                   required autofocus>
                            <label for="address2" class="sr-only">Địa chỉ bổ sung</label>
                            <input type="text" id="address2" name="address2" class="form-control"
                                   placeholder="Địa chỉ bổ sung" required autofocus>
                            <label for="tel" class="sr-only">Số điện thoại</label>
                            <input type="text" id="tel" name="tel" class="form-control" placeholder="Số điện thoại"
                                   required
                                   autofocus>
                            <label for="email" class="sr-only">Email</label>
                            <input type="text" id="email" name="email" class="form-control" placeholder="Email" required
                                   autofocus>
                            <button class="btn btn-lg btn-success btn-block" type="button"
                                    onclick="signUp('form-signup');">
                                Thêm khách hàng
                            </button>
                        </form>
                    </div>
                </div>
            </c:if>

            <c:if test="${currentUser.role != 0}">
                <h3 id="message">Xin lỗi!<br/>Bạn không có quyền truy cập trang này</h3>
            </c:if>
        </div>

        <!-- Footer -->
        <jsp:include page="../fragments/footer.jsp"/>

    </body>
</html>