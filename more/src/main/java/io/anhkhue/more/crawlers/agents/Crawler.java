package io.anhkhue.more.crawlers.agents;

public interface Crawler {

    default void crawl() {
        crawlMovieCollection();
        crawlMovieDetail();
        addToPool();
    }

    void crawlMovieCollection();

    void crawlMovieDetail();

    void addToPool();
}
