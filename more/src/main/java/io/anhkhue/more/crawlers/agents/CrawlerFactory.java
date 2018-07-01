package io.anhkhue.more.crawlers.agents;

import io.anhkhue.more.crawlers.sources.readers.SourceReader;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CrawlerFactory {

    private final SourceReader sourceReader;

    public CrawlerFactory(SourceReader sourceReader) {
        this.sourceReader = sourceReader;
    }

    @SuppressWarnings("unchecked")
    private Class<Crawler> produceCrawler(String classPath) {
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

    public Collection<Crawler> scanCrawlers()
            throws IOException,
                   XMLStreamException,
                   IllegalAccessException,
                   InstantiationException {
        List<Crawler> crawlers = new ArrayList<>();

        List<String> classPaths = sourceReader.getCrawlerClassPaths();
        for (String classPath : classPaths) {
            Class<Crawler> crawlerClass = produceCrawler(classPath);
            if (crawlerClass != null) {
                crawlers.add(crawlerClass.newInstance());
            }
        }

        return crawlers;
    }
}
