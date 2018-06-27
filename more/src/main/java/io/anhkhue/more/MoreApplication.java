package io.anhkhue.more;

import io.anhkhue.more.crawlers.agents.CrawlerFactory;
import io.anhkhue.more.services.CrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoreApplication.class, args);
    }

    @Bean
    @Autowired
    CommandLineRunner init(CrawlerFactory crawlerFactory, CrawlService crawlService) {
        return args -> {
            /*Actors actors = new Actors();
            actors.getActor().addAll(Arrays.asList("A", "B", "C", "D", "E"));
            Categories categories = new Categories();
            categories.getCategory().addAll(Arrays.asList(new Category("hành động"),
                                                          new Category("tình cảm"),
                                                          new Category("hình sự")));
            Links links = new Links();
            links.getLink().addAll(Arrays.asList(Link.builder().source("BHD").url("http://bhd.com").isCinema(true).build(),
                                                 Link.builder().source("Phimmoi").url("http://phimmoi.com").isCinema(false).build()));
            Movie movie = Movie.builder()
                               .id(1)
                               .actors(actors)
                               .year(BigInteger.valueOf(2015))
                               .categories(categories)
                               .links(links)
                               .title("ABC")
                               .director("Direc")
                               .poster("poster")
                               .onCinema(true)
                               .isComing(false)
                               .view(0)
                               .rating(0.0)
                               .build();

            Actors actors1 = new Actors();
            actors1.getActor().addAll(Arrays.asList("A", "B", "C", "D", "E"));
            Categories categories1 = new Categories();
            categories1.getCategory().addAll(Arrays.asList(new Category("chiến tranh"),
                                                           new Category("thần thoại"),
                                                           new Category("võ thuật")));
            Links links1 = new Links();
            links1.getLink().addAll(Arrays.asList(Link.builder().source("BHD").url("http://bhd.com").isCinema(true).build(),
                                                  Link.builder().source("Phimmoi").url("http://phimmoi.com").isCinema(false).build()));
            Movie movie1 = Movie.builder()
                                .id(2)
                                .year(BigInteger.valueOf(2015))
                                .actors(actors1)
                                .categories(categories1)
                                .links(links1)
                                .title("XYZ")
                                .director("Direc1")
                                .poster("poster1")
                                .onCinema(true)
                                .isComing(false)
                                .view(0)
                                .rating(0.0)
                                .build();

            Movies movies = new Movies();
            movies.getMovie().add(movie);
            movies.getMovie().add(movie1);

            SchemaValidator schemaValidator = new SchemaValidator();
            schemaValidator.validate(movie, "xml/schema/movie.xsd");
            schemaValidator.validate(movies, "xml/schema/movies.xsd");*/

            /*Runnable runnable = () -> {
                Collection<Crawler> crawlers = null;
                try {
                    crawlers = crawlerFactory.scanCrawlers();
                } catch (IOException | XMLStreamException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                crawlers.forEach(Crawler::crawl);
                crawlService.savePool();
            };

            runnable.run();*/
        };
    }
}
