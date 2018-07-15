<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<!DOCTYPE html>
<html>

    <head>
        <title>Phim</title>
        <link rel="icon" href="/img/Logo_K.svg">
        <meta charset="utf-8"/>
        <meta name="author" content="Thai Ly Anh Khue"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes"/>
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <link rel="stylesheet" href="/vendor/font-awesome/css/font-awesome.min.css">

        <link rel="stylesheet" type="text/css" media="screen" href="/css/master.css"/>
        <link href="https://unpkg.com/ionicons@4.2.4/dist/css/ionicons.min.css" rel="stylesheet">

        <link rel="stylesheet" href="/css/home.css">
        <link rel="stylesheet" href="/css/main.css">
        <link rel="stylesheet" href="/css/detail.css">
    </head>

    <body>
        <!-- Header -->
        <jsp:include page="fragments/header.jsp"/>

        <script>
            var movie = '${requestScope.MOVIE}';
            // Create Parser
            var parser = new DOMParser();

            var xmlDoc = parser.parseFromString(movie, "text/xml");

            // Retrieve Node List of movies
            var movieId = xmlDoc.evaluate("//*[local-name()='id']", xmlDoc, null, XPathResult.STRING_TYPE, null).stringValue;
        </script>

        <c:set var="movie" value="${requestScope.MOVIE}"/>

        <c:import charEncoding="UTF-8" url="/xsl/movie-detail.xsl" var="xslDoc"/>
        <x:transform doc="${movie}" xslt="${xslDoc}"/>

        <!-- Footer -->
        <c:import url="fragments/footer.jsp"/>

        <script src="/js/client-cache.js" type="text/javascript"></script>

        <script type="text/javascript">
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

                console.log(star);

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
                var vote = document.getElementById('high-votes');

                var request = new XMLHttpRequest();
                var url = '/movies/' + movieId + '/high-vote';

                request.open('GET', url, true);

                request.onreadystatechange = function () {
                    if (request.readyState === 4 && request.status === 200) {
                        vote.innerHTML = '';
                        var highVote = request.responseText;
                        var parser = new DOMParser();

                        var xmlDoc = parser.parseFromString(highVote, "text/xml");

                        var percentage = xmlDoc.evaluate("//*[local-name()='percentage']", xmlDoc, null, XPathResult.STRING_TYPE, null).stringValue;
                        percentage = parseFloat(percentage).toFixed(2);
                        var totalVote = xmlDoc.evaluate("//*[local-name()='totalVote']", xmlDoc, null, XPathResult.STRING_TYPE, null).stringValue;

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
                        var parser = new DOMParser();
                        var xmlDoc = parser.parseFromString(xmlString, "text/xml");

                        var xPath = "//*[local-name()='movie']";
                        var movies = xmlDoc.evaluate(xPath, xmlDoc, null, XPathResult.ANY_TYPE, null);

                        var movie = movies.iterateNext();

                        var row = document.createElement('div');
                        row.className = "panel-body";

                        while (movie) {
                            // Wrapper div
                            var wrapper = document.createElement('div');
                            wrapper.className = "item-box";

                            // Link to Detail
                            var detailLink = document.createElement('a');
                            detailLink.className = "movie";
                            detailLink.href = 'http://localhost:8080/phim/';

                            // Retrieve ID
                            var id = xmlDoc.evaluate("*[local-name()='id']", movie, null, XPathResult.STRING_TYPE, null).stringValue;
                            detailLink.href += id;

                            // Retrieve poster url
                            var poster = xmlDoc.evaluate("*[local-name()='poster']", movie, null, XPathResult.STRING_TYPE, null).stringValue;
                            var imgTag = document.createElement('img');
                            imgTag.src = poster;
                            detailLink.appendChild(imgTag);

                            // Retrieve movie title
                            var title = xmlDoc.evaluate("*[local-name()='title']", movie, null, XPathResult.STRING_TYPE, null).stringValue;
                            // .toUpperCase();
                            var spanTitle = document.createElement('span');
                            spanTitle.className = "item-title-en";
                            spanTitle.innerText = title;
                            detailLink.appendChild(spanTitle);

                            wrapper.appendChild(detailLink);

                            row.appendChild(wrapper);

                            movie = movies.iterateNext();
                        }
                        similarContent.appendChild(row);
                    }
                };

                request.send();
            }
        </script>

    </body>

</html>