package io.anhkhue.akphim.models.mining.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

// Lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// JAXB
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Vendor", propOrder = {
        "name",
        "image",
        "address1",
        "address2",
        "tel",
        "email"
})
// JPA
@Entity
@Table(name = "vendor", schema = "more_db")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @XmlTransient
    private int id;

    @Column(name = "name")
    @XmlElement(name = "name", required = true)
    private String name;

    @Column(name = "image")
    @XmlElement(name = "image", required = true)
    private String image;

    @Column(name = "address1")
    @XmlElement(name = "address1", required = true)
    private String address1;

    @Column(name = "address2")
    @XmlElement(name = "address2", required = true)
    private String address2;

    @Column(name = "tel")
    @XmlElement(name = "tel", required = true)
    private String tel;

    @Column(name = "email")
    @XmlElement(name = "email", required = true)
    private String email;
}
