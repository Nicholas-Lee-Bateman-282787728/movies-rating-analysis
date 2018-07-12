<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Chi tiết</title>
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
    </head>
    <body>
        <script>
            var movie = '${requestScope.MOVIE}';
            // Create Parser
            var parser = new DOMParser();

            movie = movie.replace(new RegExp(' xmlns(:.*?)?=(".*?")'), '');
            var xmlDoc = parser.parseFromString(movie, "text/xml");

            // Retrieve Node List of movies
            var movieId = xmlDoc.evaluate('//id', xmlDoc, null, XPathResult.STRING_TYPE, null).stringValue;
        </script>

        <!-- Header -->
        <jsp:include page="fragments/header.jsp"/>

        <c:set var="movie" value="${requestScope.MOVIE}"/>

        <%-- If movie not null --%>
        <c:if test="${not empty movie}">
            <div class="container">
                <div class="row wrapper">
                    <c:import charEncoding="UTF-8" url="/xsl/movie-detail.xsl" var="xslDoc"/>
                    <x:transform doc="${movie}" xslt="${xslDoc}"/>
                </div>
                <!-- Recommend -->
                <section class="testimonials text-center">
                    <div class="container">
                        <h2 class="mb-5">Có thể bạn sẽ thích</h2>
                        <div class="row" id="similar">
                            <div class="col-lg-3">
                                <div class="testimonial-item mx-auto mb-5 mb-lg-0">
                                    <img class="img-thumbnail mb-3" src="img/justice_league.jpeg" alt="">
                                    <h5>Liên minh công lý</h5>
                                </div>
                            </div>
                            <div class="col-lg-3">
                                <div class="testimonial-item mx-auto mb-5 mb-lg-0">
                                    <img class="img-thumbnail mb-3" src="img/infinity_war.jpg" alt="">
                                    <h5>Biệt đội siêu anh hùng: Cuộc chiến vô cực</h5>
                                </div>
                            </div>
                            <div class="col-lg-3">
                                <div class="testimonial-item mx-auto mb-5 mb-lg-0">
                                    <img class="img-thumbnail mb-3" src="img/black_panther.jpeg" alt="">
                                    <h5>Chiến binh Báo đen</h5>
                                </div>
                            </div>
                            <div class="col-lg-3">
                                <div class="testimonial-item mx-auto mb-5 mb-lg-0">
                                    <img class="img-thumbnail mb-3" src="img/spider_man.jpeg" alt="">
                                    <h5>Người Nhện: Về nhà</h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </c:if>
        <%-- End if movie not null --%>

        <%-- If movie null --%>
        <c:if test="${empty movie}">
            <div class="container text-center sorry-message">
                <h1 class="message">Xin lỗi!</h1>
                <br>
                <h2 class="message">Chúng tôi không thể tìm thấy trang bạn yêu cầu</h2>
                <br>
                <img src="/img/panda-404.png" alt="">
                <br>
                <a href="/">
                    <button class="btn btn-success">Quay lại</button>
                </a>
            </div>
        </c:if>
        <%-- End if movie null --%>

        <!-- Footer -->
        <jsp:include page="fragments/footer.jsp"/>
        <script src="/js/client-cache.js" type="text/javascript"></script>

        <script type="text/javascript">
            // self executing function
            /*(function () {
                getHighVote();
                getSimilarMovies();
            })();*/

            document.onreadystatechange = function () {
                getHighVote();
                getSimilarMovies();
            };

            function rate(star) {
                var stars = document.getElementsByClassName('star');

                for (var i = 0; i < stars.length; i++) {
                    stars[i].classList.remove('checked');
                }

                for (i = 1; i <= star; i++) {
                    stars[i - 1].classList.add('checked');
                }

                var movieId = document.getElementById('movie-id').value;

                var request = new XMLHttpRequest();
                var url = '/movies/' + movieId + '/rate';
                var params = 'rating=' + star;
                request.open('POST', url, true);

                // Send the proper header information along with the request
                request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

                request.onreadystatechange = function () {
                    if (request.readyState === 4 && request.status === 200) {
                        var movieRating = document.getElementById('movie-rating');
                        movieRating.innerHTML = request.responseText + ' <span class="fa fa-star"/>';

                        var message = document.getElementById('message');
                        message.innerHTML = "Cảm ơn phản hồi của bạn!";
                        topRecommendedMovies(12 * 10, null);
                    } else if (request.status === 401) {
                        message = document.getElementById('message');

                        message.innerHTML = "";
                        var errorMessage = document.createElement('div');
                        errorMessage.classList.add('alert', 'alert-danger');

                        errorMessage.innerHTML = request.responseText;
                        message.appendChild(errorMessage);
                    }
                };

                request.send(params);
            }

            function getHighVote() {
                var vote = document.getElementById('vote');

                var request = new XMLHttpRequest();
                var url = '/movies/' + movieId + '/high-vote';

                request.open('GET', url, true);

                request.onreadystatechange = function () {
                    if (request.readyState === 4 && request.status === 200) {
                        vote.innerHTML = '';
                        var highVote = request.responseText;
                        var parser = new DOMParser();

                        highVote = highVote.replace(new RegExp(' xmlns(:.*?)?=(".*?")'), '');
                        var xmlDoc = parser.parseFromString(highVote, "text/xml");

                        var percentage = xmlDoc.evaluate('//percentage', xmlDoc, null, XPathResult.STRING_TYPE, null).stringValue;
                        percentage = parseFloat(percentage).toFixed(2);
                        var totalVote = xmlDoc.evaluate('//totalVote', xmlDoc, null, XPathResult.STRING_TYPE, null).stringValue;

                        var percentageStrong = document.createElement('strong');
                        percentageStrong.append(percentage + ' %');
                        vote.appendChild(percentageStrong);
                        vote.append(' người xem đánh giá cao phim này ');
                        var voteStrong = document.createElement('strong');
                        voteStrong.append('(' + totalVote + ' votes)');
                        vote.appendChild(voteStrong);
                    } else if (request.status === 404) {
                        vote.innerHTML = '';
                        vote.append('Chưa có ai đánh giá phim này.');
                    }
                };

                request.send();
            }
            
            function getSimilarMovies() {
                var similarContent = document.getElementById('similar');

                var request = new XMLHttpRequest();
                var url = '/movies/' + movieId + '/recommended';

                request.open('GET', url, true);

                request.onreadystatechange = function () {
                    if (request.readyState === 4 || request.status === 200) {
                        similarContent.innerHTML = '';
                        var xmlString = request.responseText;
                        xmlString = xmlString.replace(new RegExp(' xmlns(:.*?)?=(".*?")'), '');
                        var parser = new DOMParser();
                        var xmlDoc = parser.parseFromString(xmlString, "text/xml");

                        var xPath = '//movie';
                        var movies = xmlDoc.evaluate(xPath, xmlDoc, null, XPathResult.ANY_TYPE, null);

                        var movie = movies.iterateNext();

                        while (movie) {
                            // Wrapper div
                            var wrapper = document.createElement('div');
                            wrapper.className = "col-lg-3";

                            // Link to Detail
                            var detailLink = document.createElement('a');
                            detailLink.href = 'http://localhost:8080/phim/';

                            // Main div for showing movie
                            var item = document.createElement('div');
                            item.className = "movie-item mx-auto mb-5 mb-lg-0";

                            // Retrieve ID
                            var id = xmlDoc.evaluate('id', movie, null, XPathResult.STRING_TYPE, null).stringValue;
                            detailLink.href += id;

                            // Retrieve poster url
                            var poster = xmlDoc.evaluate('poster', movie, null, XPathResult.STRING_TYPE, null).stringValue;
                            var imgTag = document.createElement('img');
                            imgTag.className = "img-thumbnail mb-3";
                            imgTag.src = poster;
                            item.appendChild(imgTag);

                            // Retrieve movie title
                            var title = xmlDoc.evaluate('title', movie, null, XPathResult.STRING_TYPE, null).stringValue
                                .toUpperCase();
                            var h5Tag = document.createElement('h5');
                            h5Tag.innerText = title;
                            item.appendChild(h5Tag);

                            detailLink.appendChild(item);

                            wrapper.appendChild(detailLink);

                            similarContent.appendChild(wrapper);

                            movie = movies.iterateNext();
                        }
                    }
                };

                request.send();
            }
        </script>
    </body>
</html>