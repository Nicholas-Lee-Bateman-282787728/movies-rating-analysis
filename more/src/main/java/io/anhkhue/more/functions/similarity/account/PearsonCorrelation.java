package io.anhkhue.more.functions.similarity.account;

import io.anhkhue.more.functions.similarity.Similarity;
import io.anhkhue.more.models.dto.Account;
import io.anhkhue.more.models.dto.AccountRateMovie;
import io.anhkhue.more.repositories.AccountRateMovieRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PearsonCorrelation implements Similarity<Account> {

    private final AccountRateMovieRepository accountRateMovieRepository;

    public PearsonCorrelation(AccountRateMovieRepository accountRateMovieRepository) {
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

        int n = commonMovies.size();

        if (n == 0) {
            return 0;
        }

        List<AccountRateMovie> filteredRatings1 = filterCommonMovies(ratingsByUser1, commonMovies);
        List<AccountRateMovie> filteredRatings2 = filterCommonMovies(ratingsByUser2, commonMovies);

        double sum1 = sumRatings(filteredRatings1);
        double sum2 = sumRatings(filteredRatings2);

        double sumSq1 = sumOfSquares(filteredRatings1);
        double sumSq2 = sumOfSquares(filteredRatings2);

        double pSum = sumProducts(filteredRatings1, filteredRatings2);

        // Calculate Pearson score
        double num = pSum - (sum1 * sum2 / n);
        double den = Math.sqrt((sumSq1 - Math.pow(sum1, 2) / n) * (sumSq2 - Math.pow(sum2, 2) / n));

        if (den == 0) {
            return 0;
        }

        return num / den;
    }

    private List<AccountRateMovie> filterCommonMovies(List<AccountRateMovie> ratings, List<Integer> commonMovies) {
        return ratings.stream()
                      .filter(accountRateMovie -> {
                          for (Integer movieId: commonMovies) {
                              if (movieId == accountRateMovie.getMovieId()) {
                                  return true;
                              }
                          }
                          return false;
                      })
                      .collect(Collectors.toList());
    }

    private double sumRatings(List<AccountRateMovie> ratings) {
        return ratings.stream()
                      .mapToInt(AccountRateMovie::getRating)
                      .sum();
    }

    private double sumOfSquares(List<AccountRateMovie> ratings) {
        return ratings.stream()
                      .mapToDouble(rating -> Math.pow((double) rating.getRating(), 2))
                      .sum();
    }

    private double sumProducts(List<AccountRateMovie> ratings1, List<AccountRateMovie> ratings2) {
        double result = 0;

        for (AccountRateMovie rating1: ratings1) {
            for (AccountRateMovie rating2: ratings2) {
                if (rating1.getMovieId() == rating2.getMovieId()) {
                    result += (rating1.getRating() * rating2.getRating());
                }
            }
        }

        return result;
    }
}
