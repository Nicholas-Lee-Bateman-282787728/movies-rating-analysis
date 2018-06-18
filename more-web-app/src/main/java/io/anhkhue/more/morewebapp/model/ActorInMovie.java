package io.anhkhue.more.morewebapp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "actor_in_movie", schema = "more_db", catalog = "")
@IdClass(ActorInMoviePK.class)
public class ActorInMovie {

    private int actorId;
    private int movieId;

    @Id
    @Column(name = "actor_id")
    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    @Id
    @Column(name = "movie_id")
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
        ActorInMovie that = (ActorInMovie) o;
        return actorId == that.actorId &&
               movieId == that.movieId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId, movieId);
    }
}
