package com.example.demo.dto;

import com.example.demo.enums.ColisType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransporteurCreateDTO {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotNull
    private ColisType specialite;
}
