package io.anhkhue.more.mining.recommendations;

import io.anhkhue.more.functions.similarity.Similarity;
import io.anhkhue.more.models.dto.Account;
import io.anhkhue.more.models.dto.AccountRateMovie;
import io.anhkhue.more.repositories.AccountRateMovieRepository;
import io.anhkhue.more.repositories.AccountRepository;

import java.util.*;
import java.util.stream.Collectors;

public class MovieRecommendationByRating implements MovieRecommendation<Account, Similarity<Account>> {

    private final AccountRepository accountRepository;
    private final AccountRateMovieRepository accountRateMovieRepository;

    MovieRecommendationByRating(AccountRepository accountRepository,
                                AccountRateMovieRepository accountRateMovieRepository) {
        this.accountRepository = accountRepository;
        this.accountRateMovieRepository = accountRateMovieRepository;
    }

    @Override
    public Map<Integer, Double> recommend(Account user, Similarity<Account> similarity) {
        Map<Integer, Double> rankings = new HashMap<>();

        Map<String, Double> topMatches = topMatches(user, similarity, 5);

        List<Account> others = topMatches.entrySet().stream()
                                         .map(entry -> accountRepository.findByUsername(entry.getKey())
                                                                        .orElse(null))
                                         .collect(Collectors.toList());

        Map<Integer, Double> totals = new HashMap<>();
        Map<Integer, Double> simSums = new HashMap<>();

        for (Account other: others) {
            List<AccountRateMovie> otherRatings = accountRateMovieRepository.findByAccountUsername(other.getUsername());
            otherRatings.forEach(otherRating -> {
                AccountRateMovie userRateMovie = accountRateMovieRepository
                        .findByAccountUsernameAndMovieId(user.getUsername(),
                                                         otherRating.getMovieId());
                if (userRateMovie == null) {
                    // Similarity * Rating
                    totals.putIfAbsent(otherRating.getMovieId(), 0.0);
                    double currentScore = totals.get(otherRating.getMovieId());
                    double score = totals.get(otherRating.getMovieId()) + otherRating.getRating() * topMatches.get(other.getUsername());
                    totals.put(otherRating.getMovieId(), currentScore + score);
                    // Sum of similarities
                    simSums.putIfAbsent(otherRating.getMovieId(), 0.0);
                    double currentSimSum = simSums.get(otherRating.getMovieId());
                    double sim = topMatches.get(otherRating.getAccountUsername());
                    simSums.put(otherRating.getMovieId(), currentSimSum + sim);
                }
            });
        }

        for (Map.Entry<Integer, Double> entry: totals.entrySet()) {
            int movieId = entry.getKey();
            double total = entry.getValue();
            double simSum = simSums.get(movieId);

            rankings.put(entry.getKey(), (total / simSum));
        }

        rankings = rankings.entrySet().stream()
                           .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                           .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                     (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return rankings;
    }

    private Map<String, Double> topMatches(Account user, Similarity<Account> similarity, long n) {
        Map<String, Double> scores = new HashMap<>();

        List<Account> others = accountRepository.findByUsernameNot(user.getUsername());

        for (Account account: others) {
            scores.put(account.getUsername(), similarity.score(user, account));
        }

        scores = scores.entrySet().stream()
                       .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                       .limit(n)
                       .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                 (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return scores;
    }
}
