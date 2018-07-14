package io.anhkhue.akphim.repositories;

import io.anhkhue.akphim.models.dto.AccountRateMovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRateMovieRepository extends JpaRepository<AccountRateMovie, Integer> {

    AccountRateMovie findByAccountUsernameAndMovieId(String username, int movieId);

    List<AccountRateMovie> findByAccountUsername(String username);

    List<AccountRateMovie> findByMovieId(int movieId);
}
