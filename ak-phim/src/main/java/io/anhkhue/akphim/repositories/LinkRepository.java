package io.anhkhue.akphim.repositories;

import io.anhkhue.akphim.models.dto.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, String> {

    List<Link> findByMovieId(int movieId);

    List<Link> findBySourceLike(String source);
}
