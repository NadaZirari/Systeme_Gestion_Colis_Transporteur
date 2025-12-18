package com.example.demo.dto;

import com.example.demo.enums.ColisType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ColisCreateDTO {

    @NotNull
    private ColisType type;

    @Positive
    private double poids;

    @NotBlank
    private String adresseDestination;

    // FRAGILE
    private String instructionsManutention;

    // FRIGO
    private Double temperatureMin;
    private Double temperatureMax;
}
