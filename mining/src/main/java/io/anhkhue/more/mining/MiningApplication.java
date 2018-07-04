package io.anhkhue.more.mining;

import io.anhkhue.more.mining.function.similarity.account.EuclideanDistance;
import io.anhkhue.more.mining.function.similarity.account.PearsonCorrelation;
import io.anhkhue.more.mining.function.similarity.Similarity;
import io.anhkhue.more.mining.function.similarity.movie.MovieInfoCentric;
import io.anhkhue.more.mining.model.Account;
import io.anhkhue.more.mining.model.Movie;
import io.anhkhue.more.mining.model.MovieHasCategory;
import io.anhkhue.more.mining.recommendations.MovieRecommendation;
import io.anhkhue.more.mining.recommendations.MovieRecommendationFactory;
import io.anhkhue.more.mining.repository.AccountRepository;
import io.anhkhue.more.mining.repository.MovieHasCategoryRepository;
import io.anhkhue.more.mining.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

import static io.anhkhue.more.mining.recommendations.MovieRecommendationFactory.*;

@SpringBootApplication
public class MiningApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiningApplication.class, args);
    }

    @Bean
    @Autowired
    CommandLineRunner init(MovieRecommendationFactory recommendationFactory,
                           MovieInfoCentric movieInfoCentric,
                           MovieRepository movieRepository) {
        return args -> {
            testInfoCentricSimilarity(recommendationFactory, movieInfoCentric, movieRepository);
        };
    }

    @SuppressWarnings("unchecked")
    private void testInfoCentricSimilarity(MovieRecommendationFactory recommendationFactory,
                                           MovieInfoCentric movieInfoCentric,
                                           MovieRepository movieRepository) {
        MovieRecommendation<Movie, Similarity<Movie>> recommendation = recommendationFactory.getInstance(INFO);

        Movie movie = movieRepository.findById(2).orElse(null);
        Map<Integer, Double> movieRecommendations = recommendation.recommend(movie, movieInfoCentric);
        movieRecommendations.forEach((k, v) -> movieRepository.findById(k).ifPresent(other -> System.out.println(other.getTitle() + " : " + v)));
    }

    private void testSameCategories(MovieHasCategoryRepository movieHasCategoryRepository) {
        List<MovieHasCategory> sameCategories = movieHasCategoryRepository.findCommonCategories(1, 8);
        sameCategories.forEach(c -> System.out.println(c.getCategoryName()));
        Long count = movieHasCategoryRepository.countCommonCategories(1, 8);
        System.out.println(count);
    }

    @SuppressWarnings("unchecked")
    private void testSimilarityMining(MovieRecommendationFactory recommendationFactory,
                                      EuclideanDistance euclideanDistance,
                                      PearsonCorrelation pearsonCorrelation,
                                      AccountRepository accountRepository,
                                      MovieRepository movieRepository) {
        MovieRecommendation<Account, Similarity<Account>> recommendation = recommendationFactory.getInstance(RATING);

        Account user1 = accountRepository.findByUsername("anhkhue").orElse(null);

        System.out.println("Recommendations by Euclidean Distance");
        Map<Integer, Double> movieRecommendations = recommendation.recommend(user1, euclideanDistance);
        movieRecommendations.forEach((k, v) -> movieRepository.findById(k).ifPresent(movie -> System.out.println(movie.getTitle() + " : " + v)));

        System.out.println("Recommendations by Pearson Score");
        movieRecommendations = recommendation.recommend(user1, pearsonCorrelation);
        movieRecommendations.forEach((k, v) -> movieRepository.findById(k).ifPresent(movie -> System.out.println(movie.getTitle() + " : " + v)));
    }
}
