package io.anhkhue.more.services;

import io.anhkhue.more.crawlers.agents.Crawler;
import io.anhkhue.more.crawlers.agents.CrawlerFactory;
import io.anhkhue.more.crawlers.pool.CrawledPool;
import io.anhkhue.more.crawlers.utils.StringConverter;
import io.anhkhue.more.crawlers.validators.SchemaValidator;
import io.anhkhue.more.mining.cache.AccountRecommendationCache;
import io.anhkhue.more.models.dao.ActorInMovieDAO;
import io.anhkhue.more.models.dao.MovieHasCategoryDAO;
import io.anhkhue.more.models.dto.Actor;
import io.anhkhue.more.models.dto.Category;
import io.anhkhue.more.models.dto.Movie;
import io.anhkhue.more.repositories.ActorInMovieRepository;
import io.anhkhue.more.repositories.MovieHasCategoryRepository;
import io.anhkhue.more.repositories.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static io.anhkhue.more.crawlers.constants.CrawlersConstants.CategoryConstants.normalizedArray;
import static io.anhkhue.more.crawlers.constants.CrawlersConstants.CategoryConstants.standardizedArray;

@Slf4j
@Service
public class CrawlService {

    public static boolean isCrawling = false;

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static final String MOVIE_SCHEMA_PATH = "static/xml/schema/movie.xsd";

    private final CrawlerFactory crawlerFactory;

    private final ActorService actorService;
    private final MovieService movieService;
    private final MovieRepository movieRepository;
    private final LinkService linkService;
    private final ActorInMovieDAO actorInMovieDAO;
    private final ActorInMovieRepository actorInMovieRepository;
    private final MovieHasCategoryDAO movieHasCategoryDAO;
    private final MovieHasCategoryRepository movieHasCategoryRepository;

    private final SchemaValidator schemaValidator;

    private final StringConverter stringConverter;

    public CrawlService(CrawlerFactory crawlerFactory,
                        ActorService actorService,
                        MovieService movieService,
                        MovieRepository movieRepository,
                        LinkService linkService,
                        ActorInMovieDAO actorInMovieDAO,
                        ActorInMovieRepository actorInMovieRepository,
                        MovieHasCategoryDAO movieHasCategoryDAO,
                        MovieHasCategoryRepository movieHasCategoryRepository,
                        SchemaValidator schemaValidator,
                        StringConverter stringConverter) {
        this.crawlerFactory = crawlerFactory;
        this.actorService = actorService;
        this.movieService = movieService;
        this.movieRepository = movieRepository;
        this.linkService = linkService;
        this.actorInMovieDAO = actorInMovieDAO;
        this.actorInMovieRepository = actorInMovieRepository;
        this.movieHasCategoryDAO = movieHasCategoryDAO;
        this.movieHasCategoryRepository = movieHasCategoryRepository;
        this.schemaValidator = schemaValidator;
        this.stringConverter = stringConverter;
    }

    @Transactional
    public void savePool() {
        movieService.updateOldVendorMovies();

        Collection<Movie> movies = filterMovies(CrawledPool.getCrawledPool());

        for (Movie movie : movies) {
            // Save movie
            int idStatus = movieService.save(movie);
            movie.setId(idStatus);
            // Save links as long as movies exists in database
            if (idStatus != MovieService.UNABLE_TO_SAVE) {
                movie.getLinks()
                     .getLink()
                     .forEach(link -> {
                         link.setMovieId(movie.getId());
                         linkService.save(link);
                     });
            }
            // Save actors
            for (String actorName : movie.getActors()
                                         .getActor()) {
                Actor actor = Actor.builder()
                                   .fullName(actorName)
                                   .build();
                int actorId = actorService.save(actor);
                actor.setId(actorId);
                // Save actor_in_movie
                actorInMovieDAO.save(actor.getId(), movie.getId());
            }
            // Save movie_has_category
            for (Category category : movie.getCategories()
                                          .getCategory()) {
                String categoryName = category.getCategoryName();
                movieHasCategoryDAO.save(movie.getId(), categoryName);
            }
            log.info("Saved movie " + movie.getTitle());
        }

        CrawledPool.clearPool();
    }

    private Collection<Movie> filterMovies(Collection<Movie> rawMovies) {
        Collection<Movie> movies = new ArrayList<>();

        for (Movie movie : rawMovies) {
            movie.setView(0);
            movie.setImportedDate(System.currentTimeMillis());
            for (Category category : movie.getCategories().getCategory()) {
                String categoryName = category.getCategoryName();
                categoryName = stringConverter.normalizeVietnamese(categoryName);
                for (int i = 0; i < normalizedArray.length; i++) {
                    if (categoryName.equals(normalizedArray[i])) {
                        categoryName = standardizedArray[i];
                        // Reset categoryName
                        category.setCategoryName(categoryName);
                        break;
                    }
                }
            }
            try {
                schemaValidator.validate(movie, MOVIE_SCHEMA_PATH);
                movies.add(movie);
            } catch (JAXBException | SAXException | IOException e) {
                log.info(this.getClass().getSimpleName() + "_" + e.getClass().getSimpleName()
                         + " " + e.getMessage());
                log.info(": Fail to validate " + movie.getTitle() + " - Skipped");
            }
        }

        return movies;
    }

    private void crawl() throws XMLStreamException,
                                InstantiationException,
                                IllegalAccessException,
                                IOException {
        Collection<Crawler> crawlers = crawlerFactory.scanCrawlers();
        crawlers.forEach(Crawler::crawl);

        savePool();
    }

    public void controlCrawlers(boolean isTurnedOn) {
        if (isTurnedOn) {
            isCrawling = true;
            Runnable crawlRunnable = () -> {
                try {
                    crawl();
                    Date date = new Date();
                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int year = localDate.getYear();
                    int month = localDate.getMonthValue();
                    int day = localDate.getDayOfMonth();
                    log.info("Finish crawling on : " + day + "-" + month + "-" + year);
                    LocalDate nextWeek = localDate.plusDays(7);
                    log.info("Next crawling will start on : " + nextWeek.getDayOfMonth()
                             + "-" + nextWeek.getMonthValue()
                             + "-" + nextWeek.getYear());
                    log.info("Clearing cached recommendations.");
                    AccountRecommendationCache.clearCache();
                } catch (XMLStreamException | InstantiationException | IOException | IllegalAccessException e) {
                    log.info(this.getClass().getSimpleName() + "_" + e.getClass().getSimpleName() + ": " + e.getMessage());
                }
            };
            scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(crawlRunnable, 0, 7, TimeUnit.DAYS);
        } else {
            isCrawling = false;
            scheduler.shutdown();
        }
    }
}
