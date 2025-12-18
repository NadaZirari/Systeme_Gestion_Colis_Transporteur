package com.example.demo.entity;

import com.example.demo.enums.ColisType;
import com.example.demo.enums.Role;
import com.example.demo.enums.StatutTransporteur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String id;
    private String login;
    private String password;
    private Role role;
    private Boolean isActive = true;
    
    // Champs spécifiques au transporteur (null si ADMIN)
    private StatutTransporteur statut;
    private ColisType specialite;
}