package io.anhkhue.more.crawlservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

// Lombok
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
// JPA
@Entity
@Table(name = "movie", schema = "more_db")
public class MovieDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "year")
    private String year;

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

    @Column(name = "poster")
    private String poster;

    @Column(name = "view")
    private int view;

    @Transient
    private Collection<AccountRateMovieDTO> accountRateMovies;

    @Transient
    private Collection<LinkDTO> links;

    @Transient
    private Collection<CategoryDTO> categories;

    @Transient
    private Collection<ActorDTO> actors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDTO movieDTO = (MovieDTO) o;
        return id == movieDTO.id &&
               importedDate == movieDTO.importedDate &&
               onCinema == movieDTO.onCinema &&
               isComing == movieDTO.isComing &&
               Double.compare(movieDTO.rating, rating) == 0 &&
               view == movieDTO.view &&
               Objects.equals(title, movieDTO.title) &&
               Objects.equals(year, movieDTO.year) &&
               Objects.equals(director, movieDTO.director) &&
               Objects.equals(poster, movieDTO.poster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, year, director, importedDate, onCinema, isComing, rating, poster, view);
    }
}
