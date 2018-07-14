package io.anhkhue.akphim.crawlers.pool;

import io.anhkhue.akphim.crawlers.agents.vendors.VendorCrawler;
import io.anhkhue.akphim.crawlers.agents.websites.WebsiteCrawler;
import io.anhkhue.akphim.models.dto.Movie;
import io.anhkhue.akphim.similarity.movie.SameMoviePossibility;
import io.anhkhue.akphim.similarity.string.StringSimilarity;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Component
public class CrawledPool {

    private static final SameMoviePossibility SAME_MOVIE_POSSIBILITY = new SameMoviePossibility(new StringSimilarity());

    private static Collection<Movie> crawledPool = new HashSet<>();

    public static Collection<Movie> getCrawledPool() {
        return crawledPool;
    }

    public static void addAll(Collection<Movie> movies) {
        movies.forEach(movie -> {
            Optional<Movie> foundExistedMovie = existed(movie);

            if (!foundExistedMovie.isPresent()) {
                crawledPool.add(movie);
                return;
            }

            foundExistedMovie.ifPresent(existedMovie -> {
                existedMovie.getLinks()
                            .getLink()
                            .addAll(movie.getLinks()
                                         .getLink());
                if (movie.isOnCinema()) {
                    existedMovie.setOnCinema(true);
                }
                crawledPool.add(existedMovie);
            });
        });
    }

    private static Optional<Movie> existed(Movie movie) {
        for (Movie crawledMovie: crawledPool) {
            if (SAME_MOVIE_POSSIBILITY.score(movie, crawledMovie) > 45) {
                return Optional.of(crawledMovie);
            }
        }
        return Optional.empty();
    }

    public static void clearPool() {
        crawledPool = new HashSet<>();

        // Clear vendors' movies
        VendorCrawler.showingMovies = new HashSet<>();
        VendorCrawler.comingMovies = new HashSet<>();

        // Clear websites' movies
        WebsiteCrawler.movies = new HashSet<>();
    }
}
