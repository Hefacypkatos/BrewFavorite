package com.example.BrewFavorite.service;

import com.example.BrewFavorite.exception.ResourceNotFoundException;
import com.example.BrewFavorite.model.BeverageEntity;
import com.example.BrewFavorite.model.FavoriteBeveragesEntity;
import com.example.BrewFavorite.model.UserEntity;
import com.example.BrewFavorite.repository.FavoriteBeveragesRepository;
import com.example.BrewFavorite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FavoriteBeveragesRepository favoriteBeveragesRepository;

    public List<UserEntity> getAllUsers() throws ResourceNotFoundException {
        List <UserEntity> users =  userRepository.findAll();
        if (users.isEmpty()) { throw new ResourceNotFoundException("No Users found"); }
        return users;
    }

    public UserEntity createUser (UserEntity user)  {
        UserEntity savedUser=  userRepository.save(user);
        FavoriteBeveragesEntity favoriteBeverages = new FavoriteBeveragesEntity();
        favoriteBeverages.setUser(savedUser);
        favoriteBeveragesRepository.save(favoriteBeverages);
        savedUser.setFavoriteBeverages(favoriteBeverages);
        return userRepository.save(savedUser);
    }

    public UserEntity getUserByID (long id) throws ResourceNotFoundException    {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with ID:" + id));
    }

    public void deleteUserByID (long id) throws  ResourceNotFoundException {
        if (!userRepository.existsById(id)) { throw new ResourceNotFoundException("User not found with ID:" + id); }
        else userRepository.deleteById(id);
    }

}
