package io.anhkhue.akphim.models.dao;

import io.anhkhue.akphim.models.dto.ActorInMovie;
import io.anhkhue.akphim.repositories.ActorInMovieRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ActorInMovieDAO {

    private final ActorInMovieRepository repository;

    public ActorInMovieDAO(ActorInMovieRepository repository) {
        this.repository = repository;
    }

    public void save(int actorId, int movieId) {
        ActorInMovie actorInMovie = findByActorIdAndMovieId(actorId, movieId);
        if (actorInMovie == null) {
            actorInMovie = ActorInMovie.builder()
                                       .actorId(actorId)
                                       .movieId(movieId)
                                       .build();
            repository.save(actorInMovie);
        }
    }

    private ActorInMovie findByActorIdAndMovieId(int actorId, int movieId) {
        Optional<ActorInMovie> actorInMovieDTO = repository.findByActorIdAndMovieId(actorId, movieId);
        return actorInMovieDTO.orElse(null);
    }
}
