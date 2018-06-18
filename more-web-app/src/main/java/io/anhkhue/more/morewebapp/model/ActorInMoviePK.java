package io.anhkhue.more.morewebapp.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ActorInMoviePK implements Serializable {

    private int actorId;
    private int movieId;

    @Column(name = "actor_id")
    @Id
    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
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
        ActorInMoviePK that = (ActorInMoviePK) o;
        return actorId == that.actorId &&
               movieId == that.movieId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId, movieId);
    }
}
