package com.example.bankcards.controller;

import com.example.bankcards.dto.users.ChangeRole;
import com.example.bankcards.entity.User;
import com.example.bankcards.security.annotations.RoleAdmin;
import com.example.bankcards.service.RolesService;
import com.example.bankcards.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RolesService rolesService;

    @PostMapping("/{username}/changeRole")
    @RoleAdmin
    public ResponseEntity<Void> changeRole(@PathVariable String username, @RequestBody ChangeRole.Request dto) {
        userService.updateUserRole(username, dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{username}/delete")
    @RoleAdmin
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}


