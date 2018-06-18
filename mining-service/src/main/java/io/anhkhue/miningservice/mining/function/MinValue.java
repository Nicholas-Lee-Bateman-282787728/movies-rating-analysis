package io.anhkhue.miningservice.mining.function;

@FunctionalInterface
public interface MinValue<T, U> {

    T get(U item1, U item2);
}
