package io.anhkhue.more.crawlservice.dao;

import io.anhkhue.more.crawlservice.dto.ActorInMovieDTO;
import io.anhkhue.more.crawlservice.repositories.ActorInMovieRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ActorInMovieDAO {

    private final ActorInMovieRepository repository;

    public ActorInMovieDAO(ActorInMovieRepository repository) {
        this.repository = repository;
    }

    public void save(int actorId, int movieId) {
        ActorInMovieDTO actorInMovieDTO = findByActorIdAndMovieId(actorId, movieId);
        if (actorInMovieDTO == null) {
            actorInMovieDTO = ActorInMovieDTO.builder()
                                             .actorId(actorId)
                                             .movieId(movieId)
                                             .build();
            repository.save(actorInMovieDTO);
        }
    }

    private ActorInMovieDTO findByActorIdAndMovieId(int actorId, int movieId) {
        Optional<ActorInMovieDTO> actorInMovieDTO = repository.findByActorIdAndMovieId(actorId, movieId);
        return actorInMovieDTO.orElse(null);
    }
}
