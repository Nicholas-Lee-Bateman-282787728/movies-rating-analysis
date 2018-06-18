package io.anhkhue.more.crawlservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

// Lombok
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
// JPA
@Entity
@Table(name = "actor", schema = "more_db")
public class ActorDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender")
    private boolean gender;

    @Transient
    private Collection<MovieDTO> movies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorDTO actorDTO = (ActorDTO) o;
        return id == actorDTO.id &&
               gender == actorDTO.gender &&
               Objects.equals(fullName, actorDTO.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, gender);
    }
}
