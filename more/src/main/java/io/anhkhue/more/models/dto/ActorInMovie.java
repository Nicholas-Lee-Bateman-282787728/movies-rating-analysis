package io.anhkhue.more.models.dto;

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
public class ActorInMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "actor_id")
    private int actorId;

    @Column(name = "movie_id")
    private int movieId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorInMovie that = (ActorInMovie) o;
        return id == that.id &&
               actorId == that.actorId &&
               movieId == that.movieId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, actorId, movieId);
    }
}
