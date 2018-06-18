package io.anhkhue.more.morewebapp.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class DirectorHasMoviePK implements Serializable {

    private int directorId;
    private int movieId;

    @Column(name = "director_id")
    @Id
    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
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
        DirectorHasMoviePK that = (DirectorHasMoviePK) o;
        return directorId == that.directorId &&
               movieId == that.movieId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(directorId, movieId);
    }
}
