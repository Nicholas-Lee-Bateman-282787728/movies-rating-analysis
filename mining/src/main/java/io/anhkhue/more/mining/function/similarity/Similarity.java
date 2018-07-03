package io.anhkhue.more.mining.function.similarity;

@FunctionalInterface
public interface Similarity<T> {

    double score(T item1, T item2);
}
