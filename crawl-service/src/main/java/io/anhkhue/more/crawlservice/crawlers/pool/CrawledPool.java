package io.anhkhue.more.crawlservice.crawlers.pool;

import io.anhkhue.more.crawlservice.crawlers.vendors.VendorCrawler;
import io.anhkhue.more.crawlservice.crawlers.websites.WebsiteCrawler;
import io.anhkhue.more.crawlservice.dto.MovieDTO;
import io.anhkhue.more.crawlservice.functions.MovieSimilarity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class CrawledPool {

    private static final MovieSimilarity movieSimilarity = MovieSimilarity.getInstance();

    public static Collection<MovieDTO> crawledPool = new HashSet<>();

    public static void addAll(Collection<MovieDTO> movies) {
        movies.forEach(movieDto -> {
            Optional<MovieDTO> foundExistedMovie = existed(movieDto);

            if (!foundExistedMovie.isPresent()) {
                crawledPool.add(movieDto);
                return;
            }

            foundExistedMovie.ifPresent(existedMovie -> {
                existedMovie.getLinks().addAll(movieDto.getLinks());
                if (movieDto.isOnCinema()) {
                    existedMovie.setOnCinema(true);
                }
                crawledPool.add(existedMovie);
            });
        });
    }

    private static Optional<MovieDTO> existed(MovieDTO movieDto) {
        for (MovieDTO crawledMovie: crawledPool) {
            if (movieSimilarity.score(movieDto, crawledMovie) > 45) {
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
