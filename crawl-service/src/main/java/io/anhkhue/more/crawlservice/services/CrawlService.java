package io.anhkhue.more.crawlservice.services;

import io.anhkhue.more.crawlservice.crawlers.constants.CategoryConstants;
import io.anhkhue.more.crawlservice.crawlers.pool.CrawledPool;
import io.anhkhue.more.crawlservice.crawlers.utils.StringConverter;
import io.anhkhue.more.crawlservice.dao.*;
import io.anhkhue.more.crawlservice.dto.ActorDTO;
import io.anhkhue.more.crawlservice.dto.CategoryDTO;
import io.anhkhue.more.crawlservice.dto.MovieDTO;
import org.springframework.stereotype.Service;

@Service
public class CrawlService {

    private final ActorDAO actorDAO;
    private final MovieDAO movieDAO;
    private final LinkDAO linkDAO;
    private final ActorInMovieDAO actorInMovieDAO;
    private final MovieHasCategoryDAO movieHasCategoryDAO;

    private final StringConverter stringConverter;

    public CrawlService(ActorDAO actorDAO, MovieDAO movieDAO, LinkDAO linkDAO, ActorInMovieDAO actorInMovieDAO, MovieHasCategoryDAO movieHasCategoryDAO, StringConverter stringConverter) {
        this.actorDAO = actorDAO;
        this.movieDAO = movieDAO;
        this.linkDAO = linkDAO;
        this.actorInMovieDAO = actorInMovieDAO;
        this.movieHasCategoryDAO = movieHasCategoryDAO;
        this.stringConverter = stringConverter;
    }

    public void savePool() {
        movieDAO.updateOldVendorMovies();

        for (MovieDTO movieDTO: CrawledPool.crawledPool) {
            // Save movie
            int idStatus = movieDAO.save(movieDTO);
            movieDTO.setId(idStatus);
            // Save links as long as movies exists in database
            if (idStatus != MovieDAO.UNABLE_TO_SAVE) {
                movieDTO.getLinks().forEach(linkDto -> {
                    linkDto.setMovieId(movieDTO.getId());
                    linkDAO.save(linkDto);
                });
            }
            // Save actors
            for (ActorDTO actorDTO: movieDTO.getActors()) {
                int actorId = actorDAO.save(actorDTO);
                actorDTO.setId(actorId);
                // Save actor_in_movie
                actorInMovieDAO.save(actorDTO.getId(), movieDTO.getId());
            }
            // Save movie_has_category
            for (CategoryDTO categoryDTO: movieDTO.getCategories()) {
                String category = categoryDTO.getCategoryName();
                category = stringConverter.covertStringToURL(category);
                for (int i = 0; i < CategoryConstants.normalizedArray.length; i++) {
                    if (category.equals(CategoryConstants.normalizedArray[i])) {
                        category = CategoryConstants.standardizedArray[i];
                    }
                }
                movieHasCategoryDAO.save(movieDTO.getId(), category);
            }
        }

        CrawledPool.clearPool();
    }
}
