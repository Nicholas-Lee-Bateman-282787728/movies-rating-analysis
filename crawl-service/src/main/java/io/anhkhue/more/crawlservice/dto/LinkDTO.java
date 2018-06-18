package io.anhkhue.more.crawlservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

// Lombok
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
// JPA
@Entity
@Table(name = "link", schema = "more_db")
public class LinkDTO {

    @Id
    @Column(name = "url")
    private String url;

    @Column(name = "source")
    private String source;

    @Column(name = "is_cinema")
    private boolean isCinema;

    @Column(name = "movie_id")
    private int movieId;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id",
            nullable = false, insertable = false, updatable = false)
    private MovieDTO movie;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkDTO linkDTO = (LinkDTO) o;
        return isCinema == linkDTO.isCinema &&
               movieId == linkDTO.movieId &&
               Objects.equals(url, linkDTO.url) &&
               Objects.equals(source, linkDTO.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, source, isCinema, movieId);
    }
}
