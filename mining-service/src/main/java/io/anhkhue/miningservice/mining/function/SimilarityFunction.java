package io.anhkhue.miningservice.mining.function;

@FunctionalInterface
public interface SimilarityFunction<T, P> {

    public double score(P preferences, T item1, T item2);
}
