package io.anhkhue.more.models.mining.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

// Lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// JAXB
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "vendor"
})
@XmlRootElement(name = "vendors")
public class Vendors {

    @XmlElement(name = "vendor", required = true)
    protected List<Vendor> vendor;

    public List<Vendor> getVendor() {
        if (vendor == null) {
            vendor = new ArrayList<>();
        }
        return this.vendor;
    }
}
