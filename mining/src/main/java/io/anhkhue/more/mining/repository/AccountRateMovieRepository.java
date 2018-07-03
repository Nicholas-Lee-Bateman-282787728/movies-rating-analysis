package io.anhkhue.more.mining.repository;

import io.anhkhue.more.mining.model.AccountRateMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRateMovieRepository extends JpaRepository<AccountRateMovie, Integer> {

    AccountRateMovie findByAccountUsernameAndMovieId(String username, int movieId);

    List<AccountRateMovie> findByAccountUsername(String username);

    List<AccountRateMovie> findByMovieId(int movieId);
}
