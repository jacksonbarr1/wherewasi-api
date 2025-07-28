package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.exception.EmailTakenException;
import com.wherewasi.wherewasiapi.repository.UserRepository;
import com.wherewasi.wherewasiapi.dto.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void whenUserExists_thenThrowEmailTakenException() {

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(EmailTakenException.class, () -> authService.register(RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .build()
        ));
    }


}
