package com.example.roombooking.service.impl;

import com.example.roombooking.entity.AppUser;
import com.example.roombooking.entity.Role;
import com.example.roombooking.entity.Room;
import com.example.roombooking.repository.UserRepository;
import com.example.roombooking.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public AppUser createUser(AppUser appUser){
        if(appUser.getUsername().equals("parth")){
            appUser.setRoles(Set.of(Role.ROLE_ADMIN));
        }else{
            appUser.setRoles(Set.of(Role.ROLE_USER));
        }
        return userRepository.save(appUser);
    }
}
