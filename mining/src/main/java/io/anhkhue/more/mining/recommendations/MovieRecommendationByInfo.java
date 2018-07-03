package io.anhkhue.more.mining.recommendations;

import io.anhkhue.more.mining.function.similarity.Similarity;
import io.anhkhue.more.mining.model.Movie;
import io.anhkhue.more.mining.model.MovieHasCategory;
import io.anhkhue.more.mining.repository.MovieHasCategoryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieRecommendationByInfo implements MovieRecommendation<Movie, Similarity<Movie>> {


    private final MovieHasCategoryRepository movieHasCategoryRepository;

    public MovieRecommendationByInfo(MovieHasCategoryRepository movieHasCategoryRepository) {
        this.movieHasCategoryRepository = movieHasCategoryRepository;
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

        if (otherMovies.isEmpty()) {
            return rankings;
        }



        return rankings;
    }
}
