package io.anhkhue.more.crawlservice.repositories;

import io.anhkhue.more.crawlservice.dto.MovieDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface MovieRepository extends JpaRepository<MovieDTO, Integer> {

    Optional<MovieDTO> findByTitle(String title);

    Collection<MovieDTO> findByDirectorAndYear(String director, String year);

    Set<MovieDTO> findByOnCinema(boolean onCinema);
}
