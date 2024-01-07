package com.hoteladult.hotel_adult_ap.controller;

import com.hoteladult.hotel_adult_ap.exception.UserAlreadyExistsException;
import com.hoteladult.hotel_adult_ap.model.ResultModel;
import com.hoteladult.hotel_adult_ap.model.User;
import com.hoteladult.hotel_adult_ap.services.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserServiceImplement userServiceImplement;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User userRegisted = userServiceImplement.registerUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResultModel("ok", "register successfully", userRegisted)
            );
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
