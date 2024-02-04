package java12.entities;

import jakarta.persistence.*;
import java12.enums.FamilyStatus;
import java12.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String nationality;
    @Enumerated(EnumType.STRING)
    private FamilyStatus familyStatus;

    @OneToMany(mappedBy = "customer")
    private List<RentInfo> rentInfos;

    public Customer(String firstName, String lastName, String email, LocalDate dateOfBirth, Gender gender, String nationality, FamilyStatus familyStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        this.familyStatus = familyStatus;
    }
}
