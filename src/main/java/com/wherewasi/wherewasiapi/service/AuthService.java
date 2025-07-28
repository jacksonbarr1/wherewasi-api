package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.enumeration.UserRole;
import com.wherewasi.wherewasiapi.exception.AuthenticationFailedException;
import com.wherewasi.wherewasiapi.exception.EmailTakenException;
import com.wherewasi.wherewasiapi.exception.WeakPasswordException;
import com.wherewasi.wherewasiapi.model.User;
import com.wherewasi.wherewasiapi.repository.UserRepository;
import com.wherewasi.wherewasiapi.request.auth.AuthenticationRequest;
import com.wherewasi.wherewasiapi.request.auth.RegisterRequest;
import com.wherewasi.wherewasiapi.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailTakenException("Email is already taken.");
        }

        if (request.getPassword().length() < 8) {
            throw new WeakPasswordException("Password must be at least 8 characters long.");
        }

        User user = User.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.ROLE_USER)
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty() ||
                !passwordEncoder.matches(request.getPassword(), userOptional.get().getPassword())) {
            throw new AuthenticationFailedException("Invalid credentials.");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        User user = userOptional.get();
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
