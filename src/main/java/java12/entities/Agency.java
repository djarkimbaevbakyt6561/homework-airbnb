package java12.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "agencies")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "base_gen", sequenceName = "agency_seq", allocationSize = 1)
public class Agency  extends BaseEntity{
    private String name;
    @Column(name = "phone_number", length = 13)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    @ManyToMany
    @JoinTable(
            name = "owner_agency",
            joinColumns = @JoinColumn(name = "agency_id"),
            inverseJoinColumns = @JoinColumn(name = "owner_id")
    )
    private List<Owner> owners;

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.startsWith("+996") && phoneNumber.length() >= 13) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Invalid phone number format.");
        }
    }

    public Agency(String name) {
        this.name = name;
    }
}
