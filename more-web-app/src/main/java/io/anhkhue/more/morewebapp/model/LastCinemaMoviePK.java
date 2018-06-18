package io.anhkhue.more.morewebapp.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class LastCinemaMoviePK implements Serializable {

    private String title;
    private String cinemaName;

    @Column(name = "title")
    @Id
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "cinema_name")
    @Id
    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastCinemaMoviePK that = (LastCinemaMoviePK) o;
        return Objects.equals(title, that.title) &&
               Objects.equals(cinemaName, that.cinemaName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, cinemaName);
    }
}
