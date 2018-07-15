<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>AK Phim</title>
        <link rel="icon" href="/img/Logo_K.svg">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <link href="/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="/vendor/simple-line-icons/css/simple-line-icons.css" rel="stylesheet" type="text/css">

        <link rel="stylesheet" type="text/css" media="screen" href="/css/master.css"/>
        <link href="https://unpkg.com/ionicons@4.2.4/dist/css/ionicons.min.css" rel="stylesheet">

        <link rel="stylesheet" href="/css/main.css">
        <link rel="stylesheet" href="/css/home.css">

    </head>

    <body>

        <!-- Header -->
        <jsp:include page="fragments/header.jsp"/>

        <%-- Banner --%>
        <jsp:include page="fragments/banner.jsp">
            <jsp:param name="bannerImg" value="/img/bg-masthead-1.jpg"/>
            <jsp:param name="adQuote" value="Khám phá thế giới điện ảnh của bạn"/>
        </jsp:include>

        <section id="main-body">
            <section class="features-icons bg-light text-center">
                <div class="container">

                    <c:set var="user" value="${sessionScope.USER}"/>

                    <div class="tab-list">

                        <%-- If not signed in --%>
                        <c:if test="${empty user}">
                            <div class="tab">
                                <a href="/phim-moi">
                                    <div class="tab-item">
                                        <div class="features-icons-icon d-flex">
                                            <i class="icon-film m-auto text-success"></i>
                                        </div>
                                        <h3>Mới cập nhật</h3>
                                    </div>
                                </a>
                            </div>
                            <div class="tab">
                                <a href="/sap-ra-mat">
                                    <div class="tab-item">
                                        <div class="features-icons-icon d-flex">
                                            <i class="icon-event m-auto text-success"></i>
                                        </div>
                                        <h3>Sắp ra mắt</h3>
                                    </div>
                                </a>
                            </div>
                        </c:if>

                        <%-- If signed in --%>
                        <c:if test="${not empty user}">
                            <%-- Is Admin --%>
                            <c:if test="${user.role == 0}">
                                <div class="tab">
                                    <a href="/admin/vendor-manager">
                                        <div class="tab-item">
                                            <div class="features-icons-icon d-flex">
                                                <i class="icon-people m-auto text-success"></i>
                                            </div>
                                            <h3>Quản lí khách hàng</h3>
                                        </div>
                                    </a>
                                </div>
                                <div class="tab">
                                    <a href="/admin/crawler-switch">
                                        <div class="tab-item">
                                            <div class="features-icons-icon d-flex">
                                                <i class="icon-cloud-download m-auto text-success"></i>
                                            </div>
                                            <h3>Quản lí dữ liệu</h3>
                                        </div>
                                    </a>
                                </div>
                            </c:if>
                        </c:if>

                        <%-- Is User --%>
                        <c:if test="${user.role == 1}">
                            <div class="tab">
                                <a href="/danh-cho-ban">
                                    <div class="tab-item">
                                        <div class="features-icons-icon d-flex">
                                            <i class="icon-layers m-auto text-success"></i>
                                        </div>
                                        <h3>Dành cho bạn</h3>
                                    </div>
                                </a>
                            </div>
                            <div class="tab">
                                <a href="/phim-moi">
                                    <div class="tab-item">
                                        <div class="features-icons-icon d-flex">
                                            <i class="icon-film m-auto text-success"></i>
                                        </div>
                                        <h3>Mới cập nhật</h3>
                                    </div>
                                </a>
                            </div>
                            <div class="tab">
                                <a href="/sap-ra-mat">
                                    <div class="tab-item">
                                        <div class="features-icons-icon d-flex">
                                            <i class="icon-event m-auto text-success"></i>
                                        </div>
                                        <h3>Sắp ra mắt</h3>
                                    </div>
                                </a>
                            </div>
                        </c:if>

                        <%-- Is Vendor --%>
                        <c:if test="${user.role == 2}">
                            <div class="tab">
                                <a href="/vendor/ranking-report">
                                    <div class="tab-item">
                                        <div class="features-icons-icon d-flex">
                                            <i class="icon-graph m-auto text-success"></i>
                                        </div>
                                        <h3>Báo cáo xếp hạng</h3>
                                    </div>
                                </a>
                            </div>
                            <div class="tab">
                                <a href="/vendor/coming-prediction">
                                    <div class="tab-item">
                                        <div class="features-icons-icon d-flex">
                                            <i class="icon-calculator m-auto text-success"></i>
                                        </div>
                                        <h3>Dự đoán xếp hạng</h3>
                                    </div>
                                </a>
                            </div>
                        </c:if>

                    </div>

                </div>
            </section>

            <!-- Movies -->
            <%-- If not signed in --%>
            <c:if test="${empty user}">
                <div id="main-body-wrapper">
                    <section>
                        <div class="main-panel">
                            <div class="panel-header">
                                <div class="panel-title">
                                    Phim mới cập nhật
                                </div>
                            </div>
                            <div id="movies">

                            </div>
                        </div>
                    </section>
                </div>
            </c:if>

            <%-- If signed in and is User --%>
            <c:if test="${not empty user}">
                <c:if test="${user.role == 1}">
                    <div id="main-body-wrapper">
                        <section>
                            <div class="main-panel">
                                <div class="panel-header">
                                    <div class="panel-title">
                                        Phim mới cập nhật
                                    </div>
                                </div>
                                <div id="movies">

                                </div>
                            </div>
                        </section>
                    </div>
                </c:if>
            </c:if>

        </section>

        <!-- Footer -->
        <c:import url="fragments/footer.jsp"/>

        <script src="/js/client-cache.js" type="text/javascript"></script>
        <script src="/js/more/MovieAccessor.js" type="text/javascript"></script>
        <script src="/js/more/MoviePresenter.js" type="text/javascript"></script>
        <script src="/js/more/Pagination.js" type="text/javascript"></script>

        <script type="text/javascript">
            document.onreadystatechange = function () {
                var movieAccessor = new MovieAccessor('top', 1, 8);

                var moviePresenter = new MoviePresenter('movies', movieAccessor, null);
                if (sessionStorage.getItem('topMovies') === null) {
                    topNewMovies(12 * 10, moviePresenter);
                } else {
                    moviePresenter.presentByPage(1, 8);
                }
                if (sessionStorage.getItem('comingMovies') === null) {
                    topComingMovies(12 * 10, null);
                }
                if (sessionStorage.getItem('recommendedMovies') === '') {
                    topRecommendedMovies(12 * 10, null);
                }
            };
        </script>

    </body>

</html>