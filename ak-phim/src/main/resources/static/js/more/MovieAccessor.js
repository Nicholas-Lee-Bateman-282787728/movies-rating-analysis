function MovieAccessor(type, page, moviesPerPage) {
    this.type = type;
    this.page = page;
    this.moviesPerPage = moviesPerPage;

    this.movies = null;
    this.xmlDoc = null;

    this.getFromStorage = function () {
        var moviesFromStorage = sessionStorage.getItem(this.type + 'Movies');

        var parser = new DOMParser();
        // var xmlString = moviesFromStorage.replace(new RegExp(' xmlns(:.*?)?=(".*?")'), '');
        var xmlString = moviesFromStorage;
        this.xmlDoc = parser.parseFromString(xmlString, "text/xml");

        // var xPath = '//movie[position()>' + (this.moviesPerPage * (this.page - 1))
        //     + ' and position()<' + ((this.moviesPerPage * this.page) + 1) + ' ]';

        var xPath = "//*[local-name()='movie' and position()>" + (this.moviesPerPage * (this.page - 1))
            + " and position()<" + ((this.moviesPerPage * this.page) + 1) + ']';

        this.movies = this.xmlDoc.evaluate(xPath, this.xmlDoc, null, XPathResult.ANY_TYPE, null);
    };

    this.getFromServer = function (presenter, searchValue) {
        var accessor = this;

        var request = new XMLHttpRequest();
        var url = '/movies/' + this.type + '/page=' + this.page + '&no=' + this.moviesPerPage;
        var params = 'searchValue=' + searchValue;

        request.onreadystatechange = function () {

            if (this.readyState === 4 || this.status === 200) {
                presenter.presentArea.innerHTML = '';
                var xmlString = request.responseText;
                var parser = new DOMParser();
                accessor.xmlDoc = parser.parseFromString(xmlString, "text/xml");
                var xPath = "//*[local-name()='movie']";

                accessor.movies = accessor.xmlDoc.evaluate(xPath, accessor.xmlDoc, null, XPathResult.ANY_TYPE, null);
                presenter.present();
            }
        };

        if (searchValue === null) {
            request.open('GET', url, true);
        } else {
            request.open('GET', url + '?' + params, true);
        }

        request.send();
    };
}