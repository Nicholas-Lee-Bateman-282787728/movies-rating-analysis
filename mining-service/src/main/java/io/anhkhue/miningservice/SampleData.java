package io.anhkhue.miningservice;

import io.anhkhue.miningservice.mining.entity.Movie;
import io.anhkhue.miningservice.mining.function.LongestCommonSubstring;
import io.anhkhue.miningservice.mining.function.MinValue;
import io.anhkhue.miningservice.model.RateEvent;
import io.anhkhue.miningservice.mining.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static io.anhkhue.miningservice.RatingConsts.MAX;
import static io.anhkhue.miningservice.RatingConsts.MIN;

@Component
public class SampleData implements ApplicationRunner {

    private static Logger log = LoggerFactory.getLogger(SampleData.class);

    @Override
    public void run(ApplicationArguments args) {
//        var users = List.of(new User(1, "anhkhue"),
//                            new User(2, "billgates"),
//                            new User(3, "stevejobs"),
//                            new User(4, "brucewayne"),
//                            new User(5, "tonystark"),
//                            new User(5, "elonmusk"));
//
//        Map<User, Map<Movie, RateEvent>> critics = users.stream()
//                .collect(Collectors.toMap(user -> user,
//                                          user -> new HashMap<>()));
//
//        var movies = List.of(new Movie(1, "Avengers: Infinity War"),
//                             new Movie(2, "Justice League"),
//                             new Movie(3, "Titanic"),
//                             new Movie(4, "Pacific Rim"),
//                             new Movie(5, "Tomb Raider"),
//                             new Movie(6, "Chappie"),
//                             new Movie(7, "Terminator"),
//                             new Movie(8, "Jurassic Park"),
//                             new Movie(9, "Independence Day"),
//                             new Movie(10, "Star Trek"));
//
//        Runnable runnable = () -> {
//            var randomMovie = movies.get(new Random().nextInt(movies.size()));
//            var randomRating = new Random().nextInt(MAX + 1 - MIN) + MIN;
//
//            var rateEvent = new RateEvent(randomRating, System.currentTimeMillis());
//
//            var randomUser = users.get(new Random().nextInt(users.size()));
//            critics.get(randomUser).put(randomMovie, rateEvent);
//
//            log.info(randomUser.getUsername() + " | " + randomMovie.getTitle() + " | " + randomRating);
//
//            log.info("Critics from " + randomUser.getUsername());
//            critics.get(randomUser).forEach((movie, movieRateEvent) -> log.info(movie.getTitle() + " | " + movieRateEvent.getRating()));
//        };
//
//        Executors.newScheduledThreadPool(1)
//                .scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);

        String cgv = "Avengers: Cuộc Chiến Vô Cực";
        String vungtv = "Biệt Đội Siêu Anh Hùng 3: Cuộc Chiến Vô Cực Phần 1";
        double lcsRate = new LongestCommonSubstring().score((s1, s2) -> Math.min(s1.length(), s2.length()),
                                                            cgv,
                                                            vungtv);
        log.info(String.valueOf(lcsRate));
    }
}
