package io.anhkhue.more.crawlservice.crawlers;

import io.anhkhue.more.crawlservice.xml.parsers.DomParser;

import javax.xml.xpath.XPath;


public interface Crawler {

    XPath xPath = DomParser.createXPath();

    default void crawl() {
        crawlMovieCollection();
        crawlMovieDetail();
        addToPool();
    }

    void crawlMovieCollection();

    void crawlMovieDetail();

    void addToPool();
}
