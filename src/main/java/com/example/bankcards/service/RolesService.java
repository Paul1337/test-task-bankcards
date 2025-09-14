package com.example.bankcards.service;

import com.example.bankcards.entity.Role;
import com.example.bankcards.repository.RolesRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    public Role findRole(String value) {
        return rolesRepository.findById(value).orElseThrow();
    }
}
