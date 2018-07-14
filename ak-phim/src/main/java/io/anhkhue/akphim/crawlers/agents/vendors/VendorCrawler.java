package io.anhkhue.akphim.crawlers.agents.vendors;

import io.anhkhue.akphim.crawlers.agents.Crawler;
import io.anhkhue.akphim.crawlers.pool.CrawledPool;
import io.anhkhue.akphim.models.dto.Movie;

import java.util.Collection;
import java.util.HashSet;

import static io.anhkhue.akphim.crawlers.constants.CrawlersConstants.PageTypeConstants.COMING_LIST;
import static io.anhkhue.akphim.crawlers.constants.CrawlersConstants.PageTypeConstants.NEW_LIST;

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
