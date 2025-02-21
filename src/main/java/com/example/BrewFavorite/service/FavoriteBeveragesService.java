
package com.example.BrewFavorite.service;

import com.example.BrewFavorite.exception.DuplicateFoundException;
import com.example.BrewFavorite.exception.ResourceNotFoundException;
import com.example.BrewFavorite.model.BeverageEntity;
import com.example.BrewFavorite.model.FavoriteBeveragesEntity;
import com.example.BrewFavorite.repository.FavoriteBeveragesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteBeveragesService {

    @Autowired
    FavoriteBeveragesRepository favoriteBeveragesRepository;
    @Autowired
    BeverageService beverageService;
    @Autowired
    UserService userService;


    public List<BeverageEntity> getUsersFavoriteBeverages(long id) {
        return userService.getUserByID(id).getFavoriteBeverages().getBeverages();
    }

    public FavoriteBeveragesEntity addBeverageToUserFavoriteBeverages(long userId, long beverageId) {
      FavoriteBeveragesEntity favoriteBeverages = userService.getUserByID(userId).getFavoriteBeverages();
        BeverageEntity beverage = beverageService.getBeverageByID(beverageId);
        if(!favoriteBeverages.getBeverages().contains(beverage)) {
            favoriteBeverages.getBeverages().add(beverage);
        } else throw new DuplicateFoundException(String.format("BeverageId: %s already exist in UserId: %s favorite lsit", beverageId,userId));
        return favoriteBeveragesRepository.save(favoriteBeverages);

    }

    public FavoriteBeveragesEntity removeBeverageFromUserFavoriteBeverages(long userId, long beverageId) {
        FavoriteBeveragesEntity favoriteBeverages = userService.getUserByID(userId).getFavoriteBeverages();
        BeverageEntity beverage = beverageService.getBeverageByID(beverageId);
        if(favoriteBeverages.getBeverages().contains(beverage)) {
            favoriteBeverages.getBeverages().remove(beverage);
        } else throw new ResourceNotFoundException(String.format("BeverageId: %s not found on UserId: %s favorites list", beverageId,userId));
        return favoriteBeveragesRepository.save(favoriteBeverages);
    }





}
