function generatePagination(currentPage) {
    var pageUl = document.getElementById('pagination');
    pageUl.innerHTML = "";

    if (totalPages === 1) {
        var pageLi = createPageLi(1);
        pageUl.appendChild(pageLi);
    } else {
        var pageFirst = createPagePulp(1, 'First');
        pageUl.appendChild(pageFirst);

        if (totalPages <= 5) {
            for (var i = 1; i <= totalPages; i++) {
                pageLi = createPageLi(i);
                if (i === currentPage) {
                    pageLi.classList.add("active");
                }
                pageUl.appendChild(pageLi);
            }
        } else {
            var lastPageOffset = totalPages - currentPage;

            if (lastPageOffset < 5) {
                pageLi = createPagePulp(totalPages - 5, '...');
                pageUl.appendChild(pageLi);
                for (i = totalPages - 4; i <= totalPages; i++) {
                    pageLi = createPageLi(i);
                    if (i === currentPage) {
                        pageLi.classList.add("active");
                    }
                    pageUl.appendChild(pageLi);
                }
            } else {
                var startPoint = currentPage % 5 === 0 ?
                    currentPage - 4 :
                    currentPage - currentPage % 5 + 1;
                if (startPoint > 5) {
                    pageLi = createPagePulp(startPoint - 1, '...');
                    pageUl.appendChild(pageLi);
                }
                for (i = startPoint; i < startPoint + 5; i++) {
                    pageLi = createPageLi(i);
                    if (i === currentPage) {
                        pageLi.classList.add("active");
                    }
                    pageUl.appendChild(pageLi);
                }
                pageLi = createPagePulp(startPoint + 5, '...');
                pageUl.appendChild(pageLi);
            }
        }

        var pageLast = createPagePulp(totalPages, 'Last');
        pageUl.appendChild(pageLast);
    }
}

function createPageLi(page) {
    var pageLi = document.createElement('li');
    pageLi.className = "page-item";

    var pageLink = document.createElement('a');
    pageLink.className = "page-link";
    pageLink.onclick = function () {
        currentPage = page;
        generatePagination(page);
        getMoviesByPage(page);
        document.getElementById("new-movies").scrollIntoView();
        return false;
    };

    pageLink.innerHTML = page;

    pageLi.appendChild(pageLink);

    return pageLi;
}

function createPagePulp(page, pulp) {
    var pageLi = document.createElement('li');
    pageLi.className = "page-item";

    var pageLink = document.createElement('a');
    pageLink.className = "page-link";
    pageLink.onclick = function () {
        currentPage = page;
        generatePagination(page);
        getMoviesByPage(page);
        document.getElementById("new-movies").scrollIntoView();
        return false;
    };

    pageLink.innerHTML = pulp;

    pageLi.appendChild(pageLink);

    return pageLi;
}