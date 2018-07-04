package io.anhkhue.more.functions.similarity.movie;

import io.anhkhue.more.functions.similarity.Similarity;
import io.anhkhue.more.functions.similarity.string.StringSimilarity;
import io.anhkhue.more.models.dto.Movie;
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
