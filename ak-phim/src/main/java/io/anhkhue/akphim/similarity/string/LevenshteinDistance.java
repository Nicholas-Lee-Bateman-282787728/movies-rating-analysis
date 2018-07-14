package io.anhkhue.akphim.similarity.string;

import io.anhkhue.akphim.similarity.Similarity;

import java.util.Arrays;

public class LevenshteinDistance implements Similarity<String> {

    @Override
    public double score(String x, String y) {
        int[][] distance = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    distance[i][j] = j;
                } else if (j == 0) {
                    distance[i][j] = i;
                } else {
                    distance[i][j] = min(distance[i - 1][j] + 1,
                                         distance[i][j - 1] + 1,
                                         distance[i - 1][j - 1] + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)));
                }
            }
        }

        return distance[x.length()][y.length()];
    }

    private int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    private int min(int... numbers) {
        return Arrays.stream(numbers)
                     .min()
                     .orElse(Integer.MAX_VALUE);
    }
}
