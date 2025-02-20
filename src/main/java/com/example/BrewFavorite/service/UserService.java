package com.example.BrewFavorite.service;

import com.example.BrewFavorite.exception.ResourceNotFoundException;
import com.example.BrewFavorite.model.UserEntity;
import com.example.BrewFavorite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public List<UserEntity> getAllUsers() throws ResourceNotFoundException {
        List <UserEntity> users =  userRepository.findAll();
        if (users.isEmpty()) { throw new ResourceNotFoundException("No Users found"); }
        return users;
    }

    public UserEntity createUser (UserEntity user)  {
        return userRepository.save(user);
    }

    public UserEntity getUserByID (long id) throws ResourceNotFoundException    {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with ID:" + id));
    }

    public void deleteUserByID (long id) throws  ResourceNotFoundException {
        if (!userRepository.existsById(id)) { throw new ResourceNotFoundException("User not found with ID:" + id); }
        else userRepository.deleteById(id);
    }

}
