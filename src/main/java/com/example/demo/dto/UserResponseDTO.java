package com.example.demo.dto;

import com.example.demo.enums.ColisType;
import com.example.demo.enums.Role;
import com.example.demo.enums.StatutTransporteur;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {

    private String id;
    private String login;
    private Role role;
    private boolean isActive;
    private StatutTransporteur statut;
    private ColisType specialite;
}
