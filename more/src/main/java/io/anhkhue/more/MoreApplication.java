package io.anhkhue.more;

import io.anhkhue.more.models.constants.RoleConstants;
import io.anhkhue.more.models.dto.Account;
import io.anhkhue.more.models.dto.Movie;
import io.anhkhue.more.repositories.AccountRepository;
import io.anhkhue.more.repositories.MovieRepository;
import io.anhkhue.more.services.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

        };
    }

    private void randomMiningData() {
        List<Account> accounts = accountRepository.findByRole(RoleConstants.USER);
        Set<Movie> movieSet = movieRepository.findByOnCinemaAndIsComing(true, false);
        List<Movie> movies = new ArrayList<>(movieSet);

        Runnable randomData = () -> {
            String randomUsername = accounts.get(new Random().nextInt(accounts.size())).getUsername();
            Movie randomMovie = movies.get(new Random().nextInt(movies.size()));

            int randomRating = new Random().nextInt(RATING_MAX + 1 - RATING_MIN) + RATING_MIN;

            movieService.rate(randomRating, randomMovie.getId(), randomUsername);
            System.out.println(randomUsername + " rated " + randomRating + " for " + randomMovie.getTitle());
        };

        Executors.newScheduledThreadPool(1)
                 .scheduleAtFixedRate(randomData, 1, 1, TimeUnit.SECONDS);
    }
}
