package io.anhkhue.more.models.mining;

import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class MovieRecommendationRanking {

    private Map<Integer, Double> rankings;

    public MovieRecommendationRanking() {
        rankings = new HashMap<>();
    }

    public void sort() {
        this.rankings = this.rankings.entrySet().stream()
                                     .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                                     .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                               (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
