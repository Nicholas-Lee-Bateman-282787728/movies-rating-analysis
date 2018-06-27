package io.anhkhue.more.crawlers.agents.vendors;

import io.anhkhue.more.crawlers.agents.Crawler;
import io.anhkhue.more.crawlers.pool.CrawledPool;
import io.anhkhue.more.models.dto.Movie;

import java.util.Collection;
import java.util.HashSet;

import static io.anhkhue.more.crawlers.constants.CrawlersConstants.PageTypeConstants.COMING_LIST;
import static io.anhkhue.more.crawlers.constants.CrawlersConstants.PageTypeConstants.NEW_LIST;

public abstract class VendorCrawler implements Crawler {

    public static Collection<Movie> showingMovies = new HashSet<>();
    public static Collection<Movie> comingMovies = new HashSet<>();

    @Override
    public void crawlMovieCollection() {
        crawlShowingMovies();
        crawlComingMovies();
    }

    private void crawlShowingMovies() {
        crawlMoviesFromVendor(NEW_LIST, comingMovies);
    }

    private void crawlComingMovies() {
        crawlMoviesFromVendor(COMING_LIST, showingMovies);
    }

    @Override
    public void addToPool() {
        CrawledPool.addAll(showingMovies);
        CrawledPool.addAll(comingMovies);
    }

    abstract void crawlMoviesFromVendor(int type, Collection<Movie> movieSet);
}
