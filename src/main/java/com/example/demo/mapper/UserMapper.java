package com.example.demo.mapper;


import com.example.demo.dto.TransporteurCreateDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

@Component

public class UserMapper {
    public User toEntity(TransporteurCreateDTO dto) {
        return User.builder()
                .login(dto.getLogin())
                .password(dto.getPassword())
                .specialite(dto.getSpecialite())
                .build();
    }

    public UserResponseDTO toResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .statut(user.getStatut())
                .specialite(user.getSpecialite())
                .build();
    }
}
