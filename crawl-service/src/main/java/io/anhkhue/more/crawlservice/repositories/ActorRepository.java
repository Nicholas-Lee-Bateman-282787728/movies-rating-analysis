package io.anhkhue.more.crawlservice.repositories;

import io.anhkhue.more.crawlservice.dto.ActorDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorRepository extends JpaRepository<ActorDTO, Integer> {

    Optional<ActorDTO> findByFullName(String fullName);

}
