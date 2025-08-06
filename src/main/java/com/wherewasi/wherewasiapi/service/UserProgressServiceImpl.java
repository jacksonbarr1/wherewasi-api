package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.dto.request.UserShowProgressRequest;
import com.wherewasi.wherewasiapi.dto.response.UserShowProgressResponse;
import com.wherewasi.wherewasiapi.mapper.UserProgressMapper;
import com.wherewasi.wherewasiapi.model.UserShowProgress;
import com.wherewasi.wherewasiapi.repository.UserShowProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProgressServiceImpl implements UserProgressService {

    private final UserShowProgressRepository userShowProgressRepository;
    private final UserProgressMapper userProgressMapper;

    @Override
    public UserShowProgressResponse createOrUpdateUserShowProgress(UserShowProgressRequest request, String showId,
                                                                   String userId) {
        // Find existing progress by userId and showId
        Optional<UserShowProgress> existingProgress = userShowProgressRepository.findByUserIdAndShowId(userId, showId);

        // Update existing progress if exists, otherwise create new progress object
        UserShowProgress progress = existingProgress.orElseGet(() -> UserShowProgress.builder()
                .userId(userId)
                .showId(showId)
                .watchedEpisodesBySeason(request.getWatchedEpisodesBySeason())
                .build());

        userShowProgressRepository.save(progress);

        return userProgressMapper.toResponse(progress);
    }
}
