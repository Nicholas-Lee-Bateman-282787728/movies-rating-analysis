function Pagination(currentPage, type, searchValue) {
    this.currentPage = currentPage;
    this.type = type;
    this.searchValue = searchValue;

    this.paginationId = null;

    this.createPagination = function (paginationId) {
        this.paginationId = paginationId;
        var pageList = document.getElementById(paginationId);
        pageList.innerHTML = "";

        if (totalPages === 1) {
            var pageLink = this.createPage(1);
            pageList.appendChild(pageLink);
        } else {
            var pageFirst = this.createPulp(1, 'First');
            pageList.appendChild(pageFirst);

            if (totalPages <= 5) {
                for (var i = 1; i <= totalPages; i++) {
                    pageLink = this.createPage(i);
                    if (i === this.currentPage) {
                        pageLink.classList.add("active");
                    }
                    pageList.appendChild(pageLink);
                }
            } else {
                var lastPageOffset = totalPages - this.currentPage;

                if (lastPageOffset < 5) {
                    pageLink = this.createPulp(totalPages - 5, '...');
                    pageList.appendChild(pageLink);
                    for (i = totalPages - 4; i <= totalPages; i++) {
                        pageLink = this.createPage(i);
                        if (i === this.currentPage) {
                            pageLink.classList.add("active");
                        }
                        pageList.appendChild(pageLink);
                    }
                } else {
                    var startPoint = this.currentPage % 5 === 0 ?
                        this.currentPage - 4 :
                        this.currentPage - this.currentPage % 5 + 1;
                    if (startPoint > 5) {
                        pageLink = this.createPulp(startPoint - 1, '...');
                        pageList.appendChild(pageLink);
                    }
                    for (i = startPoint; i < startPoint + 5; i++) {
                        pageLink = this.createPage(i);
                        if (i === this.currentPage) {
                            pageLink.classList.add("active");
                        }
                        pageList.appendChild(pageLink);
                    }
                    pageLink = this.createPulp(startPoint + 5, '...');
                    pageList.appendChild(pageLink);
                }
            }

            var pageLast = this.createPulp(totalPages, 'Last');
            pageList.appendChild(pageLast);
        }
    };

    this.createPage = function (page) {
        var pagination = this;

        var pageLink = document.createElement('a');
        pageLink.style.cursor = "pointer";
        pageLink.onclick = function () {
            pagination.currentPage = page;
            pagination.createPagination(pagination.paginationId);

            var movieAccessor = new MovieAccessor(pagination.type, page, 12);
            var moviePresenter = new MoviePresenter('movies', movieAccessor, pagination.searchValue);

            moviePresenter.presentByPage(page, 12);
            document.getElementById("new-movies").scrollIntoView();
            return false;
        };

        pageLink.innerHTML = page;

        return pageLink;
    };

    this.createPulp = function (page, pulp) {
        var pagination = this;

        var pageLink = document.createElement('a');
        pageLink.style.cursor = "pointer";
        pageLink.onclick = function () {
            pagination.currentPage = page;
            pagination.createPagination(pagination.paginationId);

            var movieAccessor = new MovieAccessor(pagination.type, page, 12);
            var moviePresenter = new MoviePresenter('movies', movieAccessor, pagination.searchValue);

            moviePresenter.presentByPage(page, 12);
            document.getElementById("new-movies").scrollIntoView();
            return false;
        };
        pageLink.innerHTML = pulp;

        return pageLink;
    };
}