package io.anhkhue.more.morewebapp.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Link {

    private String url;
    private String source;
    private boolean isCinema;
    private int movieId;

    @Id
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "is_cinema")
    public boolean isCinema() {
        return isCinema;
    }

    public void setCinema(boolean cinema) {
        isCinema = cinema;
    }

    @Basic
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
        Link link = (Link) o;
        return isCinema == link.isCinema &&
               movieId == link.movieId &&
               Objects.equals(url, link.url) &&
               Objects.equals(source, link.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, source, isCinema, movieId);
    }
}
