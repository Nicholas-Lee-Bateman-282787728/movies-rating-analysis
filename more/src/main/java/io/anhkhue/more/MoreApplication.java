package io.anhkhue.more;

import io.anhkhue.more.repositories.AccountRepository;
import io.anhkhue.more.repositories.MovieRepository;
import io.anhkhue.more.services.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

    @Bean
    CommandLineRunner init() {
        return args -> {
            /*List<String> usernameList = accountRepository.findAll().stream()
                                                         .map(Account::getUsername)
                                                         .collect(Collectors.toList());
            List<Movie> movies = movieRepository.findAll();

            Runnable runnable = () -> {
                String randomUsername = usernameList.get(new Random().nextInt(usernameList.size()));
                Movie randomMovie = movies.get(new Random().nextInt(movies.size()));
                int randomRating = new Random().nextInt(RATING_MAX + 1 - RATING_MIN) + RATING_MIN;

                movieService.visit(randomMovie.getId());
                log.info("A user visited movie id " + randomMovie.getTitle());
                if (!randomMovie.isComing()) {
                    movieService.rate(randomRating, randomMovie.getId(), randomUsername);
                    log.info(randomUsername + " rated " + randomRating + " for movie " + randomMovie.getTitle());
                }
            };

            Executors.newScheduledThreadPool(1)
                     .scheduleAtFixedRate(runnable, 0, 100, TimeUnit.MILLISECONDS);*/
        };
    }
}
