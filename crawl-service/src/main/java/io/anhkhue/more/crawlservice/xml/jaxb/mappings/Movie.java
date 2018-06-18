
package io.anhkhue.more.crawlservice.xml.jaxb.mappings;

import io.anhkhue.more.crawlservice.dto.MovieDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;

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
public class Movie {

    @XmlElement(required = true)
    private String id;

    @XmlElement(required = true)
    private String title;

    private boolean onCinema;

    private boolean isComing;

    private double rating;

    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    private String poster;

    @XmlElement(required = true)
    private BigInteger view;

    @XmlElement(required = true)
    private Links links;

    @XmlElement(required = true)
    private String director;

    @XmlElement(required = true)
    private Actors actors;

    @XmlElement(required = true)
    private Categories categories;

    public Movie(MovieDTO movieDTO) {
        this.id = String.valueOf(movieDTO.getId());
        this.title = movieDTO.getTitle();
        this.onCinema = movieDTO.isOnCinema();
        this.rating = movieDTO.getRating();
        this.poster = movieDTO.getPoster();
        this.view = BigInteger.valueOf(movieDTO.getView());
        this.director = movieDTO.getDirector();
        // Links
        this.links = new Links();
        movieDTO.getLinks().forEach(linkDTO -> {
            Link link = new Link(linkDTO);
            this.links.getLink().add(link);
        });
        // Actors
        this.actors = new Actors();
        movieDTO.getActors().forEach(actorDTO -> {
            String actor = actorDTO.getFullName();
            this.actors.getActor().add(actor);
        });
        // Categories
        this.categories = new Categories();
        movieDTO.getCategories().forEach(categoryDTO -> {
            Category category = Category.fromValue(categoryDTO.getCategoryName());
            this.categories.getCategory().add(category);
        });
    }
}
