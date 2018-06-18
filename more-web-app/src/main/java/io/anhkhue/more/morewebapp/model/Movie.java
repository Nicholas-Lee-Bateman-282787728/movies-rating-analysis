package io.anhkhue.more.morewebapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

// JPA
@Entity
// Lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// JAXB
@XmlAccessorType(XmlAccessType.FIELD)
public class Movie {

    @Id
    @Column(name = "id")
    @XmlElement(name = "id", required = true)
    private int id;

    @Column(name = "title")
    @XmlElement(name = "title", required = true)
    private String title;

    @Column(name = "imported_date")
    @XmlElement(name = "imported_date", required = true)
    private long importedDate;

    @Column(name = "on_cinema")
    @XmlElement(name = "on_cinema", required = true)
    private boolean onCinema;

    @Column(name = "is_coming")
    @XmlElement(name = "is_coming", required = true)
    private boolean isComing;

    @Column(name = "rating")
    @XmlElement(name = "rating", required = true)
    private double rating;

    @Column(name = "poster")
    @XmlElement(name = "poster", required = true)
    private String poster;

    @Column(name = "view")
    @XmlElement(name = "view", required = true)
    private int view;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
               importedDate == movie.importedDate &&
               onCinema == movie.onCinema &&
               isComing == movie.isComing &&
               Double.compare(movie.rating, rating) == 0 &&
               view == movie.view &&
               Objects.equals(title, movie.title) &&
               Objects.equals(poster, movie.poster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, importedDate, onCinema, isComing, rating, poster, view);
    }
}
