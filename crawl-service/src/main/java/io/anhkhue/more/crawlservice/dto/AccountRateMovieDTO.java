package io.anhkhue.more.crawlservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

// Lombok
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
// JPA
@Entity
@Table(name = "account_rate_movie", schema = "more_db")
public class AccountRateMovieDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "account_username")
    private String accountUsername;

    @ManyToOne
    @JoinColumn(name = "account_username", referencedColumnName = "username",
            nullable = false, insertable = false, updatable = false)
    private AccountDTO account;

    @Column(name = "movie_id")
    private int movieId;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id",
            nullable = false, insertable = false, updatable = false)
    private MovieDTO movie;

    @Column(name = "timestamp")
    private long timestamp;

    @Column(name = "rating")
    private int rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountRateMovieDTO that = (AccountRateMovieDTO) o;
        return timestamp == that.timestamp &&
               rating == that.rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, rating);
    }


}
