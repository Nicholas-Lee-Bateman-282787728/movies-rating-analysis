
package io.anhkhue.akphim.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

// Lombok
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
// JAXB
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Category")
// JPA
@Entity
@Table(name = "categoryName", schema = "more_db")
public class Category {

    // JPA
    @Id
    @Column(name = "category_name")
    // JAXB
    @XmlElement(name = "categoryName")
    private String categoryName;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Category that = (Category) obj;
        return Objects.equals(categoryName, that.getCategoryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName);
    }
}