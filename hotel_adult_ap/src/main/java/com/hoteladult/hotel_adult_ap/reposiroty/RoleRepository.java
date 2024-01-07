package com.hoteladult.hotel_adult_ap.reposiroty;

import com.hoteladult.hotel_adult_ap.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    boolean existsByName(String name);

}
