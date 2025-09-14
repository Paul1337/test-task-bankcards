package com.example.bankcards.service;

import com.example.bankcards.dto.auth.Register;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.RoleNotFoundException;
import com.example.bankcards.exception.UserAlreadyExistException;
import com.example.bankcards.repository.RolesRepository;
import com.example.bankcards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(Register.Request dto) {
        if (userRepository.existsByUsername(dto.getUsername())) throw new UserAlreadyExistException(dto.getUsername());
        String passwordEncoded = passwordEncoder.encode(dto.getPassword());
        User newUser = new User(dto.getUsername(), dto.getName(), passwordEncoded);
        var basicUserRole = rolesRepository.findById("USER").orElseThrow(() -> new RoleNotFoundException("USER"));
        newUser.setRole(basicUserRole);
        userRepository.save(newUser);
    }
}
