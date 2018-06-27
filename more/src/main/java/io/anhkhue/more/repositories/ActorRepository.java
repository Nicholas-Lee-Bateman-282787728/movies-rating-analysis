package io.anhkhue.more.repositories;

import io.anhkhue.more.models.dto.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Integer> {

    Optional<Actor> findByFullName(String fullName);

    Optional<Actor> findById(int id);

}
