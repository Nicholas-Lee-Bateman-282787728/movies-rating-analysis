
package io.anhkhue.more.crawlservice.xml.jaxb.mappings;

import io.anhkhue.more.crawlservice.dto.LinkDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

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
public class Link {

    @XmlElement(required = true)
    private String url;
    @XmlElement(required = true)
    private String source;
    private boolean isCinema;

    public Link(LinkDTO linkDTO) {
        this.url = linkDTO.getUrl();
        this.source = linkDTO.getSource();
        this.isCinema = linkDTO.isCinema();
    }
}
