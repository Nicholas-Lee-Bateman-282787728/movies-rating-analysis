package io.anhkhue.more.repositories;

import io.anhkhue.more.models.dto.ActorInMovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActorInMovieRepository extends JpaRepository<ActorInMovie, Integer> {

    Optional<ActorInMovie> findByActorIdAndMovieId(int actorId, int movieId);

    List<ActorInMovie> findByMovieId(int movieId);
}
