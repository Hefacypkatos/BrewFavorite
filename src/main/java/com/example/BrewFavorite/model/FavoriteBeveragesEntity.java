
package com.example.BrewFavorite.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "favorite_beverages")

public class FavoriteBeveragesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonBackReference
    private UserEntity user;

    @ManyToMany
    @JoinTable(name ="favorite_beverage_beverages", joinColumns = @JoinColumn(name= "favorite_beverage_id"), inverseJoinColumns = @JoinColumn(name = "beverage_id"))
    private List<BeverageEntity> beverages = new ArrayList<>();
}
