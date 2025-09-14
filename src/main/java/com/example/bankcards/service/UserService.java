package com.example.bankcards.service;

import com.example.bankcards.dto.users.ChangeRole;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RolesService rolesService;

  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
  }

  public void updateUserRole(String username, ChangeRole.Request request) {
    User user = getUserByUsername(username);
    user.setRole(rolesService.findRole(request.getNewRole()));
    userRepository.save(user);
  }

  public void deleteUser(String username) {
    userRepository.deleteByUsername(username);
  }

  
}

