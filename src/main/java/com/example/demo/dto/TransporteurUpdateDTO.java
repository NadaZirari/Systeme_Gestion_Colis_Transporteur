package com.example.demo.dto;


import com.example.demo.enums.ColisType;
import com.example.demo.enums.StatutTransporteur;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class TransporteurUpdateDTO {

    @NotBlank
    private String login;

    @NotNull
    private StatutTransporteur statut;

    @NotNull
    private ColisType specialite;
}
