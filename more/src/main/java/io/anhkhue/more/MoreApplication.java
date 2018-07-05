package io.anhkhue.more;

import io.anhkhue.more.functions.similarity.string.LevenshteinDistance;
import io.anhkhue.more.repositories.AccountRepository;
import io.anhkhue.more.repositories.MovieRepository;
import io.anhkhue.more.services.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MoreApplication {

    private final AccountRepository accountRepository;
    private final MovieRepository movieRepository;

    private final MovieService movieService;

    private final Integer RATING_MIN = 1;
    private final Integer RATING_MAX = 5;


    public MoreApplication(AccountRepository accountRepository,
                           MovieRepository movieRepository,
                           MovieService movieService) {
        this.accountRepository = accountRepository;
        this.movieRepository = movieRepository;
        this.movieService = movieService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MoreApplication.class, args);
    }
}
