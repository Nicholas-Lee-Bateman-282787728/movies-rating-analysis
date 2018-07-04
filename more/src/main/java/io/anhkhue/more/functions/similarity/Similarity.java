package io.anhkhue.more.functions.similarity;

@FunctionalInterface
public interface Similarity<T> {

    double score(T t1, T t2);
}
