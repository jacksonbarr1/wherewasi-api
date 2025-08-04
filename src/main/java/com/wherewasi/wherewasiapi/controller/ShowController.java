package com.wherewasi.wherewasiapi.controller;

import com.wherewasi.wherewasiapi.dto.response.ShowDetailsDTO;
import com.wherewasi.wherewasiapi.dto.response.ShowMetadataDTO;
import com.wherewasi.wherewasiapi.service.ShowService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/v1/show")
public class ShowController {

    private final ShowService showService;

    @GetMapping("/search")
    public ResponseEntity<List<ShowMetadataDTO>> searchShows(@RequestParam @NotBlank @Size(min = 2, max = 50) String query) {
        return ResponseEntity.ok(showService.searchShows(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowDetailsDTO> getShowDetailsById(@PathVariable Integer id) {
        return ResponseEntity.ok(showService.getShowDetailsById(String.valueOf(id)));
    }
}
