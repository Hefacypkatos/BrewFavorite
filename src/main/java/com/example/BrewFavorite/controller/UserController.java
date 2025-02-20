package com.example.BrewFavorite.controller;

import com.example.BrewFavorite.exception.ResourceNotFoundException;
import com.example.BrewFavorite.model.BeverageEntity;
import com.example.BrewFavorite.model.UserEntity;
import com.example.BrewFavorite.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers()   {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserByID(@PathVariable long id) {
        UserEntity user = userService.getUserByID(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody @Valid UserEntity user)  {
        UserEntity createdUser = userService.createUser(user);
        createdUser.getFavoriteBeverages();
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserByID(@PathVariable long id)   {
        userService.deleteUserByID(id);
        return ResponseEntity.noContent().build();

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleResourceNotFound(ResourceNotFoundException e)   {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

}
