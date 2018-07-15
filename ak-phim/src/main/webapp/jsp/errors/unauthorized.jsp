<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<!DOCTYPE html>
<html>

    <head>
        <title>AK Phim</title>
        <link rel="icon" href="/img/Logo_K.svg">
        <meta charset="utf-8" />
        <meta name="author" content="Thai Ly Anh Khue" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes" />
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <link rel="stylesheet" href="/vendor/font-awesome/css/font-awesome.min.css">

        <link rel="stylesheet" type="text/css" media="screen" href="/css/master.css" />
        <link href="https://unpkg.com/ionicons@4.2.4/dist/css/ionicons.min.css" rel="stylesheet">

        <link rel="stylesheet" href="/css/home.css">
        <link rel="stylesheet" href="/css/main.css">
        <link rel="stylesheet" href="/css/detail.css">
        <link rel="stylesheet" href="/css/error.css">
    </head>

    <body>

        <!-- Header -->
        <jsp:include page="../fragments/header.jsp"/>

        <section id="main-body">
            <div id="main-body-wrapper">
                <div class="message">
                    Rất tiếc!
                </div>
                <div class="message">
                    Bạn không có quyền truy cập trang này
                </div>
                <img class="error-img" src="/img/no-entry.png">
                <button onclick="location.replace('http://localhost:8080/');"
                        class="return-homepage"
                        style="cursor: pointer;">
                    Quay về trang chủ
                </button>
            </div>
        </section>

        <!-- Footer -->
        <c:import url="../fragments/footer.jsp"/>
    </body>

</html>