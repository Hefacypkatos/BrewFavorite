package com.example.BrewFavorite.service;

import com.example.BrewFavorite.repository.BeverageRepository;
import com.example.BrewFavorite.exception.ResourceNotFoundException;
import com.example.BrewFavorite.model.BeverageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeverageService {

    @Autowired
    BeverageRepository beverageRepository;

    public List<BeverageEntity> getAllBeverages() throws ResourceNotFoundException  {
        List<BeverageEntity> beverages = beverageRepository.findAll();
        if (beverages.isEmpty()) {  throw new ResourceNotFoundException("No Beverages found");  }
        return beverages;
    }

    public BeverageEntity createBeverage(BeverageEntity beverage){
        return beverageRepository.save(beverage);
    }

    public BeverageEntity getBeverageByID(long id) throws ResourceNotFoundException {
        return beverageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Beverage not found with ID:" + id));
    }

    public void deleteBeverageByID(long id) throws ResourceNotFoundException {
        if (!beverageRepository.existsById(id)) { throw new ResourceNotFoundException("Beverage not found with ID: " + id); }
        else beverageRepository.deleteById(id);
    }


}
