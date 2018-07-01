function topNewMovies(total) {
    var request = new XMLHttpRequest();
    var url = '/movies/top/page=1&no=' + total;

    request.open('GET', url, true);

    request.onreadystatechange = function () {
        if (this.readyState === 4 || this.status === 200) {
            sessionStorage.setItem('topMovies', request.responseText);
        }
    };

    request.send();
}

function topComingMovies(total) {
    var request = new XMLHttpRequest();
    var url = '/movies/coming/page=1&no=' + total;

    request.open('GET', url, true);

    request.onreadystatechange = function () {
        if (this.readyState === 4 || this.status === 200) {
            sessionStorage.setItem('comingMovies', request.responseText);
        }
    };

    request.send();
}

function getMoviesByPageFromStorage(type, page, moviesPerPage) {
    var moviesFromStorage = sessionStorage.getItem(type + 'Movies');

    var parser = new DOMParser();
    var xmlString = moviesFromStorage.replace(new RegExp(' xmlns(:.*?)?=(".*?")'), '');
    var xmlDoc = parser.parseFromString(xmlString, "text/xml");

    var xPath = '//movie[position()>' + (moviesPerPage * (page - 1)) + ' and position() < ' + ((moviesPerPage * page) + 1) + ' ]';
    var movies = xmlDoc.evaluate(xPath, xmlDoc, null, XPathResult.ANY_TYPE, null);

    var movie = movies.iterateNext();
    while (movie) {
        var id = xmlDoc.evaluate('id', movie, null, XPathResult.STRING_TYPE, null).stringValue;
        console.log(id);

        var title = xmlDoc.evaluate('title', movie, null, XPathResult.STRING_TYPE, null).stringValue;
        console.log(title);

        movie = movies.iterateNext();
    }
}