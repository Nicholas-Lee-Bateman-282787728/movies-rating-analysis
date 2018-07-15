<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>AK Phim</title>
        <link rel="icon" href="/img/Logo_K.svg">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" media="screen" href="/css/main.css"/>
        <link rel="stylesheet" type="text/css" media="screen" href="/css/vendor-manager.css"/>
        <link rel="stylesheet" type="text/css" media="screen" href="/css/crawler-switch.css"/>
        <link href="https://unpkg.com/ionicons@4.2.4/dist/css/ionicons.min.css" rel="stylesheet">
    </head>

    <body>

        <c:set var="user" value="${sessionScope.USER}"/>

        <div class="wrapper">

            <div class="header">
                <div class="header-title">
                    <a href="/">
                        AK Phim
                    </a>
                </div>
                <div class="header-username">
                    ${user.lastName} ${user.middleName} ${user.firstName}
                </div>
                <div class="header-logout">
                    <a href="/dang-xuat" style="text-decoration: none; color: black">
                        Đăng xuất
                    </a>
                </div>
            </div>

            <div class="body">

                <c:set var="crawling" value="${sessionScope.CRAWLING}"/>

                <h1 class="message">Khởi động bộ thu thập dữ liệu</h1>
                <br/>
                <label class="switch">
                    <c:if test="${crawling == 'true'}">
                        <input type="checkbox" id="switch-crawler" onclick="check();" checked/>
                    </c:if>
                    <c:if test="${crawling == 'false'}">
                        <input type="checkbox" id="switch-crawler" onclick="check();"/>
                    </c:if>
                    <span class="slider round"></span>
                </label>
                <h3 id="message">

                </h3>
                <h3 id="wait"></h3>
            </div>

            <!-- Footer -->
            <c:import url="../fragments/footer.jsp"/>
        </div>

        <script type="text/javascript">
            document.onreadystatechange = function () {
                checkStatus();
            };
            
            function checkStatus() {
                var switchButton = document.getElementById('switch-crawler');
                var dotsIntervalId;
                var message = document.getElementById('message');
                var wait = document.getElementById("wait");

                message.innerHTML = '';

                if (switchButton.checked === true) {
                    message.innerHTML = 'Hệ thống đang thu thập dữ liệu<br/>';
                    wait.style.display = 'block';
                    dotsIntervalId = setInterval(function () {
                        var wait = document.getElementById("wait");
                        if (wait.innerHTML.length > 3) {
                            wait.innerHTML = "";
                        } else {
                            wait.innerHTML += ".";
                        }
                    }, 500);
                    localStorage.setItem("dotsIntervalId", dotsIntervalId);
                } else {
                    message.innerHTML = 'Đã tạm ngưng việc thu thập dữ liệu';
                    wait.style.display = 'none';
                    clearInterval(localStorage.getItem("dotsIntervalId"));
                }
            }

            function check() {
                var switchButton = document.getElementById('switch-crawler');
                var dotsIntervalId;
                var message = document.getElementById('message');
                var wait = document.getElementById("wait");

                message.innerHTML = '';

                var turn = switchButton.checked;

                var request = new XMLHttpRequest();
                var url = "/admin/crawler";
                var params = 'status=' + turn;

                request.open('POST', url, true);

                request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

                request.onreadystatechange = function () {
                    if (request.readyState === 4 && request.status === 200) {
                        message.innerHTML = request.responseText;
                    }
                };
                request.send(params);

                if (switchButton.checked === true) {
                    // message.innerHTML = 'Hệ thống đang thu thập dữ liệu<br/>';
                    wait.style.display = 'block';
                    dotsIntervalId = setInterval(function () {
                        var wait = document.getElementById("wait");
                        if (wait.innerHTML.length > 3) {
                            wait.innerHTML = "";
                        } else {
                            wait.innerHTML += ".";
                        }
                    }, 500);
                    localStorage.setItem("dotsIntervalId", dotsIntervalId);
                } else {
                    // message.innerHTML = 'Đã tạm ngưng việc thu thập dữ liệu';
                    wait.style.display = 'none';
                    clearInterval(localStorage.getItem("dotsIntervalId"));
                }
            }
        </script>
    </body>

</html>