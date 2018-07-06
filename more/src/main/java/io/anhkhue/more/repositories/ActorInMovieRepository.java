package io.anhkhue.more.repositories;

import io.anhkhue.more.models.dto.ActorInMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActorInMovieRepository extends JpaRepository<ActorInMovie, Integer> {

    Optional<ActorInMovie> findByActorIdAndMovieId(int actorId, int movieId);

    List<ActorInMovie> findByMovieId(int movieId);

    List<ActorInMovie> findByActorIdAndMovieIdNot(int actorId, int movieId);

    @Query(value = "SELECT aim1.* FROM actor_in_movie aim1 INNER JOIN (SELECT * FROM actor_in_movie WHERE movie_id = :movieId2) aim2 ON aim1.actor_id = aim2.actor_id WHERE aim1.movie_id = :movieId1", nativeQuery = true)
    List<ActorInMovie> findCommonActors(@Param("movieId1") int movieId1,
                                        @Param("movieId2") int movieId2);

    @Query(value = "SELECT COUNT(*) FROM actor_in_movie aim1 INNER JOIN (SELECT * FROM actor_in_movie WHERE movie_id = :movieId2) aim2 ON aim1.actor_id = aim2.actor_id WHERE aim1.movie_id = :movieId1 GROUP BY aim1.movie_id", nativeQuery = true)
    Long countCommonActors(@Param("movieId1") int movieId1,
                           @Param("movieId2") int movieId2);
}
