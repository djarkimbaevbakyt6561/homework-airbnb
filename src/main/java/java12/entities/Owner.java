package java12.entities;

import jakarta.persistence.*;
import java12.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "owners")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "base_gen", sequenceName = "owner_seq", allocationSize = 1)
public class Owner  extends BaseEntity{
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    @Enumerated
    private Gender gender;
    @ManyToMany(mappedBy = "owners")
    private List<Agency> agencies;
}
