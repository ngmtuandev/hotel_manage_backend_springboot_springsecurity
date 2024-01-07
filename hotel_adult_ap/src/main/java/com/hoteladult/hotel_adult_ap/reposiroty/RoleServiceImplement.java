package com.hoteladult.hotel_adult_ap.reposiroty;

import com.hoteladult.hotel_adult_ap.exception.RoleAlreadyExistException;
import com.hoteladult.hotel_adult_ap.exception.UserAlreadyExistsException;
import com.hoteladult.hotel_adult_ap.model.Role;
import com.hoteladult.hotel_adult_ap.model.User;
import com.hoteladult.hotel_adult_ap.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImplement implements IRoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createNewRole(Role theRoleNew) {
        String roleName = theRoleNew.getName().toUpperCase();
        Role newRoleCreate = new Role(roleName);
        if (roleRepository.existsByName(roleName)) {
            throw new RoleAlreadyExistException(theRoleNew.getName() + "role already exist");
        }
        return roleRepository.save(newRoleCreate);
    }

    @Override
    public String deleteRole(Long roleId) {
        roleRepository.deleteById(roleId);
        return "remove role successfully";
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).get();
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent() && role.get().getUsers().contains(user.get())){
            role.get().removeUserFromRole(user.get());
            // save new data table role
            roleRepository.save(role.get());
            return user.get();
        }
        throw new UsernameNotFoundException("User not exist");
    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (user.isPresent() && user.get().getRoles().contains(role.get())) {
            throw new UserAlreadyExistsException(
                    user.get().getEmail() + "this user already exist role" + role.get().getName()
            );
        }
        if (role.isPresent()) {
            // phuong thuc role tu method model
            role.get().assignRoleToken(user.get());
            // luu role voi user moi addd vao role
            roleRepository.save(role.get());
        }
        return user.get();
    }

    // delete all
    @Override
    public Role removeAllUserFromRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        role.ifPresent(Role::removeAllUsersFromRole);
        return roleRepository.save(role.get());
    }
}
