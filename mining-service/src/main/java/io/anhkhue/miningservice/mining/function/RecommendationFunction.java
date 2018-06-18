package io.anhkhue.miningservice.mining.function;

import io.anhkhue.miningservice.mining.entity.BasedMining;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface RecommendationFunction<T, U, V> {

    public List getRecommendation(Map<T, Map<U, V>> preferences,
                                  BasedMining item,
                                  SimilarityFunction similarityFunction);
}
