package io.anhkhue.more.morewebapp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "account_rate_movie", schema = "more_db", catalog = "")
@IdClass(AccountRateMoviePK.class)
public class AccountRateMovie {

    private String accountUsername;
    private int movieId;
    private long timestamp;
    private int rating;

    @Id
    @Column(name = "account_username")
    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    @Id
    @Column(name = "movie_id")
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Basic
    @Column(name = "timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "rating")
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountRateMovie that = (AccountRateMovie) o;
        return movieId == that.movieId &&
               timestamp == that.timestamp &&
               rating == that.rating &&
               Objects.equals(accountUsername, that.accountUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountUsername, movieId, timestamp, rating);
    }
}
