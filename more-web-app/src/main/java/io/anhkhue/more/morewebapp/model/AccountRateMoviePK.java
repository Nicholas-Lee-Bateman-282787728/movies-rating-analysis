package io.anhkhue.more.morewebapp.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class AccountRateMoviePK implements Serializable {

    private String accountUsername;
    private int movieId;

    @Column(name = "account_username")
    @Id
    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    @Column(name = "movie_id")
    @Id
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountRateMoviePK that = (AccountRateMoviePK) o;
        return movieId == that.movieId &&
               Objects.equals(accountUsername, that.accountUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountUsername, movieId);
    }
}
