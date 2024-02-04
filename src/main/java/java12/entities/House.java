package java12.entities;

import jakarta.persistence.*;
import java12.enums.HouseType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "houses")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "base_gen", sequenceName = "house_seq", allocationSize = 1)
public class House extends BaseEntity{
    @Enumerated
    private HouseType houseType;
    private BigDecimal price;
    private double rating;
    private String description;
    private int room;
    private boolean furniture;
    @OneToOne
    private Address address;
}
