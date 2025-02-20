package com.example.BrewFavorite.model;


import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "beverages")

public class BeverageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    @NotNull
    private String name;

    @Column(name = "type")
    private String beverageType;

    @Column(name = "alcohol_volume")
    private double alcoholVol;

    @Column(name = "manufacture_date")
    private int manufactureDate;

    @ManyToMany(mappedBy = "beverages")
    private List<FavoriteBeveragesEntity> favoriteBeverages;


}