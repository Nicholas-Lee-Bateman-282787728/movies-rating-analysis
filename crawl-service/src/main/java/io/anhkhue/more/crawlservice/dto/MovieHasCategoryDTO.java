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
@Table(name = "movie_has_category", schema = "more_db")
public class MovieHasCategoryDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "movie_id")
    private int movieId;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id",
            nullable = false, insertable = false, updatable = false)
    private MovieDTO movie;

    @Column(name = "category_name")
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "category_name", referencedColumnName = "category_name",
            nullable = false, insertable = false, updatable = false)
    private CategoryDTO category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieHasCategoryDTO that = (MovieHasCategoryDTO) o;
        return id == that.id &&
               movieId == that.movieId &&
               Objects.equals(categoryName, that.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movieId, categoryName);
    }
}
