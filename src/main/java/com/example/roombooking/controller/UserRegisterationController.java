package com.example.roombooking.controller;

import com.example.roombooking.entity.AppUser;
import com.example.roombooking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserRegisterationController {

    private final UserService userService;

    public UserRegisterationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> userRegistraion(@RequestBody AppUser appUser){
        AppUser saved;
        try {
            saved = userService.createUser(appUser);

        }catch (Exception ex) {
            return new ResponseEntity<>("The User is Duplicate",HttpStatus.BAD_REQUEST);
        }
       // return new ResponseEntity<>("The User is registered",HttpStatus.OK);
        return ResponseEntity.status(500).body(saved);

    }
}
