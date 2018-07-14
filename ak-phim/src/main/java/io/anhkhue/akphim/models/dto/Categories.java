
package io.anhkhue.akphim.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Categories", propOrder = {
    "category"
})
public class Categories {

    @XmlElement(required = true)
    private List<Category> category;

    public List<Category> getCategory() {
        if (category == null) {
            category = new ArrayList<>();
        }
        return this.category;
    }
}
