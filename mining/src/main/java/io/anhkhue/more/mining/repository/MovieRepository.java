package io.anhkhue.more.mining.repository;

import io.anhkhue.more.mining.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findAll();

    Optional<Movie> findById(int id);

    Collection<Movie> findByDirectorAndYear(String director, BigInteger year);

    Set<Movie> findByOnCinema(boolean onCinema);

    Set<Movie> findByOnCinemaAndIsComing(boolean onCinema, boolean isComing);

    Page<Movie> findAll(Pageable pageable);

    Page<Movie> findByIsComing(Pageable pageable, boolean isComing);

    List<Movie> findByDirectorLikeAndIdNot(String director, int id);
}
