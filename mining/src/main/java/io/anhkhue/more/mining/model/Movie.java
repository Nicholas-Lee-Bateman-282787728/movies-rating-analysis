
package io.anhkhue.more.mining.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.math.BigInteger;
import java.util.Objects;

// Lombok
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
// JPA
@Entity
@Table(name = "movie", schema = "more_db")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // JPA
    @Column(name = "title")
    private String title;

    // JPA
    @Column(name = "year")
    private BigInteger year;

    // JPA
    @Column(name = "director")
    private String director;

    @Column(name = "imported_date")
    private long importedDate;

    @Column(name = "on_cinema")
    private boolean onCinema;

    @Column(name = "is_coming")
    private boolean isComing;

    @Column(name = "rating")
    private double rating;

    // JPA
    @Column(name = "poster")
    private String poster;

    // JPA
    @Column(name = "view")
    private int view;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movieDTO = (Movie) o;
        return Objects.equals(title, movieDTO.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
