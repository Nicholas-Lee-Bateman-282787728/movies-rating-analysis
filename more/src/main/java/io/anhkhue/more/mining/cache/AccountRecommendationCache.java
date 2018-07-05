package io.anhkhue.more.mining.cache;

import io.anhkhue.more.models.dto.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountRecommendationCache {

    private static Map<String, List<Movie>> recommendationMap = new HashMap<>();

    public static void cache(String username, List<Movie> movies) {
        if (!recommendationMap.containsKey(username)) {
            recommendationMap.put(username, movies);
        }
    }

    public static Map<String, List<Movie>> getRecommendationMap() {
        return recommendationMap;
    }

    public static void removeNewRated(String username, int movieId) {
        if (recommendationMap.containsKey(username)) {
            List<Movie> movieList = recommendationMap.get(username).stream()
                                                     .filter(movie -> movie.getId() != movieId)
                                                     .collect(Collectors.toList());
            recommendationMap.put(username, movieList);
        }
    }

    public static void clearCache() {
        recommendationMap = new HashMap<>();
    }
}
