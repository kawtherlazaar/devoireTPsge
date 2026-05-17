package com.example.sge.controller;

import com.example.sge.dto.AuthResponse;
import com.example.sge.dto.LoginRequest;
import com.example.sge.model.Role;
import com.example.sge.model.Utilisateur;
import com.example.sge.Repositories.UtilisateurRepository;
import com.example.sge.security.JwtService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UtilisateurRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UtilisateurRepository repository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService,
                          AuthenticationManager authenticationManager) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody LoginRequest req) {

        // vérifier username existe déjà
        if (repository.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Utilisateur u = new Utilisateur();

        u.setUsername(req.getUsername());

        // encoder password
        u.setPassword(passwordEncoder.encode(req.getPassword()));

        // rôle envoyé depuis Postman
        if (req.getRole() == null) {
            u.setRole(Role.USER);
        } else {
            u.setRole(req.getRole());
        }

        repository.save(u);

        // génération JWT
        String token = jwtService.generateToken(u);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, u.getRole().name()));
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )
        );

        Utilisateur u = repository.findByUsername(req.getUsername())
                .orElseThrow();

        String token = jwtService.generateToken(u);

        return ResponseEntity.ok(
                new AuthResponse(token, u.getRole().name())
        );
    }
}