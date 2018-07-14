<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
    <body>

        <c:set var="user" value="${sessionScope.USER}"/>

        <nav id="main-header">
            <div id="main-header-wrapper">
                <div id="logo" onclick="goToHome()" style="cursor: pointer;">
                </div>
                <span id="MoRe-text">AK Phim</span>

                <c:if test="${not empty user}">
                    <div id="account-tool">
                        <span style="color: #28a745;">
                            ${user.lastName} ${user.middleName} ${user.firstName}
                        </span>
                        <a href="/dang-xuat" style="color: black; margin-left: 15%;">
                            <span>Đăng xuất</span>
                        </a>
                    </div>
                </c:if>

                <c:if test="${empty user}">
                    <div id="account-tool">
                        <a href="/dang-nhap">
                            <span>Sign In</span>
                        </a>
                        <a href="/dang-ky" style="cursor: pointer">
                            <button>
                                Sign Up
                            </button>
                        </a>
                    </div>
                </c:if>

            </div>
        </nav>

        <script>
            function goToHome() {
                window.location.replace("http://localhost:8080/");
            }
        </script>
    </body>
</html>