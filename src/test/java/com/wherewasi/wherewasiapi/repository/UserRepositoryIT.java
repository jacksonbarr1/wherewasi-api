package com.wherewasi.wherewasiapi.repository;

import com.wherewasi.wherewasiapi.AbstractIT;
import com.wherewasi.wherewasiapi.enumeration.UserRole;
import com.wherewasi.wherewasiapi.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserRepositoryIT extends AbstractIT {
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUserByEmail() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .role(UserRole.ROLE_USER)
                .build();

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());

        Optional<User> foundUserOptional = userRepository.findByEmail(user.getEmail());

        assertThat(foundUserOptional).isPresent();
        User foundUser = foundUserOptional.get();
        assertThat(foundUser.getId()).isEqualTo(savedUser.getId());
        assertThat(foundUser.getFirstName()).isEqualTo("John");
        assertThat(foundUser.getLastName()).isEqualTo("Doe");
        assertThat(foundUser.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(foundUser.getRole()).isEqualTo(UserRole.ROLE_USER);
    }

}
