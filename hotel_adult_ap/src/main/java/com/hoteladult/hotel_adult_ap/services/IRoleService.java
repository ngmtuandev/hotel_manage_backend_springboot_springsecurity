package com.hoteladult.hotel_adult_ap.services;

import com.hoteladult.hotel_adult_ap.model.Role;
import com.hoteladult.hotel_adult_ap.model.User;
import com.hoteladult.hotel_adult_ap.reposiroty.RoleRepository;

import java.util.List;

public interface IRoleService {

    List<Role> getAllRoles();
    Role createNewRole(Role theRoleNew);
    String deleteRole(Long roleId);
    Role findByName(String name);

    User removeUserFromRole(Long userId, Long roleId);
    User assignRoleToUser(Long userId, Long roleId);
    Role removeAllUserFromRole(Long roleId);

}
