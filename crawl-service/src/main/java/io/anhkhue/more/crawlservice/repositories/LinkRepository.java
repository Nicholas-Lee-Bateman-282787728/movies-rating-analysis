package io.anhkhue.more.crawlservice.repositories;

import io.anhkhue.more.crawlservice.dto.LinkDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface LinkRepository extends JpaRepository<LinkDTO, String> {

    Collection<LinkDTO> findByMovieId(int movieId);
}
