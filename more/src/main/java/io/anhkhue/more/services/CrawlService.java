package io.anhkhue.more.services;

import io.anhkhue.more.crawlers.pool.CrawledPool;
import io.anhkhue.more.crawlers.utils.StringConverter;
import io.anhkhue.more.crawlers.validators.SchemaValidator;
import io.anhkhue.more.models.dao.*;
import io.anhkhue.more.models.dto.Actor;
import io.anhkhue.more.models.dto.Category;
import io.anhkhue.more.models.dto.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Collection;

import static io.anhkhue.more.crawlers.constants.CrawlersConstants.CategoryConstants.normalizedArray;
import static io.anhkhue.more.crawlers.constants.CrawlersConstants.CategoryConstants.standardizedArray;

@Slf4j
@Service
public class CrawlService {

    private static final String MOVIE_SCHEMA_PATH = "xml/schema/movie.xsd";

    private final ActorService actorService;
    private final MovieService movieService;
    private final LinkService linkService;
    private final ActorInMovieDAO actorInMovieDAO;
    private final MovieHasCategoryDAO movieHasCategoryDAO;

    private final SchemaValidator schemaValidator;

    private final StringConverter stringConverter;

    public CrawlService(ActorService actorService, MovieService movieService, LinkService linkService,
                        ActorInMovieDAO actorInMovieDAO, MovieHasCategoryDAO movieHasCategoryDAO,
                        SchemaValidator schemaValidator, StringConverter stringConverter) {
        this.actorService = actorService;
        this.movieService = movieService;
        this.linkService = linkService;
        this.actorInMovieDAO = actorInMovieDAO;
        this.movieHasCategoryDAO = movieHasCategoryDAO;
        this.schemaValidator = schemaValidator;
        this.stringConverter = stringConverter;
    }

    @Transactional
    public void savePool() {
        movieService.updateOldVendorMovies();

        Collection<Movie> movies = filterMovies(CrawledPool.getCrawledPool());

        for (Movie movie: movies) {
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
            for (String actorName: movie.getActors()
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
            for (Category category: movie.getCategories()
                                         .getCategory()) {
                String categoryName = category.getCategoryName();
                movieHasCategoryDAO.save(movie.getId(), categoryName);
            }
        }

        CrawledPool.clearPool();
    }

    private Collection<Movie> filterMovies(Collection<Movie> rawMovies) {
        Collection<Movie> movies = new ArrayList<>();

        for (Movie movie: rawMovies) {
            movie.setView(0);
            movie.setImportedDate(System.currentTimeMillis());
            for (Category category: movie.getCategories().getCategory()) {
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
            } catch (JAXBException e) {
                log.info(this.getClass().getSimpleName() + "_" + e.getClass().getSimpleName()
                         + ": Fail to validate " + movie.getTitle()
                         + "- Skipped");
            }
        }

        return movies;
    }
}
