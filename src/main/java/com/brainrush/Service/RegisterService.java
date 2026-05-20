package com.brainrush.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.brainrush.DTO.AuthRegisterRequestDto;
import com.brainrush.DTO.AuthRegisterResponseDto;
import com.brainrush.Repository.RoleRepository;
import com.brainrush.Repository.UserRepository;
import com.brainrush.model.Role;
import com.brainrush.model.User;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthRegisterResponseDto register(AuthRegisterRequestDto request) {
        // Verifica che le password coincidano
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le password non coincidono");
        }

        // Verifica che l'username non esista già
        Optional<User> existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username già in uso");
        }

        // Crea nuovo utente
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        // Assegna il ruolo di default "USER"
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ruolo USER non trovato"));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        newUser.setRoles(roles);

        // Salva l'utente
        User savedUser = userRepository.save(newUser);

        return new AuthRegisterResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),
                "Registrazione completata con successo");
    }
}
