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
    @Builder.Default
    private Boolean active = false;
    
    // Champs spécifiques au transporteur (null si ADMIN)
    private StatutTransporteur statut;
    private ColisType specialite;

    // Explicit getter for active to ensure compatibility with Lombok and MapStruct
    public Boolean isActive() {
        return active;
    }

    // Explicit setter for active
    public void setActive(Boolean active) {
        this.active = active;
    }
}