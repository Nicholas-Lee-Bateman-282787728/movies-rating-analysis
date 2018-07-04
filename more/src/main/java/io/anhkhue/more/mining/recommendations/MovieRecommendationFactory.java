package io.anhkhue.more.mining.recommendations;

import io.anhkhue.more.repositories.*;
import org.springframework.stereotype.Component;

@Component
public class MovieRecommendationFactory {

    public static final String RATING = "rating";
    public static final String INFO = "info";

    private final AccountRepository accountRepository;
    private final AccountRateMovieRepository accountRateMovieRepository;

    private final MovieHasCategoryRepository movieHasCategoryRepository;
    private final MovieRepository movieRepository;
    private  final ActorInMovieRepository actorInMovieRepository;

    public MovieRecommendationFactory(AccountRepository accountRepository,
                                      AccountRateMovieRepository accountRateMovieRepository,
                                      MovieHasCategoryRepository movieHasCategoryRepository,
                                      MovieRepository movieRepository,
                                      ActorInMovieRepository actorInMovieRepository) {
        this.accountRepository = accountRepository;
        this.accountRateMovieRepository = accountRateMovieRepository;
        this.movieHasCategoryRepository = movieHasCategoryRepository;
        this.movieRepository = movieRepository;
        this.actorInMovieRepository = actorInMovieRepository;
    }

    public MovieRecommendation getInstance(String type) {
        switch (type) {
            case RATING:
                return new MovieRecommendationByRating(accountRepository, accountRateMovieRepository);
            default:
                return new MovieRecommendationByInfo(movieHasCategoryRepository, movieRepository);
        }
    }
}
