package io.anhkhue.more.repositories;

import io.anhkhue.more.models.dto.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, String> {

    List<Link> findByMovieId(int movieId);

    List<Link> findBySourceLike(String source);
}
