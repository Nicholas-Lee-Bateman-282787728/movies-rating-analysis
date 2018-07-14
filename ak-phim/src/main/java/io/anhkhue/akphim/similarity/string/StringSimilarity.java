package io.anhkhue.akphim.similarity.string;

import io.anhkhue.akphim.similarity.Similarity;
import org.springframework.stereotype.Component;

@Component
public class StringSimilarity implements Similarity<String> {

    @Override
    public double score(String s1, String s2) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        double distance = levenshteinDistance.score(s1, s2);
        int maxLength = Math.max(s1.length(), s2.length());

        return ((double) maxLength - distance) / (double) maxLength * 100;
    }
}
