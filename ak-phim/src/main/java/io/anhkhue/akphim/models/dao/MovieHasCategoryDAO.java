package io.anhkhue.akphim.models.dao;

import io.anhkhue.akphim.models.dto.MovieHasCategory;
import io.anhkhue.akphim.repositories.MovieHasCategoryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MovieHasCategoryDAO {

    private final MovieHasCategoryRepository repository;

    public MovieHasCategoryDAO(MovieHasCategoryRepository repository) {
        this.repository = repository;
    }

    public void save(int movieId, String categoryName) {
        MovieHasCategory movieHasCategoryDTO = findByMovieIdAndCategoryName(movieId, categoryName);
        if (movieHasCategoryDTO == null) {
            movieHasCategoryDTO = MovieHasCategory.builder()
                                                  .movieId(movieId)
                                                  .categoryName(categoryName)
                                                  .build();
            repository.save(movieHasCategoryDTO);
        }
    }

    private MovieHasCategory findByMovieIdAndCategoryName(int movieId, String categoryName) {
        Optional<MovieHasCategory> movieHasCategoryDTO = repository.findByMovieIdAndCategoryName(movieId, categoryName);
        return movieHasCategoryDTO.orElse(null);
    }
}
