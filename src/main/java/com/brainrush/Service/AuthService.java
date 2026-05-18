package com.brainrush.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.brainrush.DTO.AuthLoginRequestDto;
import com.brainrush.DTO.AuthLoginResponseDto;
import com.brainrush.Repository.UserRepository;
import com.brainrush.model.User;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthLoginResponseDto login(AuthLoginRequestDto request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenziali non valide");
        }

        User user = userOpt.get();
        boolean validPassword = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!validPassword) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenziali non valide");
        }

        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName())
                .toList();

        return new AuthLoginResponseDto(user.getId(), user.getUsername(), roles);
    }
}
