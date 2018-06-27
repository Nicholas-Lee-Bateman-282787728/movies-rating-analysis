
package io.anhkhue.more.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Objects;

// Lombok
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
// JAXB
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Link", propOrder = {
        "url",
        "source",
        "isCinema"
})
// JPA
@Entity
@Table(name = "link", schema = "more_db")
public class Link {

    @Id
    @Column(name = "url")
    @XmlElement(required = true)
    private String url;

    @Column(name = "source")
    @XmlElement(required = true)
    private String source;

    @Column(name = "is_cinema")
    private boolean isCinema;

    @XmlTransient
    @Column(name = "movie_id")
    private int movieId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Link linkDTO = (Link) obj;
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
