package io.anhkhue.more.crawlservice.crawlers.websites;

import io.anhkhue.more.crawlservice.crawlers.Crawler;
import io.anhkhue.more.crawlservice.crawlers.pool.CrawledPool;
import io.anhkhue.more.crawlservice.dto.MovieDTO;

import java.util.Collection;
import java.util.HashSet;

import static io.anhkhue.more.crawlservice.crawlers.CrawlerConstants.PageTypeConstants.NEW_LIST;
import static io.anhkhue.more.crawlservice.crawlers.CrawlerConstants.PageTypeConstants.PAGINATION_LIST;

public abstract class WebsiteCrawler implements Crawler {

    public static Collection<MovieDTO> movies = new HashSet<>();

    @Override
    public void crawlMovieCollection() {
//        crawlNewMovies();
//        crawlMoviesWithPagination();
    }

    private void crawlNewMovies() {
        crawlMoviesFromWebsite(NEW_LIST, movies);
    }

    @Override
    public void addToPool() {
        CrawledPool.addAll(movies);
    }

    private void crawlMoviesWithPagination() {
        crawlMoviesFromWebsite(PAGINATION_LIST, movies);
    }

    abstract void crawlMoviesFromWebsite(int type, Collection<MovieDTO> movieSet);
}
