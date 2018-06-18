package io.anhkhue.more.morewebapp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "director_has_movie", schema = "more_db", catalog = "")
@IdClass(DirectorHasMoviePK.class)
public class DirectorHasMovie {

    private int directorId;
    private int movieId;

    @Id
    @Column(name = "director_id")
    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
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
        DirectorHasMovie that = (DirectorHasMovie) o;
        return directorId == that.directorId &&
               movieId == that.movieId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(directorId, movieId);
    }
}
