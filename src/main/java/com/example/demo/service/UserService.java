package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.enums.ColisType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
        User createTransporteur(User user);

User updateTransporteur(String id, User user);

void deleteTransporteur(String id);

Page<User> getAllUsers(Pageable pageable);

Page<User> getTransporteurs(Pageable pageable);

Page<User> getTransporteursBySpecialite(
        ColisType specialite,
        Pageable pageable
);

User activateUser(String id);
User desactivateUser(String id);

Optional<User> findByLogin(String login);
}