package io.anhkhue.akphim.models.mining;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

// Lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// JAXB
@XmlRootElement(name = "highVote")
public class HighVote {

    private double percentage;
    private int totalVote;
}
