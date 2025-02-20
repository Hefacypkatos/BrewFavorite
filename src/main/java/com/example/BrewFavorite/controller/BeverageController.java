package com.example.BrewFavorite.controller;


import com.example.BrewFavorite.service.BeverageService;
import com.example.BrewFavorite.exception.ResourceNotFoundException;
import com.example.BrewFavorite.model.BeverageEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/beverages")
public class BeverageController {

    @Autowired
    BeverageService beverageService;

    @GetMapping
    public ResponseEntity<List<BeverageEntity>> getAllBeverages()   {
        List<BeverageEntity> beverages = beverageService.getAllBeverages();
        return ResponseEntity.ok(beverages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeverageEntity> getBeverageByID(@PathVariable long id)    {
        BeverageEntity beverage = beverageService.getBeverageByID(id);
        return ResponseEntity.ok(beverage);
    }

    @PostMapping
    public ResponseEntity<BeverageEntity> createBeverage(@RequestBody @Valid BeverageEntity beverage)  {
        BeverageEntity createdBeverage = beverageService.createBeverage(beverage);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBeverage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeverageByID(@PathVariable long id) {
        beverageService.deleteBeverageByID(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleResourceNotFound(ResourceNotFoundException e)   {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }
}