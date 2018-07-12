<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
              rel="stylesheet"
              type="text/css">
        <!-- Custom styles for this template -->
        <link href="/css/phim-moi.css" rel="stylesheet">
        <link href="/css/master.css" rel="stylesheet">
    </head>

    <body>
        <!-- Header -->
        <jsp:include page="fragments/header.jsp"/>

        <!-- Masthead -->
        <jsp:include page="fragments/masthead.jsp">
            <jsp:param name="adQuote" value="Khám phá thế giới điện ảnh của bạn"/>
        </jsp:include>

        <c:set var="user" value="${sessionScope.USER}"/>

        <!-- Icons Grid -->
        <section class="features-icons bg-light text-center">
            <div class="container">
                <%-- If not signed in --%>
                <c:if test="${empty user}">
                    <div class="row">
                        <div class="col-lg-6">
                            <a href="/phim-moi">
                                <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">
                                    <div class="features-icons-icon d-flex">
                                        <i class="icon-film m-auto text-success"></i>
                                    </div>
                                    <h3>Mới cập nhật</h3>
                                </div>
                            </a>
                        </div>
                        <div class="col-lg-6">
                            <a href="/sap-ra-mat">
                                <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">
                                    <div class="features-icons-icon d-flex">
                                        <i class="icon-event m-auto text-success"></i>
                                    </div>
                                    <h3>Sắp ra mắt</h3>
                                </div>
                            </a>
                        </div>
                    </div>
                </c:if>

                <%-- If signed in --%>
                <c:if test="${not empty user}">
                    <%-- Is Admin --%>
                    <c:if test="${user.role == 0}">
                        <div class="row">
                            <div class="col-lg-6">
                                <a href="/admin/vendor-manager">
                                    <div class="features-icons-item mx-auto mb-0 mb-lg-3">
                                        <div class="features-icons-icon d-flex">
                                            <i class="icon-people m-auto text-success"></i>
                                        </div>
                                        <h3>Quản lí khách hàng</h3>
                                    </div>
                                </a>
                            </div>
                            <div class="col-lg-6">
                                <a href="/admin/crawler-switch">
                                    <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">
                                        <div class="features-icons-icon d-flex">
                                            <i class="icon-cloud-download m-auto text-success"></i>
                                        </div>
                                        <h3>Quản lí dữ liệu</h3>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </c:if>

                    <%-- Is User --%>
                    <c:if test="${user.role == 1}">
                        <div class="row">
                            <div class="col-lg-4">
                                <a href="/danh-cho-ban">
                                    <div class="features-icons-item mx-auto mb-0 mb-lg-3">
                                        <div class="features-icons-icon d-flex">
                                            <i class="icon-layers m-auto text-success"></i>
                                        </div>
                                        <h3>Dành cho bạn</h3>
                                    </div>
                                </a>
                            </div>
                            <div class="col-lg-4">
                                <a href="/phim-moi">
                                    <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">
                                        <div class="features-icons-icon d-flex">
                                            <i class="icon-film m-auto text-success"></i>
                                        </div>
                                        <h3>Mới cập nhật</h3>
                                    </div>
                                </a>
                            </div>
                            <div class="col-lg-4">
                                <a href="/sap-ra-mat">
                                    <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">
                                        <div class="features-icons-icon d-flex">
                                            <i class="icon-event m-auto text-success"></i>
                                        </div>
                                        <h3>Sắp ra mắt</h3>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </c:if>

                    <%-- Is Vendor --%>
                    <c:if test="${user.role == 2}">
                        <div class="row">
                            <div class="col-lg-6">
                                <a href="/vendor/ranking-report">
                                    <div class="features-icons-item mx-auto mb-0 mb-lg-3">
                                        <div class="features-icons-icon d-flex">
                                            <i class="icon-graph m-auto text-success"></i>
                                        </div>
                                        <h3>Báo cáo xếp hạng</h3>
                                    </div>
                                </a>
                            </div>
                            <div class="col-lg-6">
                                <a href="/vendor/coming-prediction">
                                    <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">
                                        <div class="features-icons-icon d-flex">
                                            <i class="icon-calculator m-auto text-success"></i>
                                        </div>
                                        <h3>Dự đoán xếp hạng</h3>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </c:if>

                </c:if>

            </div>
        </section>

        <!-- Movies -->
        <%-- If not signed in --%>
        <c:if test="${empty user}">
            <section class="movies text-center bg-light">
                <div class="container">
                    <h2 class="mb-5">Phim mới</h2>
                        <%-- Movie List --%>
                    <div id="movies">

                    </div>
                </div>
            </section>
        </c:if>
        <%-- If signed in --%>
        <c:if test="${not empty user}">
            <%-- Is User --%>
            <c:if test="${user.role == 1}">
                <section class="movies text-center bg-light">
                    <div class="container">
                        <h2 class="mb-5">Phim mới</h2>
                            <%-- Movie List --%>
                        <div id="movies">

                        </div>
                    </div>
                </section>
            </c:if>
        </c:if>

        <!-- Footer -->
        <jsp:include page="fragments/footer.jsp"/>

        <script src="/js/client-cache.js" type="text/javascript"></script>
        <script src="/js/more/MovieAccessor.js" type="text/javascript"></script>
        <script src="/js/more/MoviePresenter.js" type="text/javascript"></script>
        <script src="/js/more/Pagination.js" type="text/javascript"></script>

        <script type="text/javascript">
            // self executing function
            /*(function () {
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
            })();*/

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