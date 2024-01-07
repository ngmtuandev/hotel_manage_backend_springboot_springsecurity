package com.hoteladult.hotel_adult_ap.services;

import com.hoteladult.hotel_adult_ap.exception.UserAlreadyExistsException;
import com.hoteladult.hotel_adult_ap.model.Role;
import com.hoteladult.hotel_adult_ap.model.User;
import com.hoteladult.hotel_adult_ap.reposiroty.RoleRepository;
import com.hoteladult.hotel_adult_ap.reposiroty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImplement implements IUserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    // need config passwordEncoder in webSecurityConfig

    @Autowired
    RoleRepository roleRepository;


    @Override
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail() + "already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role_user = roleRepository.findByName("USER").get();

        // tao 1 set chi chua 1 phan tu -> set Role for User
        user.setRoles(Collections.singleton(role_user));

        return userRepository.save(user);

    }

    @Override
    public List<User> getListUser() {
        return userRepository.findAll();
    }

    @Override
    public String deleteUser(String email) {
        return null;
    }

    @Override
    public User getOneUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserAlreadyExistsException("user not founf"));
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
