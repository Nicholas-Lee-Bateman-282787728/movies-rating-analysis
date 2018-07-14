
package io.anhkhue.akphim.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Actors", propOrder = {
    "actor"
})
public class Actors {

    @XmlElement(name = "actor", required = true)
    private List<String> actor;

    public List<String> getActor() {
        if (actor == null) {
            actor = new ArrayList<>();
        }
        return this.actor;
    }

}
