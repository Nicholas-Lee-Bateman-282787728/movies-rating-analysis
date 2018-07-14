package io.anhkhue.akphim.controllers.apis;

import io.anhkhue.akphim.mining.cache.AccountRecommendationCache;
import io.anhkhue.akphim.models.dto.Account;
import io.anhkhue.akphim.models.dto.Movie;
import io.anhkhue.akphim.models.dto.Movies;
import io.anhkhue.akphim.models.mining.HighVote;
import io.anhkhue.akphim.services.MiningService;
import io.anhkhue.akphim.services.MovieService;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@Controller
public class MovieController {

    private final MovieService movieService;

    private final MiningService miningService;

    public MovieController(MovieService movieService, MiningService miningService) {
        this.movieService = movieService;
        this.miningService = miningService;
    }

    @GetMapping(value = "/movies", produces = APPLICATION_XML_VALUE)
    public ResponseEntity getAll() {
        Movies movies = new Movies();
        movies.setMovie(movieService.findAll());
        return ResponseEntity.status(OK).body(movies);
    }

    @GetMapping(value = "/movies/top/page={page}&no={number}", produces = APPLICATION_XML_VALUE)
    public ResponseEntity getTopNewMoviesByPage(@PathVariable String page,
                                                @PathVariable String number) {
        Page<Movie> moviePage = movieService.findTopNewMovies(Integer.parseInt(page),
                                                              Integer.parseInt(number));
        Movies movies = new Movies();
        movies.setMovie(moviePage.getContent());
        return ResponseEntity.status(OK).body(movies);
    }

    @GetMapping(value = "/movies/coming/page={page}&no={number}", produces = APPLICATION_XML_VALUE)
    public ResponseEntity getComingMoviesByPage(@PathVariable String page,
                                                @PathVariable String number) {
        Page<Movie> moviePage = movieService.findIsComingMovies(Integer.parseInt(page),
                                                                Integer.parseInt(number));
        Movies movies = new Movies();
        movies.setMovie(moviePage.getContent());
        return ResponseEntity.status(OK).body(movies);
    }

    @GetMapping(value = "/movies/search/page={page}&no={number}", produces = APPLICATION_XML_VALUE)
    public ResponseEntity searchMoviesByTitle(@PathVariable String page,
                                              @PathVariable String number,
                                              @RequestParam String searchValue) {
        Page<Movie> moviePage = movieService.searchMoviesByTitle(Integer.parseInt(page),
                                                                 Integer.parseInt(number),
                                                                 searchValue);
        Movies movies = new Movies();
        movies.setMovie(moviePage.getContent());
        return ResponseEntity.status(OK).body(movies);
    }

    @PostMapping(value = "/movies/{id}/rate")
    public ResponseEntity rateMovie(HttpServletRequest request,
                                    @PathVariable String id,
                                    @RequestParam String rating) {
        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("USER");

        if (currentUser == null) {
            return ResponseEntity.status(UNAUTHORIZED).body("Vui lòng đăng nhập để đánh giá phim.");
        }

        double newRating = movieService.rate(Integer.parseInt(rating),
                                             Integer.parseInt(id),
                                             currentUser.getUsername());

        return ResponseEntity.status(OK).body(newRating);
    }

    @GetMapping(value = "/movies/{id}/recommended", produces = APPLICATION_XML_VALUE)
    public ResponseEntity getSimilarMovies(@PathVariable String id) {
        List<Movie> movieList = movieService.getSimilarMoviesByMovieId(Integer.parseInt(id));

        Movies movies = Movies.builder()
                              .movie(movieList)
                              .build();

        return ResponseEntity.status(OK).body(movies);
    }

    @GetMapping(value = "/movies/{id}/high-vote", produces = APPLICATION_XML_VALUE)
    public ResponseEntity getHighVote(@PathVariable String id) {
        HighVote highVote = movieService.getHighVoteByMovieId(Integer.parseInt(id));
        if (highVote.getTotalVote() == 0) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
        return ResponseEntity.status(OK).body(highVote);
    }

    @GetMapping(value = "/movies/recommended/page={page}&no={number}", produces = APPLICATION_XML_VALUE)
    public ResponseEntity getRecommendationMovies(HttpServletRequest request,
                                                  @PathVariable String page,
                                                  @PathVariable String number) {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("USER");
        if (account != null) {
            List<Movie> movieList = null;
            if (AccountRecommendationCache.getRecommendationMap()
                                          .containsKey(account.getUsername())) {
                movieList = AccountRecommendationCache.getRecommendationMap()
                                                      .get(account.getUsername());
            } else {
                movieList = miningService.getRecommendationForUser(account);
                AccountRecommendationCache.cache(account.getUsername(), movieList);
            }
            PagedListHolder<Movie> moviePage = movieService.getPageFromList(Integer.parseInt(page),
                                                                            Integer.parseInt(number),
                                                                            movieList);
            Movies movies = Movies.builder()
                                  .movie(moviePage.getPageList())
                                  .build();
            return ResponseEntity.status(OK).body(movies);
        }

        return ResponseEntity.status(NOT_FOUND).build();
    }
}
