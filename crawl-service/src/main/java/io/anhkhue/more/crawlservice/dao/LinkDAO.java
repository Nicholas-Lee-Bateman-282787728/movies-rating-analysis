package io.anhkhue.more.crawlservice.dao;

import io.anhkhue.more.crawlservice.dto.LinkDTO;
import io.anhkhue.more.crawlservice.repositories.LinkRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class LinkDAO {

    private final LinkRepository repository;

    public LinkDAO(LinkRepository repository) {
        this.repository = repository;
    }

    public void save(LinkDTO linkDto) {
        repository.save(linkDto);
    }

    public Collection<LinkDTO> findByMovieId(int movieId) {
        return repository.findByMovieId(movieId);
    }
}
