package io.anhkhue.more.mining.function.similarity.movie;

import io.anhkhue.more.mining.function.similarity.Similarity;
import io.anhkhue.more.mining.model.Movie;
import io.anhkhue.more.mining.repository.ActorInMovieRepository;
import io.anhkhue.more.mining.repository.MovieHasCategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class MovieInfoCentric implements Similarity<Movie> {

    private final MovieHasCategoryRepository movieHasCategoryRepository;
    private final ActorInMovieRepository actorInMovieRepository;

    public MovieInfoCentric(MovieHasCategoryRepository movieHasCategoryRepository,
                            ActorInMovieRepository actorInMovieRepository) {
        this.movieHasCategoryRepository = movieHasCategoryRepository;
        this.actorInMovieRepository = actorInMovieRepository;
    }

    @Override
    public double score(Movie movie1, Movie movie2) {
        double totalScore = 0;

        // Similar Categories Score
        Long categoryScore = movieHasCategoryRepository.countCommonCategories(movie1.getId(), movie2.getId());
        totalScore += categoryScore;

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
