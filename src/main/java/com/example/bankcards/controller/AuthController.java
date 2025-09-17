package com.example.bankcards.controller;

import com.example.bankcards.dto.auth.Login;
import com.example.bankcards.dto.auth.Register;
import com.example.bankcards.dto.auth.UserInfo;
import com.example.bankcards.entity.User;
import com.example.bankcards.security.JwtTokenProvider;
import com.example.bankcards.security.annotations.RoleUser;
import com.example.bankcards.service.RegisterService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid Register.Request registerRequest) {
        registerService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Login.Response> login(@RequestBody @Valid Login.Request loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtTokenProvider.generateToken(authentication);
        String roleResponse = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().getFirst();

        return ResponseEntity.ok(new Login.Response(jwt, userDetails.getUsername(), roleResponse));
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfo> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        String role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse("ROLE_UNKNOWN");
        return ResponseEntity.ok(new UserInfo(username, role));
    }
}
