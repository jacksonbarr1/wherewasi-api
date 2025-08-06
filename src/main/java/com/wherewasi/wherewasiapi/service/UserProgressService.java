package com.wherewasi.wherewasiapi.service;

import com.wherewasi.wherewasiapi.dto.request.UserShowProgressRequest;
import com.wherewasi.wherewasiapi.dto.response.UserShowProgressResponse;

public interface UserProgressService {
    UserShowProgressResponse createOrUpdateUserShowProgress(UserShowProgressRequest request, String showId, String userId);
}
