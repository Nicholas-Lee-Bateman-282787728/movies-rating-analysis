package io.anhkhue.akphim.models.mining.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

// Lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// JAXB
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Movies", propOrder = {
        "movie"
})
public class Movies {

    @XmlElement(name = "movie", required = true)
    protected List<Movie> movie;

    public List<Movie> getMovie() {
        if (movie == null) {
            movie = new ArrayList<>();
        }
        return this.movie;
    }

}
