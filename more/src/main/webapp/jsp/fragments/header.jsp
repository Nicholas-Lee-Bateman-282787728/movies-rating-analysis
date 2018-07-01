<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
    <body>
        <c:set var="user" value="${sessionScope.USER}"/>
        <!-- Navigation -->
        <nav class="navbar navbar-light bg-light static-top">
            <div class="container">
                <a class="navbar-brand" href="/">
                    <img src="img/Logo_K.svg" alt="">
                    <span class="web-name">MoRe</span>
                </a>
                <div class="d-flex flex-row-reverse">
                    <c:if test="${not empty user}">
                        <a class="btn btn-success" href="/dang-xuat">Đăng xuất</a>
                        <a class="btn text-success" href="#">
                                ${user.lastName} ${user.middleName} ${user.firstName}
                        </a>
                    </c:if>
                    <c:if test="${empty user}">
                        <a class="btn btn-success" href="/dang-ky">Đăng ký</a>
                        <a class="btn text-success" href="/dang-nhap">Đăng nhập</a>
                    </c:if>
                </div>
            </div>
        </nav>
    </body>
</html>