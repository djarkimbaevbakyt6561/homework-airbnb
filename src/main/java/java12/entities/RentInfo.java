package java12.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "rent_info")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "base_gen", sequenceName = "rent_info_seq", allocationSize = 1)
public class RentInfo extends BaseEntity {
    private LocalDate checkIn;
    private LocalDate checkOut;
    @OneToOne
    private House house;
    @ManyToOne
    @JoinTable(
            name = "rent_info_owner",
            joinColumns = @JoinColumn(name = "owner_id"),
            inverseJoinColumns = @JoinColumn(name = "rent_info_id")
    )
    private Owner owner;
    @ManyToOne
    private Customer customer;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Agency agency;

    public RentInfo(LocalDate checkIn, LocalDate checkOut) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
