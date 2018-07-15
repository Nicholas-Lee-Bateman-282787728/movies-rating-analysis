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
                row.className = "panel-body";
            }

            // Wrapper div
            var wrapper = document.createElement('div');
            wrapper.className = "item-box";

            // Link to Detail
            var detailLink = document.createElement('a');
            detailLink.className = "movie";
            detailLink.href = 'http://localhost:8080/phim/';

            // Retrieve ID
            var id = accessor.xmlDoc.evaluate("*[local-name()='id']", movie, null, XPathResult.STRING_TYPE, null).stringValue;
            detailLink.href += id;

            // Retrieve poster url
            var poster = accessor.xmlDoc.evaluate("*[local-name()='poster']", movie, null, XPathResult.STRING_TYPE, null).stringValue;
            var imgTag = document.createElement('img');
            imgTag.src = poster;
            detailLink.appendChild(imgTag);

            // Retrieve movie title
            var title = accessor.xmlDoc.evaluate("*[local-name()='title']", movie, null, XPathResult.STRING_TYPE, null).stringValue;
            // .toUpperCase();
            var spanTitle = document.createElement('span');
            spanTitle.className = "item-title-en";
            spanTitle.innerText = title;
            detailLink.appendChild(spanTitle);

            wrapper.appendChild(detailLink);

            row.appendChild(wrapper);

            i = i + 1;
            // Append row to section before creating new row
            // after 4 items has been iterated
            movie = accessor.movies.iterateNext();

            if (((i + 1) % 4 === 0) || movie === null) {
                if (movie === null && (i % 4 !== 0)) {

                    for (var j = 4; j > (i % 4); j--) {
                        // Wrapper div
                        var filledUpGap = document.createElement('div');
                        filledUpGap.className = "item-box";
                        filledUpGap.style.visibility = 'hidden';

                        // Link to Detail
                        var filledUpDetailLink = document.createElement('a');
                        filledUpDetailLink.className = "movie";
                        filledUpDetailLink.href = '';

                        // Retrieve poster url
                        var filledUpImgTag = document.createElement('img');
                        filledUpDetailLink.appendChild(filledUpImgTag);

                        // Retrieve movie title
                        var filledUpSpanTitle = document.createElement('span');
                        filledUpSpanTitle.className = "item-title-en";
                        filledUpDetailLink.appendChild(filledUpSpanTitle);

                        filledUpGap.appendChild(filledUpDetailLink);
                        row.append(filledUpGap);
                    }

                }
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