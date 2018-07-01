package io.anhkhue.more.services;

import io.anhkhue.more.crawlers.agents.vendors.VendorCrawler;
import io.anhkhue.more.functions.Similarity;
import io.anhkhue.more.models.dto.*;
import io.anhkhue.more.models.mining.HighVote;
import io.anhkhue.more.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Service
public class MovieService {

    private static final int NOT_EXISTED = -1;
    static final int UNABLE_TO_SAVE = -2;

    private final Similarity<Movie> movieSimilarity;

    private final MovieRepository movieRepository;
    private final ActorInMovieRepository actorInMovieRepository;
    private final ActorRepository actorRepository;
    private final MovieHasCategoryRepository movieHasCategoryRepository;
    private final LinkRepository linkRepository;
    private final AccountRateMovieRepository accountRateMovieRepository;

    public MovieService(Similarity<Movie> movieSimilarity,
                        MovieRepository movieRepository,
                        ActorInMovieRepository actorInMovieRepository,
                        ActorRepository actorRepository,
                        MovieHasCategoryRepository movieHasCategoryRepository,
                        LinkRepository linkRepository,
                        AccountRateMovieRepository accountRateMovieRepository) {
        this.movieSimilarity = movieSimilarity;
        this.movieRepository = movieRepository;
        this.actorInMovieRepository = actorInMovieRepository;
        this.actorRepository = actorRepository;
        this.movieHasCategoryRepository = movieHasCategoryRepository;
        this.linkRepository = linkRepository;
        this.accountRateMovieRepository = accountRateMovieRepository;
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie findById(int id) {
        Movie movie = movieRepository.findById(id)
                                     .orElse(null);
        if (movie != null) {
            // Actors
            List<Integer> actorIds = actorInMovieRepository.findByMovieId(movie.getId())
                                                           .stream()
                                                           .map(ActorInMovie::getActorId)
                                                           .collect(Collectors.toList());

            List<Actor> actorList = actorIds.stream()
                                            .map(actorRepository::findById)
                                            .map(Optional::get)
                                            .collect(Collectors.toList());

            Actors actors = new Actors();
            actors.getActor().addAll(actorList.stream()
                                              .map(Actor::getFullName)
                                              .collect(Collectors.toList()));
            movie.setActors(actors);

            // Categories
            List<Category> categoryList = movieHasCategoryRepository.findByMovieId(movie.getId())
                                                                    .stream()
                                                                    .map(MovieHasCategory::getCategoryName)
                                                                    .map(Category::new)
                                                                    .collect(Collectors.toList());
            Categories categories = new Categories();
            categories.getCategory().addAll(categoryList);
            movie.setCategories(categories);

            // Links
            List<Link> linkList = linkRepository.findByMovieId(movie.getId());
            Links links = new Links();
            links.getLink().addAll(linkList);
            movie.setLinks(links);
        }
        return movie;
    }

    public Page<Movie> findTopNewMovies(int page, int moviePerPage) {
        int actualPage = page - 1;
        Pageable pageable = PageRequest.of(actualPage, moviePerPage,
                                           new Sort(DESC, "year", "importedDate"));
        return movieRepository.findAll(pageable);
    }

    public Page<Movie> findIsComingMovies(int page, int moviePerPage) {
        int actualPage = page - 1;
        Pageable pageable = PageRequest.of(actualPage, moviePerPage,
                                           new Sort(ASC, "year"));

        return movieRepository.findByIsComing(pageable, Boolean.TRUE);
    }

    public Integer save(Movie movie) {
        int result = UNABLE_TO_SAVE;
        try {
            int existedId = existed(movie);
            if (existedId == NOT_EXISTED) {
                movieRepository.save(movie);
                movieRepository.flush();
                result = movie.getId();
            } else {
                result = existedId;
            }
        } catch (Exception e) {
            log.info(this.getClass().getName() + "_" + e.getClass().getName() + ": " + e.getMessage());
        }

        return result;
    }

    public void visit(int movieId) {
        Movie movie = findById(movieId);
        if (movie != null) {
            int view = movie.getView() + 1;
            movie.setView(view);
            movieRepository.save(movie);
        }
    }

    @Transactional
    public double rate(int rating, int movieId, String username) {
        AccountRateMovie accountRateMovie = accountRateMovieRepository
                .findByAccountUsernameAndMovieId(username, movieId);

        if (accountRateMovie == null) {
            accountRateMovie = AccountRateMovie.builder()
                                               .accountUsername(username)
                                               .movieId(movieId)
                                               .rating(rating)
                                               .timestamp(System.currentTimeMillis())
                                               .build();
        } else {
            accountRateMovie.setRating(rating);
        }
        accountRateMovieRepository.save(accountRateMovie);

        double totalRating = accountRateMovieRepository.findByMovieId(movieId).stream()
                                                       .mapToDouble(AccountRateMovie::getRating)
                                                       .average()
                                                       .orElse(0.0);

        Optional<Movie> movie = movieRepository.findById(movieId);
        movie.ifPresent(m -> {
            m.setRating(totalRating);
            movieRepository.save(m);
        });

        return totalRating;
    }

    public HighVote getHighVoteByMovieId(int movieId) {
        List<AccountRateMovie> ratings = accountRateMovieRepository.findByMovieId(movieId);

        HighVote highVote = HighVote.builder()
                                    .totalVote(ratings.size())
                                    .build();

        long highRate = ratings.stream()
                               .map(AccountRateMovie::getRating)
                               .filter(rating -> rating > 3)
                               .count();

        highVote.setPercentage(((double) highRate / ratings.size()) * 100);
        return highVote;
    }

    private int existed(Movie newMovie) {
        AtomicInteger result = new AtomicInteger(NOT_EXISTED);
        Collection<Movie> moviesByDirectorAndYear = movieRepository.findByDirectorAndYear(newMovie.getDirector(),
                                                                                          newMovie.getYear());

        moviesByDirectorAndYear.forEach(movie -> {
            if (movieSimilarity.score(newMovie, movie) > 45) {
                movie.setOnCinema(newMovie.isOnCinema());
                movie.setComing(newMovie.isComing());
                result.set(movie.getId());
            }
        });

        movieRepository.saveAll(moviesByDirectorAndYear);
        return result.get();
    }

    void updateOldVendorMovies() {
        updateOldComingMovies();
        updateOldShowingMovies();
    }

    private void updateOldShowingMovies() {
        Set<Movie> oldMovies = movieRepository.findByOnCinemaAndIsComing(true, false);

        Set<Movie> moviesToUpdate = oldMovies
                .stream()
                .filter(movie -> !VendorCrawler.showingMovies.contains(movie))
                .collect(Collectors.toSet());

        moviesToUpdate.forEach(movie -> movie.setComing(false));
        movieRepository.saveAll(moviesToUpdate);
    }

    private void updateOldComingMovies() {
        Set<Movie> oldMovies = movieRepository.findByOnCinemaAndIsComing(true, true);

        Set<Movie> moviesToUpdate = oldMovies
                .stream()
                .filter(movie -> !VendorCrawler.comingMovies.contains(movie))
                .collect(Collectors.toSet());

        moviesToUpdate.forEach(movie -> movie.setComing(false));
        movieRepository.saveAll(moviesToUpdate);
    }
}
