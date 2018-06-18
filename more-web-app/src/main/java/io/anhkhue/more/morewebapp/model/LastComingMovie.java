package io.anhkhue.more.morewebapp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "last_coming_movie", schema = "more_db", catalog = "")
@IdClass(LastComingMoviePK.class)
public class LastComingMovie {

    private String title;
    private String cinemaName;

    @Id
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Id
    @Column(name = "cinema_name")
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
        LastComingMovie that = (LastComingMovie) o;
        return Objects.equals(title, that.title) &&
               Objects.equals(cinemaName, that.cinemaName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, cinemaName);
    }
}
