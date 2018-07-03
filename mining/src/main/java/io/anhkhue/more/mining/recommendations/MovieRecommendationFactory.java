package io.anhkhue.more.mining.recommendations;

import io.anhkhue.more.mining.repository.AccountRateMovieRepository;
import io.anhkhue.more.mining.repository.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class MovieRecommendationFactory {

    public static final String RATING = "rating";
    public static final String INFO = "info";

    private final AccountRepository accountRepository;
    private final AccountRateMovieRepository accountRateMovieRepository;

    public MovieRecommendationFactory(AccountRepository accountRepository, AccountRateMovieRepository accountRateMovieRepository) {
        this.accountRepository = accountRepository;
        this.accountRateMovieRepository = accountRateMovieRepository;
    }

    public MovieRecommendation getInstance(String type) {
        switch (type) {
            case RATING:
                return new MovieRecommendationByRating(accountRepository, accountRateMovieRepository);
            default:
                return new MovieRecommendationByInfo(movieHasCategoryRepository);
        }
    }
}
