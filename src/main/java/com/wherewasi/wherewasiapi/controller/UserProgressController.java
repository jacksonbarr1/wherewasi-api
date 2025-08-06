package com.wherewasi.wherewasiapi.controller;

import com.wherewasi.wherewasiapi.dto.request.UserShowProgressRequest;
import com.wherewasi.wherewasiapi.dto.response.UserShowProgressResponse;
import com.wherewasi.wherewasiapi.service.UserProgressService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/v1/user-progress")
public class UserProgressController {

    private final UserProgressService userProgressService;

    @PutMapping("/{showId}")
    public ResponseEntity<UserShowProgressResponse> createOrUpdateUserShowProgress(
            @PathVariable @Min(1) Integer showId,
            @RequestBody @Valid UserShowProgressRequest request,
            Principal principal) {
        String userId = principal.getName();
        UserShowProgressResponse progress = userProgressService.createOrUpdateUserShowProgress(request,
                String.valueOf(showId), userId);

        return ResponseEntity.ok(progress);
    }

}

