package io.anhkhue.more.crawlservice.crawlers;

import io.anhkhue.more.crawlservice.xml.readers.SourceReader;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CrawlerFactory implements Serializable {

    @SuppressWarnings("unchecked")
    private static Class<Crawler> produceCrawler(String classPath) {
        Class<?> crawlerClass;
        try {
            crawlerClass = Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            return null;
        }

        if (Crawler.class.isAssignableFrom(crawlerClass)) {
            return (Class<Crawler>) crawlerClass;
        }

        return null;
    }

    public static List<Crawler> scanCrawlers()
            throws IOException,
                   XMLStreamException,
                   IllegalAccessException,
                   InstantiationException {
        List<Crawler> crawlers = new ArrayList<>();

        List<String> classPaths = SourceReader.getCrawlerClassPaths();
        for (String classPath : classPaths) {
            Class<Crawler> crawlerClass = CrawlerFactory.produceCrawler(classPath);
            if (crawlerClass != null) {
                crawlers.add(crawlerClass.newInstance());
            }
        }

        return crawlers;
    }
}
