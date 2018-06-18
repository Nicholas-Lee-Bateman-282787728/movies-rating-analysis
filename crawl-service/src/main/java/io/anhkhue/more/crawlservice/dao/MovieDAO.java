package io.anhkhue.more.crawlservice.dao;

import io.anhkhue.more.crawlservice.crawlers.pool.CrawledPool;
import io.anhkhue.more.crawlservice.crawlers.vendors.VendorCrawler;
import io.anhkhue.more.crawlservice.dto.MovieDTO;
import io.anhkhue.more.crawlservice.functions.MovieSimilarity;
import io.anhkhue.more.crawlservice.repositories.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MovieDAO {

    private static final int NOT_EXISTED = -1;
    public static final int UNABLE_TO_SAVE = -2;

    private final MovieRepository repository;

    private final MovieSimilarity movieSimilarity = MovieSimilarity.getInstance();

    public MovieDAO(MovieRepository repository) {
        this.repository = repository;
    }

    public Integer save(MovieDTO movieDto) {
        int result = UNABLE_TO_SAVE;
        try {
            int existedId = existed(movieDto);
            if (existedId == NOT_EXISTED) {
                movieDto.setImportedDate(System.currentTimeMillis());
                repository.save(movieDto);

                repository.flush();
                result = movieDto.getId();
            } else {
                result = existedId;
            }
        } catch (Exception e) {
            log.info(this.getClass().getName() + "_" + e.getClass().getName() + ": " + e.getMessage());
        }

        return result;
    }

    public void savePool() {
        updateOldVendorMovies();

        for (MovieDTO movieDto: CrawledPool.crawledPool) {
            if (existed(movieDto) != NOT_EXISTED) {
                movieDto.setImportedDate(System.currentTimeMillis());
                repository.save(movieDto);
            }
        }

        CrawledPool.clearPool();
    }

    private int existed(MovieDTO newDto) {
        AtomicInteger result = new AtomicInteger(NOT_EXISTED);
        Collection<MovieDTO> moviesByDirectorAndYear = repository.findByDirectorAndYear(newDto.getDirector(),
                                                                                        newDto.getYear());

        moviesByDirectorAndYear.forEach(movieDto -> {
            if (movieSimilarity.score(newDto, movieDto) > 45) {
                movieDto.setOnCinema(newDto.isOnCinema());
                movieDto.setComing(newDto.isComing());
                result.set(movieDto.getId());
            }
        });

        repository.saveAll(moviesByDirectorAndYear);
        return result.get();
    }

    public void updateOldVendorMovies() {
        // Update old showing movies
        Set<MovieDTO> oldShowingMovies = repository.findByOnCinema(true);

        Set<MovieDTO> moviesToUpdate = oldShowingMovies
                .stream()
                .filter(movieDto -> !VendorCrawler.showingMovies.contains(movieDto))
                .collect(Collectors.toSet());

        moviesToUpdate.forEach(movieDto -> movieDto.setOnCinema(false));
        repository.saveAll(moviesToUpdate);
    }
}
