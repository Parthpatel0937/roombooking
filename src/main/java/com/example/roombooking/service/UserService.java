package com.example.roombooking.service;

import com.example.roombooking.entity.AppUser;

public interface UserService {
    AppUser findByUsername(String username);
    AppUser createUser(AppUser appUser);

}
