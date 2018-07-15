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
        <link href="/css/crawler-switch.css" rel="stylesheet">
    </head>
    <body>
        <!-- Header -->
        <jsp:include page="../fragments/header.jsp"/>

        <c:set var="currentUser" value="${sessionScope.USER}"/>

        <div class="container text-center sorry-message">
            <c:if test="${currentUser.role == 0}">
                <h1 class="message">Khởi động bộ thu thập dữ liệu</h1>
                <c:set var="crawling" value="${sessionScope.CRAWLING}"/>
                <c:choose>
                    <c:when test="${crawling == 'true'}">
                        <label class="switch">
                            <input type="checkbox" id="switch-crawler" checked onclick="check();">
                            <span class="slider round"></span>
                        </label>
                        <h3 id="message">Hệ thống đang thu thập dữ liệu</h3>
                    </c:when>
                    <c:otherwise>
                        <label class="switch">
                            <input type="checkbox" id="switch-crawler" onclick="check();">
                            <span class="slider round"></span>
                        </label>
                        <h3 id="message">Đã tạm ngưng việc thu thập dữ liệu<br/></h3>
                    </c:otherwise>
                </c:choose>
                <h3 id="wait"></h3>
            </c:if>

            <c:if test="${currentUser.role != 0}">
                <h3 id="message">Xin lỗi!<br/>Bạn không có quyền truy cập trang này</h3>
            </c:if>
        </div>

        <!-- Footer -->
        <jsp:include page="../fragments/footer.jsp"/>

        <script type="text/javascript">
            function check() {
                var switchButton = document.getElementById('switch-crawler');
                var turn = switchButton.checked;

                var message = document.getElementById('message');
                message.innerHTML = '';

                var wait = document.getElementById("wait");


                var request = new XMLHttpRequest();
                var url = "/admin/crawler";
                console.log(turn);
                var params = 'status=' + turn;

                request.open('POST', url, true);

                request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

                request.onreadystatechange = function () {
                    if (request.readyState === 4 && request.status === 200) {
                        message.innerHTML = request.responseText;
                        if (switchButton.checked === true) {
                            wait.hidden = false;
                            var dots = window.setInterval(function () {
                                var wait = document.getElementById("wait");
                                if (wait.innerHTML.length > 3)
                                    wait.innerHTML = "";
                                else
                                    wait.innerHTML += ".";
                            }, 800);
                        } else {
                            wait.hidden = true;
                        }
                    }
                };
                request.send(params);
            }
        </script>
    </body>
</html>