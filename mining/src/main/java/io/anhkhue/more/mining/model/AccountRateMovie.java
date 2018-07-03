package io.anhkhue.more.mining.model;

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
public class AccountRateMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "account_username")
    private String accountUsername;

    @Column(name = "movie_id")
    private int movieId;

    @Column(name = "timestamp")
    private long timestamp;

    @Column(name = "rating")
    private int rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountRateMovie that = (AccountRateMovie) o;
        return timestamp == that.timestamp &&
               rating == that.rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, rating);
    }


}
