package io.anhkhue.akphim.similarity.movie;

import io.anhkhue.akphim.models.dto.Movie;
import io.anhkhue.akphim.repositories.ActorInMovieRepository;
import io.anhkhue.akphim.repositories.MovieHasCategoryRepository;
import io.anhkhue.akphim.similarity.Similarity;
import org.springframework.stereotype.Component;

@Component
public class MovieSimilarity implements Similarity<Movie> {

    private final MovieHasCategoryRepository movieHasCategoryRepository;
    private final ActorInMovieRepository actorInMovieRepository;

    public MovieSimilarity(MovieHasCategoryRepository movieHasCategoryRepository,
                           ActorInMovieRepository actorInMovieRepository) {
        this.movieHasCategoryRepository = movieHasCategoryRepository;
        this.actorInMovieRepository = actorInMovieRepository;
    }

    @Override
    public double score(Movie movie1, Movie movie2) {
        double totalScore = 0;

        // Similar Categories Score
        Long categoryScore = movieHasCategoryRepository.countCommonCategories(movie1.getId(), movie2.getId());
        if (categoryScore != null) {
            totalScore += categoryScore;
        }

        // Similar Directors Score
        String movie1Director = movie1.getDirector();
        String movie2Director = movie2.getDirector();
        if (movie1Director.contains(movie2Director) ||
            movie2Director.contains(movie1Director)) {
            totalScore += 1;
        }

        // Similar Actors score
        Long actorScore = actorInMovieRepository.countCommonActors(movie1.getId(), movie2.getId());
        if (actorScore != null) {
            totalScore += actorScore;
        }

        return totalScore;
    }
}
