package io.anhkhue.more.services;

import io.anhkhue.more.functions.similarity.Similarity;
import io.anhkhue.more.functions.similarity.account.EuclideanDistance;
import io.anhkhue.more.functions.similarity.movie.MovieSimilarity;
import io.anhkhue.more.mining.recommendations.MovieRecommendation;
import io.anhkhue.more.mining.recommendations.MovieRecommendationFactory;
import io.anhkhue.more.models.dto.Account;
import io.anhkhue.more.models.dto.Movie;
import io.anhkhue.more.models.mining.MovieRecommendationRanking;
import io.anhkhue.more.repositories.AccountRepository;
import io.anhkhue.more.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MiningService {

    private final MovieRecommendationFactory recommendationFactory;

    private final EuclideanDistance euclideanDistance;

    private final MovieRepository movieRepository;
    private final AccountRepository accountRepository;

    private final MovieSimilarity movieSimilarity;

    public MiningService(MovieRecommendationFactory recommendationFactory,
                         EuclideanDistance euclideanDistance,
                         MovieRepository movieRepository,
                         AccountRepository accountRepository, MovieSimilarity movieSimilarity) {
        this.recommendationFactory = recommendationFactory;
        this.euclideanDistance = euclideanDistance;
        this.movieRepository = movieRepository;
        this.accountRepository = accountRepository;
        this.movieSimilarity = movieSimilarity;
    }

    public List<Movie> getRecommendationForUser(Account user) {
        List<Movie> movies = new ArrayList<>();

        movies.addAll(getRecommendationForUserByRating(user));

        return movies;
    }

    @SuppressWarnings("unchecked")
    public List<Movie> getSimilarMovies(Movie movie) {
        MovieRecommendationRanking ranking = new MovieRecommendationRanking();

        MovieRecommendation<Movie, Similarity<Movie>> recommendation =
                recommendationFactory.getInstance(MovieRecommendationFactory.INFO);
        Map<Integer, Double> miningRanking = recommendation.recommend(movie, movieSimilarity);

        ranking.setRankings(miningRanking);
        ranking.sort();

        List<Movie> movies = ranking.getRankings().entrySet().stream()
                                    .map(Map.Entry::getKey)
                                    .limit(4)
                                    .map(movieId -> movieRepository.findById(movieId).orElse(null))
                                    .collect(Collectors.toList());

        return movies;
    }

    @SuppressWarnings("unchecked")
    private List<Movie> getRecommendationForUserByRating(Account account) {
        List<Movie> movies = new ArrayList<>();

        MovieRecommendationRanking ranking = new MovieRecommendationRanking();

        MovieRecommendation<Account, Similarity<Account>> recommendation =
                recommendationFactory.getInstance(MovieRecommendationFactory.RATING);
        Map<Integer, Double> miningRanking = recommendation.recommend(account, euclideanDistance);

        ranking.setRankings(miningRanking);
        ranking.sort();

        ranking.getRankings().forEach((k, v) -> movieRepository.findById(k).ifPresent(movies::add));

        return movies;
    }


}
