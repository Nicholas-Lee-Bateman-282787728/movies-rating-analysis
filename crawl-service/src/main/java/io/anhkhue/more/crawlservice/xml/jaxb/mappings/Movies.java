
package io.anhkhue.more.crawlservice.xml.jaxb.mappings;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "movie"
})
@XmlRootElement(name = "movies")
public class Movies {

    @XmlElement(required = true)
    protected List<Movie> movie;

    public List<Movie> getMovie() {
        if (movie == null) {
            movie = new ArrayList<>();
        }
        return this.movie;
    }

}
