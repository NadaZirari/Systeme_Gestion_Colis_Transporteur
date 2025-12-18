package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.enums.ColisType;
import com.example.demo.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface  UserRepository extends MongoRepository<User, String> {

    Optional<User> findByLogin(String login);

    Page<User> findByRole(Role role, Pageable pageable);

    Page<User> findByRoleAndSpecialite(
            Role role,
            ColisType specialite,
            Pageable pageable
    );
}
