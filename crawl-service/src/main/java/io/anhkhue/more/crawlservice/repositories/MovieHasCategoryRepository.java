package io.anhkhue.more.crawlservice.repositories;

import io.anhkhue.more.crawlservice.dto.MovieHasCategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieHasCategoryRepository extends JpaRepository<MovieHasCategoryDTO, Integer> {

    Optional<MovieHasCategoryDTO> findByMovieIdAndCategoryName(int movieId, String categoryName);
}
