package io.anhkhue.more.repositories;

import io.anhkhue.more.models.dto.MovieHasCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieHasCategoryRepository extends JpaRepository<MovieHasCategory, Integer> {

    Optional<MovieHasCategory> findByMovieIdAndCategoryName(int movieId, String categoryName);

    List<MovieHasCategory> findByMovieId(int movieId);
}
