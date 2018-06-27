
package io.anhkhue.more.models.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

// Lombok
@NoArgsConstructor
@AllArgsConstructor
// JAXB
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Links", propOrder = {
        "link"
})
public class Links {

    @XmlElement(required = true)
    protected List<Link> link;

    public List<Link> getLink() {
        if (link == null) {
            link = new ArrayList<>();
        }
        return this.link;
    }
}
