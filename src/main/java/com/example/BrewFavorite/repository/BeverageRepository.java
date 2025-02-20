package com.example.BrewFavorite.repository;

import com.example.BrewFavorite.model.BeverageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeverageRepository extends JpaRepository<BeverageEntity, Long> {
}
