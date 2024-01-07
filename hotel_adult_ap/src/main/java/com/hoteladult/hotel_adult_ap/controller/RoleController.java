package com.hoteladult.hotel_adult_ap.controller;

import com.hoteladult.hotel_adult_ap.exception.RoleAlreadyExistException;
import com.hoteladult.hotel_adult_ap.model.ResultModel;
import com.hoteladult.hotel_adult_ap.model.Role;
import com.hoteladult.hotel_adult_ap.model.User;
import com.hoteladult.hotel_adult_ap.reposiroty.RoleServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static org.springframework.http.HttpStatus.FOUND;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleServiceImplement roleServiceImplement;

    @PostMapping("/create-new-role")
    public ResponseEntity<?> createNewRole(@RequestBody Role role) {
        try {
            Role newRole = roleServiceImplement.createNewRole(role);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResultModel("ok", "create role new successfully",  newRole)
            );
        }catch(RoleAlreadyExistException re){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(re.getMessage());

        }
    }

    @GetMapping("/get_all_role")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleServiceImplement.getAllRoles(), FOUND);
    }

    @DeleteMapping("/delete/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable("roleId") Long roleId) {
        roleServiceImplement.deleteRole(roleId);
        return null;
    }

//    ==== remove user from role
    @PostMapping("/remove-user-from-role")
    public User removeUserFromRole(
            @RequestParam("userId") Long userId,
            @RequestParam("roleId") Long roleId
    ) {
        return roleServiceImplement.removeUserFromRole(userId,roleId);
    }

//    add role for user
    @PostMapping("assign-role-for-user")
    public User assignRoleForUser(
            @RequestParam("userId") Long userId,
            @RequestParam("roleId") Long roleId
    ) {
        return roleServiceImplement.assignRoleToUser(userId,roleId);
    }


}
