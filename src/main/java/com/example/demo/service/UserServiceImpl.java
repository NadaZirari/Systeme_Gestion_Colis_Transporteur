package com.example.demo.service;


import com.example.demo.entity.User;
import com.example.demo.enums.ColisType;
import com.example.demo.enums.Role;
import com.example.demo.enums.StatutTransporteur;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createTransporteur(User user) {
        user.setRole(Role.TRANSPORTEUR);
        user.setActive(true);
        user.setStatut(StatutTransporteur.DISPONIBLE);
        return userRepository.save(user);
    }

    @Override
    public User updateTransporteur(String id, User user) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transporteur not found"));

        existing.setLogin(user.getLogin());
        existing.setSpecialite(user.getSpecialite());
        existing.setStatut(user.getStatut());

        return userRepository.save(existing);
    }

    @Override
    public void deleteTransporteur(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> getTransporteurs(Pageable pageable) {
        return userRepository.findByRole(Role.TRANSPORTEUR, pageable);
    }

    @Override
    public Page<User> getTransporteursBySpecialite(
            ColisType specialite,
            Pageable pageable
    ) {
        return userRepository.findByRoleAndSpecialite(
                Role.TRANSPORTEUR,
                specialite,
                pageable
        );
    }

    @Override
    public User activateUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

}
