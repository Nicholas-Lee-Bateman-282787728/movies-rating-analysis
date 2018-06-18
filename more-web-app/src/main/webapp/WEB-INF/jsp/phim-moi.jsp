<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Phim mới</title>

        <!-- Bootstrap core CSS -->
        <link href="/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom fonts for this template -->
        <link href="/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="/vendor/simple-line-icons/css/simple-line-icons.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic"
              rel="stylesheet"
              type="text/css">

        <!-- Custom styles for this template -->
        <link href="/css/landing-page.css" rel="stylesheet">
        <link href="/css/phim-moi.css" rel="stylesheet">

    </head>

    <body>

        <!-- Navigation -->
        <nav class="navbar navbar-light bg-light static-top">
            <div class="container">
                <a class="navbar-brand" href="index.html">
                    <img src="/img/Logo_K.svg" alt="">
                    <span class="web-name">MoRe</span>
                </a>
                <div class="d-flex flex-row-reverse">
                    <a class="btn btn-success" href="#">Sign Up</a>
                    <a class="btn text-success" href="#">Sign In</a>
                </div>
            </div>
        </nav>

        <!-- Masthead -->
        <header class="masthead text-white text-center">
            <div class="overlay"></div>
            <div class="container">
                <div class="row">
                    <div class="col-xl-9 mx-auto">
                        <h1 class="mb-5">Luôn cập nhật các tác phẩm điện ảnh mới nhất</h1>
                    </div>
                    <div class="col-md-10 col-lg-8 col-xl-7 mx-auto">
                        <form>
                            <div class="form-row">
                                <div class="col-12 col-md-9 mb-2 mb-md-0">
                                    <input type="text" class="form-control form-control-lg" placeholder="Tên phim...">
                                </div>
                                <div class="col-12 col-md-3">
                                    <button type="submit" class="btn btn-block btn-lg btn-success">Tìm kiếm</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </header>

        <!-- Testimonials -->
        <section class="testimonials text-center bg-light">
            <div class="container">
                <h2 class="mb-5">Phim mới cập nhật</h2>

                <div id="movies">

                </div>
            </div>
            <br>
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                    <li class="page-item">
                        <a class="page-link" href="#" tabindex="-1">First</a>
                    </li>
                    <li class="page-item active">
                        <a class="page-link" href="#">1</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="#">2</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="#">3</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="#">4</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="#">5</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="#">Last</a>
                    </li>
                </ul>
            </nav>
        </section>

        <!-- Footer -->
        <footer class="footer">
            <div class="container">
                <div class="row">
                    <div class="col-lg-6 h-100 text-center text-lg-left my-auto">
                        <ul class="list-inline mb-2">
                            <li class="list-inline-item">
                                <a href="#">About</a>
                            </li>
                            <li class="list-inline-item">&sdot;</li>
                            <li class="list-inline-item">
                                <a href="#">Contact</a>
                            </li>
                            <li class="list-inline-item">&sdot;</li>
                            <li class="list-inline-item">
                                <a href="#">Terms of Use</a>
                            </li>
                            <li class="list-inline-item">&sdot;</li>
                            <li class="list-inline-item">
                                <a href="#">Privacy Policy</a>
                            </li>
                        </ul>
                        <p class="text-muted small mb-4 mb-lg-0">&copy; Your Website 2018. All Rights Reserved.</p>
                    </div>
                    <div class="col-lg-6 h-100 text-center text-lg-right my-auto">
                        <ul class="list-inline mb-0">
                            <li class="list-inline-item mr-3">
                                <a href="#">
                                    <i class="fa fa-facebook fa-2x fa-fw"></i>
                                </a>
                            </li>
                            <li class="list-inline-item mr-3">
                                <a href="#">
                                    <i class="fa fa-twitter fa-2x fa-fw"></i>
                                </a>
                            </li>
                            <li class="list-inline-item">
                                <a href="#">
                                    <i class="fa fa-instagram fa-2x fa-fw"></i>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </footer>

        <script type="text/javascript">

            var request = new XMLHttpRequest();

            request.onreadystatechange = function () {
                if (this.readyState === 4 || this.status === 200) {
                    var showSection = document.getElementById("movies");
                    showSection.innerHTML = "";

                    // Create Parser
                    var parser = new DOMParser();

                    // Create DOM from XML response
                    var xmlDoc = parser.parseFromString(request.responseText, "text/xml");
                    console.log(request.responseText);

                    // Retrieve Node List of movies
                    var xpathExp = "//movie";
                    var movies = xmlDoc.evaluate(xpathExp, xmlDoc, null, XPathResult.ANY_TYPE, null);

                    var movie = movies.iterateNext();

                    var i = 0;
                    var row;
                    while (movie) {
                        // Create new row from beginning
                        // and after 4 items has been iterated
                        if (i % 4 === 0) {
                            row = document.createElement('div');
                            row.className = "row";
                        }

                        // Wrapper div
                        var wrapper = document.createElement('div');
                        wrapper.className = "col-lg-3";

                        // Main div for showing movie
                        var item = document.createElement('div');
                        item.className = "testimonial-item mx-auto mb-5 mb-lg-0";

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

                        wrapper.appendChild(item);

                        row.appendChild(wrapper);

                        i = i + 1;
                        // Append row to section before creating new row
                        // after 4 items has been iterated
                        if ((i + 1) % 4 === 0) {
                            row.appendChild(document.createElement('br'));
                            row.appendChild(document.createElement('br'));
                            showSection.appendChild(row);
                        }

                        movie = movies.iterateNext();
                    }
                }
            };

            request.open('GET', '/movies', true);
            request.send();
        </script>

    </body>

</html>
