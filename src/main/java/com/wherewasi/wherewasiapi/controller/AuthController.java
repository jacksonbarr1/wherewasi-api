package com.wherewasi.wherewasiapi.controller;

import com.wherewasi.wherewasiapi.request.auth.AuthenticationRequest;
import com.wherewasi.wherewasiapi.request.auth.RegisterRequest;
import com.wherewasi.wherewasiapi.response.AuthenticationResponse;
import com.wherewasi.wherewasiapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // Registration/authentication errors (bad password, user already exists, etc.) are thrown by service layer
    // and handled by global exception handler. `ResponseEntity.ok` is only ever returned to user on success.

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }

}
