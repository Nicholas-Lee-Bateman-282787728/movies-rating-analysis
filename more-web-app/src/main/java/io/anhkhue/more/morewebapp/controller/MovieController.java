package io.anhkhue.more.morewebapp.controller;

import io.anhkhue.more.morewebapp.movie.Movies;
import io.anhkhue.more.morewebapp.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping(value = "/movies", produces = "application/xml")
    public ResponseEntity getAll() {
        Movies movies = new Movies();
        movies.setMovies(movieRepository.findAll());
        return ResponseEntity.status(HttpStatus.OK).body(movies);
    }
}
