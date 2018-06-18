package io.anhkhue.more.morewebapp.repository;

import io.anhkhue.more.morewebapp.model.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

    @Override
    List<Movie> findAll();
}
