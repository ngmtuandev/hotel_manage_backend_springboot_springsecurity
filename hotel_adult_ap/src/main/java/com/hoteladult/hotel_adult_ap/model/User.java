package com.hoteladult.hotel_adult_ap.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_entity")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;

}
