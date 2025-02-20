
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


    public List<BeverageEntity> getUsersFavoriteBeverages(long id) {
        FavoriteBeveragesEntity favoriteBeverages =  favoriteBeveragesRepository.findByUserID(id); //Do wyjebania , prosciej bedzie z UserService
        return favoriteBeverages.getBeverages();
    }

    public FavoriteBeveragesEntity addBeverageToUserFavoriteBeverages(long userId, long beverageId) {
        FavoriteBeveragesEntity favoriteBeverages =  favoriteBeveragesRepository.findByUserID(userId);
        BeverageEntity beverage = beverageService.getBeverageByID(beverageId);
        if(!favoriteBeverages.getBeverages().contains(beverage)) {
            favoriteBeverages.getBeverages().add(beverage);
        } else throw new DuplicateFoundException (String.format("Duplicate found! UserID: %s BeverageID: %s", userId,beverageId));
        return favoriteBeveragesRepository.save(favoriteBeverages);

    }

    public FavoriteBeveragesEntity removeBeverageFromUserFavoriteBeverages(long userId, long beverageId) {
        FavoriteBeveragesEntity favoriteBeverages = favoriteBeveragesRepository.findByUserID(userId);
        BeverageEntity beverage = beverageService.getBeverageByID(beverageId);
        if(favoriteBeverages.getBeverages().contains(beverage)) {
            favoriteBeverages.getBeverages().remove(beverage);
        } else throw new ResourceNotFoundException(String.format("BeverageId: %s not found on UserId: %s favorites list", beverageId,userId));
        return favoriteBeveragesRepository.save(favoriteBeverages);
    }





}
