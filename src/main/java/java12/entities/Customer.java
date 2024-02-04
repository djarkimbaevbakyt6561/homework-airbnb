package java12.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java12.enums.FamilyStatus;
import java12.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "base_gen", sequenceName = "customer_seq", allocationSize = 1)
public class Customer extends BaseEntity{
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    @Enumerated
    private Gender gender;
    private String nationality;
    @Enumerated
    private FamilyStatus familyStatus;
}
