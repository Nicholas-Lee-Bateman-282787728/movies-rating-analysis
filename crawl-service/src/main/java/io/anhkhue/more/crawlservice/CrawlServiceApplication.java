package io.anhkhue.more.crawlservice;

import io.anhkhue.more.crawlservice.crawlers.Crawler;
import io.anhkhue.more.crawlservice.crawlers.CrawlerFactory;
import io.anhkhue.more.crawlservice.services.CrawlService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CrawlServiceApplication {

    private final CrawlService crawlService;

    public CrawlServiceApplication(CrawlService crawlService) {
        this.crawlService = crawlService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CrawlServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner init() {
        return args -> {
            List<Crawler> crawlers = CrawlerFactory.scanCrawlers();
            crawlers.forEach(Crawler::crawl);

//            Movies movies = new Movies();
//            if (movies.getMovies() == null) {
//                movies.setMovies(new ArrayList<>());
//                movies.getMovies().addAll(CrawledPool.crawledPool);
//            }
//
//            SchemaValidator schemaValidator = new SchemaValidator();
//            schemaValidator.validate(movies, "xml/schema/movies.xsd");

//            movieDao.savePool();
            crawlService.savePool();
        };
    }


}
