package com.kyura.message.mapper;

import com.kyura.message.models.User;
import com.kyura.message.payload.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.function.Function;

//@Service
//public class UserDtoMapper implements Function<User, UserResponse> {
//
//    @Override
//    public UserResponse apply(User user) {
//        return UserResponse.builder()
//                .id(user.getId())
//                .username(user.getUsername())
//                .roles(user.getRoles())
//                .build();
//    }
//}
