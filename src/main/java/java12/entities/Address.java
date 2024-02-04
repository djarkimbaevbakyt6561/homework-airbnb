package java12.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "base_gen", sequenceName = "address_seq", allocationSize = 1)
public class Address  extends BaseEntity{
    private String city;
    private String region;
    @Column(name = "street", unique = true)
    private String street;
    @OneToOne(mappedBy = "address")
    private Agency agency;

    public Address(String city, String region, String street) {
        this.city = city;
        this.region = region;
        this.street = street;
    }
}
