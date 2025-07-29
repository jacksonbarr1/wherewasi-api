package com.wherewasi.wherewasiapi.controller;

import com.wherewasi.wherewasiapi.dto.ShowMetadataDTO;
import com.wherewasi.wherewasiapi.service.ShowService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/show")
public class ShowController {

    private final ShowService showService;

    @GetMapping("/search")
    public ResponseEntity<List<ShowMetadataDTO>> searchShows(String query) {
        List<ShowMetadataDTO> shows = showService.searchShows(query);
        return ResponseEntity.ok(shows);
    }
}
