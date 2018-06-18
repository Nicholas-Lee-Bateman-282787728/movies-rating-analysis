package io.anhkhue.more.crawlservice.functions;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class StringSimilarity implements Similarity<String> {

    private static StringSimilarity instance = new StringSimilarity();

    public static StringSimilarity getInstance() {
        return instance;
    }

    private StringSimilarity() {
    }

    @Override
    public double score(String s1, String s2) {
        LevenshteinDistance levenshteinDistance = LevenshteinDistance.getDefaultInstance();

        int distance = levenshteinDistance.apply(s1, s2);
        int maxLength = Math.max(s1.length(), s2.length());

        return ((double) maxLength - (double) distance) / (double) maxLength * 100;
    }
}
