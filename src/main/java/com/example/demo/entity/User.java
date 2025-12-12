package com.example.demo.entity;

import com.example.demo.enums.ColisType;
import com.example.demo.enums.Role;
import com.example.demo.enums.StatutTransporteur;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "users")
@Data
public class User {
    @Id
    private String id;
    private String login;
    private String password;
    private Role role;
    private Boolean active = true;
    
    // Champs spécifiques au transporteur (null si ADMIN)
    private StatutTransporteur statut;
    private ColisType specialite;
}