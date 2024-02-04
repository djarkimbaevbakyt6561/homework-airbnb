package java12.entities;

import jakarta.persistence.*;
import java12.enums.HouseType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "houses")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "base_gen", sequenceName = "house_seq", allocationSize = 1)
public class House extends BaseEntity{

    private BigDecimal price;
    private double rating;
    private String description;
    private int room;
    private boolean furniture;
    @Enumerated(EnumType.STRING)
    @Column(name = "house_type")
    private HouseType houseType;
    @ManyToOne
    private Owner owner;
    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Address address;

    public House(HouseType houseType, BigDecimal price, double rating, String description, int room, boolean furniture) {
        this.houseType = houseType;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.room = room;
        this.furniture = furniture;
    }
}
