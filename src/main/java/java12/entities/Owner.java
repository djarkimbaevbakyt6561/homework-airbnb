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
    @Column(name = "email", unique = true)
    private String email;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @ManyToMany(mappedBy = "owners")
    private List<Agency> agencies;
    @OneToMany(mappedBy = "owner")
    private List<House> houses;
    @OneToMany(mappedBy = "owner")
    private List<RentInfo> rentInfos;
    public boolean isAtLeast18YearsOld() {
        LocalDate minimumBirthDate = LocalDate.now().minusYears(18);
        return dateOfBirth.isBefore(minimumBirthDate);
    }

    public Owner(String firstName, String lastName, String email, LocalDate dateOfBirth, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }
}
