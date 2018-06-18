package io.anhkhue.more.crawlservice.crawlers.vendors;

import io.anhkhue.more.crawlservice.crawlers.Crawler;
import io.anhkhue.more.crawlservice.crawlers.pool.CrawledPool;
import io.anhkhue.more.crawlservice.dto.MovieDTO;

import java.util.Collection;
import java.util.HashSet;

import static io.anhkhue.more.crawlservice.crawlers.CrawlerConstants.PageTypeConstants.COMING_LIST;
import static io.anhkhue.more.crawlservice.crawlers.CrawlerConstants.PageTypeConstants.NEW_LIST;

public abstract class VendorCrawler implements Crawler {

    public static Collection<MovieDTO> showingMovies = new HashSet<>();
    public static Collection<MovieDTO> comingMovies = new HashSet<>();

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

    abstract void crawlMoviesFromVendor(int type, Collection<MovieDTO> movieSet);
}
