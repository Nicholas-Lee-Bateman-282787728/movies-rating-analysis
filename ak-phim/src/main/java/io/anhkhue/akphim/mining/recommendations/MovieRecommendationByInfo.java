package io.anhkhue.akphim.mining.recommendations;

import io.anhkhue.akphim.models.dto.Movie;
import io.anhkhue.akphim.models.dto.MovieHasCategory;
import io.anhkhue.akphim.repositories.MovieHasCategoryRepository;
import io.anhkhue.akphim.repositories.MovieRepository;
import io.anhkhue.akphim.similarity.Similarity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieRecommendationByInfo implements MovieRecommendation<Movie, Similarity<Movie>> {

    private final MovieHasCategoryRepository movieHasCategoryRepository;
    private final MovieRepository movieRepository;

    MovieRecommendationByInfo(MovieHasCategoryRepository movieHasCategoryRepository,
                              MovieRepository movieRepository) {
        this.movieHasCategoryRepository = movieHasCategoryRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public Map<Integer, Double> recommend(Movie movie, Similarity<Movie> similarity) {
        Map<Integer, Double> rankings = new HashMap<>();

        // Optimize set of mining
        String specificCategories = movieHasCategoryRepository.findByMovieId(movie.getId()).stream()
                                                              .map(MovieHasCategory::getCategoryName)
                                                              .collect(Collectors.toList())
                                                              .get(0);

        List<MovieHasCategory> otherMovies = movieHasCategoryRepository
                .findByCategoryNameAndMovieIdNot(specificCategories, movie.getId());

        for (MovieHasCategory movieHasCategory: otherMovies) {
            movieRepository.findById(movieHasCategory.getMovieId()).ifPresent(other -> {
                double simScore = similarity.score(movie, other);
                rankings.put(other.getId(), simScore);
            });
        }

        return rankings.entrySet().stream()
                       .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                       .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                 (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
