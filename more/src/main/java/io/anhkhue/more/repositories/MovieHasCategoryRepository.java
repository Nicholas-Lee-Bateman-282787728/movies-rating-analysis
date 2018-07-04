package io.anhkhue.more.repositories;

import io.anhkhue.more.models.dto.MovieHasCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieHasCategoryRepository extends JpaRepository<MovieHasCategory, Integer> {

    Optional<MovieHasCategory> findByMovieIdAndCategoryName(int movieId, String categoryName);

    List<MovieHasCategory> findByMovieId(int movieId);

    List<MovieHasCategory> findByCategoryNameAndMovieIdNot(String categoryName, int movieId);

    @Query(value = "SELECT mhc1.* FROM movie_has_category mhc1 INNER JOIN (SELECT * FROM movie_has_category WHERE movie_id = :movieId2) mhc2 ON mhc1.category_name = mhc2.category_name WHERE mhc1.movie_id = :movieId1", nativeQuery = true)
    List<MovieHasCategory> findCommonCategories(@Param("movieId1") int movieId1,
                                                @Param("movieId2") int movieId2);

    @Query(value = "SELECT COUNT(*) FROM movie_has_category mhc1 INNER JOIN (SELECT * FROM movie_has_category WHERE movie_id = :movieId2) mhc2 ON mhc1.category_name = mhc2.category_name WHERE mhc1.movie_id = :movieId1 GROUP BY mhc1.movie_id", nativeQuery = true)
    Long countCommonCategories(@Param("movieId1") int movieId1,
                               @Param("movieId2") int movieId2);
}
