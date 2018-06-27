package io.anhkhue.more.services;

import io.anhkhue.more.models.dto.Link;
import io.anhkhue.more.repositories.LinkRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LinkService {

    private final LinkRepository repository;

    public LinkService(LinkRepository repository) {
        this.repository = repository;
    }

    public void save(Link linkDto) {
        repository.save(linkDto);
    }

    public Collection<Link> findByMovieId(int movieId) {
        return repository.findByMovieId(movieId);
    }
}
