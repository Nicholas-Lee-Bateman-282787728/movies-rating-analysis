package io.anhkhue.more.crawlservice.dao;

import io.anhkhue.more.crawlservice.dto.MovieHasCategoryDTO;
import io.anhkhue.more.crawlservice.repositories.MovieHasCategoryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MovieHasCategoryDAO {

    private final MovieHasCategoryRepository repository;

    public MovieHasCategoryDAO(MovieHasCategoryRepository repository) {
        this.repository = repository;
    }

    public void save(int movieId, String categoryName) {
        MovieHasCategoryDTO movieHasCategoryDTO = findByMovieIdAndCategoryName(movieId, categoryName);
        if (movieHasCategoryDTO == null) {
            movieHasCategoryDTO = MovieHasCategoryDTO.builder()
                                                     .movieId(movieId)
                                                     .categoryName(categoryName)
                                                     .build();
            repository.save(movieHasCategoryDTO);
        }
    }

    private MovieHasCategoryDTO findByMovieIdAndCategoryName(int movieId, String categoryName) {
        Optional<MovieHasCategoryDTO> movieHasCategoryDTO = repository.findByMovieIdAndCategoryName(movieId, categoryName);
        return movieHasCategoryDTO.orElse(null);
    }
}
