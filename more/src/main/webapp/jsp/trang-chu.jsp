<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

        <!-- Icons Grid -->
        <section class="features-icons bg-light text-center">
            <div class="container">
                <div class="row">
                    <div class="col-lg-4">
                        <a href="phim-moi.html">
                            <div class="features-icons-item mx-auto mb-0 mb-lg-3">
                                <div class="features-icons-icon d-flex">
                                    <i class="icon-layers m-auto text-success"></i>
                                </div>
                                <h3>Dành cho bạn</h3>
                            </div>
                        </a>
                    </div>
                    <div class="col-lg-4">
                        <a href="phim-moi.html">
                            <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">
                                <div class="features-icons-icon d-flex">
                                    <i class="icon-film m-auto text-success"></i>
                                </div>
                                <h3>Mới cập nhật</h3>
                            </div>
                        </a>
                    </div>
                    <div class="col-lg-4">
                        <a href="sap-ra-mat.html">
                            <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">
                                <div class="features-icons-icon d-flex">
                                    <i class="icon-event m-auto text-success"></i>
                                </div>
                                <h3>Sắp ra mắt</h3>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
        </section>

        <!-- Movies -->
        <section class="movies text-center bg-light">
            <div class="container">
                <h2 class="mb-5">Phim mới</h2>
                <%-- Movie List --%>
                <div id="movies">

                </div>
            </div>
        </section>

        <!-- Footer -->
        <jsp:include page="fragments/footer.jsp"/>

        <script src="/js/movie-request.js" type="text/javascript"></script>

        <script type="text/javascript">
            // self executing function
            (function () {
                // Get Movies
                getTopMovies();
            })();

            function getTopMovies() {
                var url = '/movies/top/page=1&no=8';
                getMovies(url);
            }
        </script>
    </body>

</html>