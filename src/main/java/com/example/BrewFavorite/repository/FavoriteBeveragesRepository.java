
package com.example.BrewFavorite.repository;

import com.example.BrewFavorite.model.FavoriteBeveragesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteBeveragesRepository extends JpaRepository<FavoriteBeveragesEntity, Long> {

}
