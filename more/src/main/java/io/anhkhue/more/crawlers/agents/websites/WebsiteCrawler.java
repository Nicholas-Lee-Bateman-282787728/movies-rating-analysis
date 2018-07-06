package io.anhkhue.more.crawlers.agents.websites;

import io.anhkhue.more.crawlers.agents.Crawler;
import io.anhkhue.more.crawlers.pool.CrawledPool;
import io.anhkhue.more.models.dto.Movie;

import java.util.Collection;
import java.util.HashSet;

import static io.anhkhue.more.crawlers.constants.CrawlersConstants.PageTypeConstants.NEW_LIST;
import static io.anhkhue.more.crawlers.constants.CrawlersConstants.PageTypeConstants.PAGINATION_LIST;

public abstract class WebsiteCrawler implements Crawler {

    public static Collection<Movie> movies = new HashSet<>();

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

    abstract void crawlMoviesFromWebsite(int type, Collection<Movie> movieSet);
}
