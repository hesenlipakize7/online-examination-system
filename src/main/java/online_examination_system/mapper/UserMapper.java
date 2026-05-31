package online_examination_system.mapper;

import online_examination_system.dto.response.UserResponse;
import online_examination_system.model.entity.User;

public class UserMapper {
    public static UserResponse toResponse(User user) {


        return UserResponse.builder()
                .id((long) user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
