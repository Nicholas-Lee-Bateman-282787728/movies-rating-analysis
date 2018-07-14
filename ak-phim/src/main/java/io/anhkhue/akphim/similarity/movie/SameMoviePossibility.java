package io.anhkhue.akphim.similarity.movie;

import io.anhkhue.akphim.models.dto.Movie;
import io.anhkhue.akphim.similarity.Similarity;
import io.anhkhue.akphim.similarity.string.StringSimilarity;
import org.springframework.stereotype.Component;

@Component
public class SameMoviePossibility implements Similarity<Movie> {

    private final Similarity<String> stringSimilarity;

    public SameMoviePossibility(StringSimilarity stringSimilarity) {
        this.stringSimilarity = stringSimilarity;
    }

    @Override
    public double score(Movie m1, Movie m2) {
        if (!m1.getYear().equals(m2.getYear()) ||
            !m1.getDirector().equals(m2.getDirector())) {
            return 0;
        }

        return stringSimilarity.score(m1.getTitle(), m2.getTitle());
    }
}
