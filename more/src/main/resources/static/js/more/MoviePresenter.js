function MoviePresenter(presentAreaId, accessor, searchValue) {
    this.presentArea = document.getElementById(presentAreaId);
    this.searchValue = searchValue;

    this.present = function () {
        this.presentArea.innerHTML = '';
        var movie = accessor.movies.iterateNext();

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
            var id = accessor.xmlDoc.evaluate("*[local-name()='id']", movie, null, XPathResult.STRING_TYPE, null).stringValue;
            detailLink.href += id;

            // Retrieve poster url
            var poster = accessor.xmlDoc.evaluate("*[local-name()='poster']", movie, null, XPathResult.STRING_TYPE, null).stringValue;
            var imgTag = document.createElement('img');
            imgTag.className = "img-thumbnail mb-3";
            imgTag.src = poster;
            item.appendChild(imgTag);

            // Retrieve movie title
            var title = accessor.xmlDoc.evaluate("*[local-name()='title']", movie, null, XPathResult.STRING_TYPE, null).stringValue
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
            movie = accessor.movies.iterateNext();

            if (((i + 1) % 4 === 0) || movie === null) {
                row.appendChild(document.createElement('br'));
                row.appendChild(document.createElement('br'));
                this.presentArea.appendChild(row);
            }
        }
    };

    this.presentByPage = function (page, moviePerPage) {
        if ((moviePerPage * page) <= 120) {
            accessor.getFromStorage();
            this.present();
        } else {
            accessor.getFromServer(this, searchValue);
        }
    };
}