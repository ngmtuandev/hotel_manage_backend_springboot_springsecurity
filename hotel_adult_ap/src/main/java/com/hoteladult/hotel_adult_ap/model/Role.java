package com.hoteladult.hotel_adult_ap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<User>  users = new HashSet<>();

//    ------------- METHOD
//  ====== TAO SAN METHOD DE XU LY ======

    public void assignRoleToken(User user) {
        user.getRoles().add(this);
        this.getUsers().add(user);
    }

    public void removeUserFromRole(User user) {
        user.getRoles().remove(this);
        this.getUsers().remove(user);
    }

    public void removeAllUsersFromRole() {
        if (this.getUsers() != null) {
            List<User> roleUsers = this.getUsers().stream().toList();
            roleUsers.forEach(this :: removeUserFromRole);
        // <-> roleUsers.forEach(user -> this.removeUserFromRole(user));
        }
    }

//    ----------

    public Role(String name) {
        this.name = name;
    }



    public Role() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
