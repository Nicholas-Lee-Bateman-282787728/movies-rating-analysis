package io.anhkhue.more.crawlservice.repositories;

import io.anhkhue.more.crawlservice.dto.ActorInMovieDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorInMovieRepository extends JpaRepository<ActorInMovieDTO, Integer> {

    Optional<ActorInMovieDTO> findByActorIdAndMovieId(int actorId, int movieId);
}
