package io.anhkhue.more.functions.similarity.account;

import io.anhkhue.more.functions.similarity.Similarity;
import io.anhkhue.more.models.dto.Account;
import io.anhkhue.more.models.dto.AccountRateMovie;
import io.anhkhue.more.repositories.AccountRateMovieRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EuclideanDistance implements Similarity<Account> {

    private final AccountRateMovieRepository accountRateMovieRepository;

    public EuclideanDistance(AccountRateMovieRepository accountRateMovieRepository) {
        this.accountRateMovieRepository = accountRateMovieRepository;
    }

    @Override
    public double score(Account user1, Account user2) {
        List<AccountRateMovie> ratingsByUser1 = accountRateMovieRepository.findByAccountUsername(user1.getUsername());
        List<AccountRateMovie> ratingsByUser2 = accountRateMovieRepository.findByAccountUsername(user2.getUsername());

        List<Integer> commonMovies = ratingsByUser1.stream()
                                                   .map(AccountRateMovie::getMovieId)
                                                   .filter(movieId -> {
                                                       for (AccountRateMovie accountRateMovie: ratingsByUser2) {
                                                           if (movieId == accountRateMovie.getMovieId()) {
                                                               return true;
                                                           }
                                                       }
                                                       return false;
                                                   })
                                                   .collect(Collectors.toList());

        if (commonMovies.isEmpty()) {
            return 0;
        }

        return sumOfSquares(ratingsByUser1, ratingsByUser2, commonMovies);
    }

    private double sumOfSquares(List<AccountRateMovie> ratings1,
                                List<AccountRateMovie> ratings2,
                                List<Integer> commonMovies) {
        double result = 0;

        for (Integer movieId: commonMovies) {
            double r1 = getRatingByMovieId(ratings1, movieId);
            double r2 = getRatingByMovieId(ratings2, movieId);
            result += square(r1, r2);
        }

        return 1 / (1 + result);
    }

    private double getRatingByMovieId(List<AccountRateMovie> accountRateMovies, int movieId) {
        for (AccountRateMovie accountRateMovie: accountRateMovies) {
            if (accountRateMovie.getMovieId() == movieId) {
                return accountRateMovie.getRating();
            }
        }

        return 0;
    }

    private double square(double r1, double r2) {
        return Math.pow((r1 - r2), 2);
    }
}