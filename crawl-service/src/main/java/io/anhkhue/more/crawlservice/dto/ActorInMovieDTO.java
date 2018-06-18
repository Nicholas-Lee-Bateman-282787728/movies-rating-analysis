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
@Table(name = "actor_in_movie", schema = "more_db")
public class ActorInMovieDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "actor_id")
    private int actorId;

    @ManyToOne
    @JoinColumn(name = "actor_id", referencedColumnName = "id",
            nullable = false, insertable = false, updatable = false)
    private ActorDTO actor;

    @Column(name = "movie_id")
    private int movieId;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id",
            nullable = false, insertable = false, updatable = false)
    private MovieDTO movie;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorInMovieDTO that = (ActorInMovieDTO) o;
        return id == that.id &&
               actorId == that.actorId &&
               movieId == that.movieId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, actorId, movieId);
    }
}
