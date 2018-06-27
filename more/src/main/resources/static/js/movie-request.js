function getMovies(url) {
    var request = new XMLHttpRequest();

    request.onreadystatechange = function () {
        if (this.readyState === 4 || this.status === 200) {
            var showSection = document.getElementById("movies");
            showSection.innerHTML = "";

            // Create Parser
            var parser = new DOMParser();

            // Create DOM from XML response
            var xmlString = request.responseText;
            xmlString = xmlString.replace(new RegExp(' xmlns(:.*?)?=(".*?")'), '');
            var xmlDoc = parser.parseFromString(xmlString, "text/xml");
            // console.log(request.responseText);

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

    request.open('GET', url, true);
    request.send();
}