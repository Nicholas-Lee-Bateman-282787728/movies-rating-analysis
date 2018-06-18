package io.anhkhue.more.morewebapp.movie;

import io.anhkhue.more.morewebapp.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

// Lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
// JAXB
@XmlRootElement(name = "movies")
@XmlAccessorType(XmlAccessType.FIELD)
public class Movies {

    @XmlElement(name = "movie", required = true)
    List<Movie> movies;
}
