package io.anhkhue.akphim.services;

import io.anhkhue.akphim.models.dto.Actor;
import io.anhkhue.akphim.repositories.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActorService {

    private final ActorRepository repository;

    public ActorService(ActorRepository repository) {
        this.repository = repository;
    }

    public int save(Actor actor) {
        Actor foundActor = findByFullName(actor.getFullName());
        if (foundActor == null) {
            repository.save(actor);
            repository.flush();
            return actor.getId();
        }

        return foundActor.getId();
    }

    private Actor findByFullName(String fullName) {
        Optional<Actor> actorDTO = repository.findByFullName(fullName);
        return actorDTO.orElse(null);
    }
}
