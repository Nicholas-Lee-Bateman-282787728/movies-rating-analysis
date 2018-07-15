package io.anhkhue.akphim.crawlers.agents.websites;

import io.anhkhue.akphim.crawlers.agents.Crawler;
import io.anhkhue.akphim.crawlers.pool.CrawledPool;
import io.anhkhue.akphim.models.dto.Movie;

import java.util.Collection;
import java.util.HashSet;

import static io.anhkhue.akphim.crawlers.constants.CrawlersConstants.PageTypeConstants.NEW_LIST;
import static io.anhkhue.akphim.crawlers.constants.CrawlersConstants.PageTypeConstants.PAGINATION_LIST;

public abstract class WebsiteCrawler implements Crawler {

    public static Collection<Movie> movies = new HashSet<>();

    @Override
    public void crawlMovieCollection() {
        crawlNewMovies();
        crawlMoviesWithPagination();
    }

    private void crawlNewMovies() {
        crawlMoviesFromWebsite(NEW_LIST);
    }

    @Override
    public void addToPool() {
        CrawledPool.addAll(movies);
    }

    private void crawlMoviesWithPagination() {
        crawlMoviesFromWebsite(PAGINATION_LIST);
    }

    abstract void crawlMoviesFromWebsite(int type);
}
