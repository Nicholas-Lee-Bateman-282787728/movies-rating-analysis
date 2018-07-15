<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Phim mới</title>
        <link rel="icon" href="/img/Logo_K.svg">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <link href="/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="/vendor/simple-line-icons/css/simple-line-icons.css" rel="stylesheet" type="text/css">

        <link rel="stylesheet" type="text/css" media="screen" href="css/master.css"/>
        <link href="https://unpkg.com/ionicons@4.2.4/dist/css/ionicons.min.css" rel="stylesheet">

        <link rel="stylesheet" href="/css/main.css">
        <link rel="stylesheet" href="/css/home.css">
        <link rel="stylesheet" href="/css/list.css">

    </head>

    <body>

        <script>
            var totalPages = parseInt('${requestScope.TOTAL_PAGES}');
            var currentPage = 1;
            var moviePerPage = 12;
        </script>

        <!-- Header -->
        <jsp:include page="fragments/header.jsp"/>

        <%-- Banner --%>
        <jsp:include page="fragments/banner.jsp">
            <jsp:param name="bannerImg" value="/img/bg-masthead-2.jpg"/>
            <jsp:param name="adQuote" value="Luôn cập nhật các tác phẩm điện ảnh mới nhất"/>
        </jsp:include>

        <div id="main-body-wrapper">
            <section>
                <div class="main-panel">
                    <div class="panel-header">
                        <div class="panel-title" id="anchor">
                            Phim mới cập nhật
                        </div>
                    </div>

                    <div id="movies">

                    </div>
                </div>

            </section>

            <div id="pagination" class="pagination">

            </div>
        </div>

        <!-- Footer -->
        <jsp:include page="fragments/footer.jsp"/>

        <script src="/js/client-cache.js" type="text/javascript"></script>
        <script src="/js/more/MovieAccessor.js" type="text/javascript"></script>
        <script src="/js/more/MoviePresenter.js" type="text/javascript"></script>
        <script src="/js/more/Pagination.js" type="text/javascript"></script>

        <script type="text/javascript">
            document.onreadystatechange = function () {
                var pagination = new Pagination(currentPage, 'top', null);

                pagination.createPagination('pagination');
                var movieAccessor = new MovieAccessor('top', currentPage, moviePerPage);

                var moviePresenter = new MoviePresenter('movies', movieAccessor, null);
                if (sessionStorage.getItem('topMovies') === null) {
                    topNewMovies(moviePerPage * 10, moviePresenter);
                } else {
                    moviePresenter.presentByPage(currentPage, moviePerPage);
                }
            };
        </script>

    </body>

</html>