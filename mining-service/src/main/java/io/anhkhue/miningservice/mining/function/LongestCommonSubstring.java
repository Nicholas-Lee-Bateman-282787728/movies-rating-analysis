package io.anhkhue.miningservice.mining.function;

public class LongestCommonSubstring implements SimilarityFunction<String, MinValue<Integer, String>> {

    @Override
    public double score(MinValue<Integer, String> minValue, String s1, String s2) {
        int lcs[][] = new int[s1.length() + 1][s2.length() + 1];
        // Length of the longest common substring
        int lcsLength = 0;

        // Bottom-up
        for (int i = 0; i < s1.length() + 1; i++) {
            for (int j = 0; j < s2.length() + 1; j++) {
                if (i == 0 || j == 0)
                    lcs[i][j] = 0;
                else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    lcs[i][j] = lcs[i - 1][j - 1] + 1;
                    lcsLength = Integer.max(lcsLength, lcs[i][j]);
                } else
                    lcs[i][j] = 0;
            }
        }

        return ((double) lcsLength/minValue.get(s1, s1)) * 100;
    }
}

