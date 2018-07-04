package io.anhkhue.more.models.mining.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;

// Lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// JAXB
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Movie", propOrder = {
        "title",
        "director",
        "year"
})
public class Movie {

    @XmlElement(name = "title", required = true)
    private String title;
    @XmlElement(name = "director", required = true)
    private String director;
    @XmlElement(name = "year", required = true)
    private BigInteger year;
}
