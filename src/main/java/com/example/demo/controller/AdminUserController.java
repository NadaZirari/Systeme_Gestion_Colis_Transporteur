package com.example.demo.controller;

import com.example.demo.dto.TransporteurUpdateDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.enums.ColisType;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable)
                .map(userMapper::toResponse);
    }

    @GetMapping("/transporteurs")
    public Page<UserResponseDTO> getTransporteurs(Pageable pageable) {
        return userService.getTransporteurs(pageable)
                .map(userMapper::toResponse);
    }

    @GetMapping("/transporteurs/specialite/{specialite}")
    public Page<UserResponseDTO> getBySpecialite(
            @PathVariable ColisType specialite,
            Pageable pageable
    ) {
        return userService.getTransporteursBySpecialite(specialite, pageable)
                .map(userMapper::toResponse);
    }

    @PostMapping("/transporteurs")
    public UserResponseDTO createTransporteur(
            @Valid @RequestBody TransporteurCreateDTO dto
    ) {
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return userMapper.toResponse(userService.createTransporteur(user));
    }

    @PutMapping("/transporteurs/{id}")
    public UserResponseDTO updateTransporteur(
            @PathVariable String id,
            @Valid @RequestBody TransporteurUpdateDTO dto
    ) {
        User user = User.builder()
                .login(dto.getLogin())
                .statut(dto.getStatut())
                .specialite(dto.getSpecialite())
                .build();

        return userMapper.toResponse(
                userService.updateTransporteur(id, user)
        );
    }

    @PutMapping("/users/{id}/activate")
    public UserResponseDTO activateUser(@PathVariable String id) {
        return userMapper.toResponse(userService.activateUser(id));
    }

    @DeleteMapping("/transporteurs/{id}")
    public void deleteTransporteur(@PathVariable String id) {
        userService.deleteTransporteur(id);
    }
}
