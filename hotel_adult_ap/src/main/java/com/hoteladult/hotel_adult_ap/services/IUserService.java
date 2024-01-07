package com.hoteladult.hotel_adult_ap.services;

import com.hoteladult.hotel_adult_ap.model.User;

import java.util.List;

public interface IUserService {

    User registerUser(User user);
    List<User> getListUser();
    String deleteUser(String email);
    User getOneUser(String email);

}
