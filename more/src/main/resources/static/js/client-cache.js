function topNewMovies(total, presenter) {
    var request = new XMLHttpRequest();
    var url = '/movies/top/page=1&no=' + total;

    request.open('GET', url, true);

    request.onreadystatechange = function () {
        if (this.readyState === 4 || this.status === 200) {
            sessionStorage.setItem('topMovies', request.responseText);
            if (presenter !== null) {
                presenter.presentByPage(1, 12);
            }
        }
    };

    request.send();
}

function topComingMovies(total, presenter) {
    var request = new XMLHttpRequest();
    var url = '/movies/coming/page=1&no=' + total;

    request.open('GET', url, true);

    request.onreadystatechange = function () {
        if (this.readyState === 4 || this.status === 200) {
            sessionStorage.setItem('comingMovies', request.responseText);
            if (presenter !== null) {
                presenter.presentByPage(1, 12);
            }
        }
    };

    request.send();
}
