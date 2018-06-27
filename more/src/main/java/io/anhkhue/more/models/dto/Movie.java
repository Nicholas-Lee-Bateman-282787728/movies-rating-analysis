
package io.anhkhue.more.models.dto;

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
// JAXB
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Movie", propOrder = {
        "id",
        "title",
        "year",
        "onCinema",
        "isComing",
        "rating",
        "poster",
        "view",
        "links",
        "director",
        "actors",
        "categories"
})
@XmlRootElement(name = "movie")
// JPA
@Entity
@Table(name = "movie", schema = "more_db")
public class Movie {

    // JPA
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    // JAXB
    @XmlElement(name = "id", required = true)
    private int id;

    // JPA
    @Column(name = "title")
    // JAXB
    @XmlElement(name = "title", required = true)
    private String title;

    // JPA
    @Column(name = "year")
    private BigInteger year;

    // JPA
    @Column(name = "director")
    // JAXB
    @XmlElement(required = true)
    private String director;

    @XmlTransient
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
    // JAXB
    @XmlElement(name = "poster", required = true)
    @XmlSchemaType(name = "anyURI")
    private String poster;

    // JPA
    @Column(name = "view")
    // JAXB
    @XmlElement(required = true)
    private int view;

    @XmlElement(required = true)
    @Transient
    private Links links;

    @XmlElement(required = true)
    @Transient
    private Actors actors;

    @XmlElement(required = true)
    @Transient
    private Categories categories;

    @Override
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
