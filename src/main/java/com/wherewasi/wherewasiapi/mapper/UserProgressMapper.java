package com.wherewasi.wherewasiapi.mapper;

import com.wherewasi.wherewasiapi.dto.response.UserShowProgressResponse;
import com.wherewasi.wherewasiapi.model.UserShowProgress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProgressMapper {

    UserShowProgressResponse toResponse(UserShowProgress userShowProgress);
}
