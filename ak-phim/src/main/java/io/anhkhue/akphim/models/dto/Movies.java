
package io.anhkhue.akphim.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

// Lombok
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
// JAXB
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "movie"
})
@XmlRootElement(name = "movies")
public class Movies {

    @XmlElement(name = "movie", required = true)
    private List<Movie> movie;

    public List<Movie> getMovie() {
        if (movie == null) {
            movie = new ArrayList<>();
        }
        return this.movie;
    }
}
