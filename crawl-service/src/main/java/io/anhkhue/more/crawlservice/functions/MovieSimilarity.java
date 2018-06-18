package io.anhkhue.more.crawlservice.functions;

import io.anhkhue.more.crawlservice.dto.MovieDTO;

public class MovieSimilarity implements Similarity<MovieDTO> {

    private static final StringSimilarity stringSimilarity = StringSimilarity.getInstance();

    private static MovieSimilarity instance = new MovieSimilarity();

    public static MovieSimilarity getInstance() {
        return instance;
    }

    private MovieSimilarity() {
    }

    @Override
    public double score(MovieDTO m1, MovieDTO m2) {
        if (!m1.getYear().equals(m2.getYear()) ||
            !m1.getDirector().equals(m2.getDirector())) {
            return 0;
        }

        return stringSimilarity.score(m1.getTitle(), m2.getTitle());
    }
}
