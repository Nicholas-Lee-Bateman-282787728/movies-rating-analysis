package io.anhkhue.more.crawlservice.dao;

import io.anhkhue.more.crawlservice.dto.ActorDTO;
import io.anhkhue.more.crawlservice.repositories.ActorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ActorDAO {

    private final ActorRepository repository;

    public ActorDAO(ActorRepository repository) {
        this.repository = repository;
    }

    public int save(ActorDTO actorDTO) {
        ActorDTO foundActor = findByFullName(actorDTO.getFullName());
        if (foundActor == null) {
            repository.save(actorDTO);
            repository.flush();
            return actorDTO.getId();
        }

        return foundActor.getId();
    }

    private ActorDTO findByFullName(String fullName) {
        Optional<ActorDTO> actorDTO = repository.findByFullName(fullName);
        return actorDTO.orElse(null);
    }
}
