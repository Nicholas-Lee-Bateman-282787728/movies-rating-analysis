function Pagination(currentPage, type) {
    this.currentPage = currentPage;
    this.type = type;

    this.paginationId = null;

    this.createPagination = function (paginationId) {
        this.paginationId = paginationId;
        var pageUl = document.getElementById(paginationId);
        pageUl.innerHTML = "";

        if (totalPages === 1) {
            var pageLi = this.createPage(1);
            pageUl.appendChild(pageLi);
        } else {
            var pageFirst = this.createPulp(1, 'First');
            pageUl.appendChild(pageFirst);

            if (totalPages <= 5) {
                for (var i = 1; i <= totalPages; i++) {
                    pageLi = this.createPage(i);
                    if (i === this.currentPage) {
                        pageLi.classList.add("active");
                    }
                    pageUl.appendChild(pageLi);
                }
            } else {
                var lastPageOffset = totalPages - this.currentPage;

                if (lastPageOffset < 5) {
                    pageLi = this.createPulp(totalPages - 5, '...');
                    pageUl.appendChild(pageLi);
                    for (i = totalPages - 4; i <= totalPages; i++) {
                        pageLi = this.createPage(i);
                        if (i === this.currentPage) {
                            pageLi.classList.add("active");
                        }
                        pageUl.appendChild(pageLi);
                    }
                } else {
                    var startPoint = this.currentPage % 5 === 0 ?
                        this.currentPage - 4 :
                        this.currentPage - this.currentPage % 5 + 1;
                    if (startPoint > 5) {
                        pageLi = this.createPulp(startPoint - 1, '...');
                        pageUl.appendChild(pageLi);
                    }
                    for (i = startPoint; i < startPoint + 5; i++) {
                        pageLi = this.createPage(i);
                        if (i === this.currentPage) {
                            pageLi.classList.add("active");
                        }
                        pageUl.appendChild(pageLi);
                    }
                    pageLi = this.createPulp(startPoint + 5, '...');
                    pageUl.appendChild(pageLi);
                }
            }

            var pageLast = this.createPulp(totalPages, 'Last');
            pageUl.appendChild(pageLast);
        }
    };

    this.createPage = function(page) {
        var pagination = this;
        var pageLi = document.createElement('li');
        pageLi.className = "page-item";

        var pageLink = document.createElement('a');
        pageLink.className = "page-link";
        pageLink.onclick = function () {
            pagination.currentPage = page;
            pagination.createPagination(pagination.paginationId);

            var movieAccessor = new MovieAccessor(pagination.type, page, 12);
            var moviePresenter = new MoviePresenter('movies', movieAccessor);

            moviePresenter.presentByPage(page, 12);
            document.getElementById("new-movies").scrollIntoView();
            return false;
        };

        pageLink.innerHTML = page;

        pageLi.appendChild(pageLink);

        return pageLi;
    };

    this.createPulp = function(page, pulp) {
        var pagination = this;
        var pageLi = document.createElement('li');
        pageLi.className = "page-item";

        var pageLink = document.createElement('a');
        pageLink.className = "page-link";
        pageLink.onclick = function () {
            pagination.currentPage = page;
            pagination.createPagination(pagination.paginationId);

            var movieAccessor = new MovieAccessor(pagination.type, page, 12);
            var moviePresenter = new MoviePresenter('movies', movieAccessor);

            moviePresenter.presentByPage(page, 12);
            document.getElementById("new-movies").scrollIntoView();
            return false;
        };
        pageLink.innerHTML = pulp;
        pageLi.appendChild(pageLink);

        return pageLi;
    };
}